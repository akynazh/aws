package cn.edu.xidian.aws.config;

import cn.edu.xidian.aws.constant.Mqtt;
import cn.edu.xidian.aws.pojo.bo.MqttResult;
import cn.edu.xidian.aws.pojo.vo.record.RecordAddVO;
import cn.edu.xidian.aws.service.MqttUserService;
import cn.edu.xidian.aws.service.MqttWeighService;
import cn.edu.xidian.aws.service.RecordService;
import cn.edu.xidian.aws.service.TodoService;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author akynazh@gmail.com
 * @date 1/31/25
 * @description
 */
@Configuration
@Slf4j
public class MqttConfig {
    @Value("${mqtt.broker.urls}")
    private String[] brokerUrls;
    @Value("${mqtt.server.username}")
    private String username;
    @Value("${mqtt.server.password}")
    private String password;
    @Value("${mqtt.edge.username}")
    private String usernameEdge;
    @Value("${mqtt.edge.password}")
    private String passwordEdge;
    @Autowired
    private MqttWeighService weighService;
    @Autowired
    private MqttUserService mqttUserService;
    @Autowired
    private TodoService todoService;

    @PostConstruct
    public void init() {
        if (mqttUserService.userNotExists(username)) {
            mqttUserService.createScaleSubscriberAndResultPublisher(username, password);
        }
        if (mqttUserService.userNotExists(usernameEdge)) {
            mqttUserService.createScalePublisher(usernameEdge, passwordEdge);
        }
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(brokerUrls);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setAutomaticReconnect(true);
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer mqttInbound(MqttPahoClientFactory factory,
                                       @Qualifier("mqttInboundChannel") MessageChannel channel) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(UUID.randomUUID().toString(), factory, Mqtt.TOPIC_SCALE);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(Mqtt.QOS_EXACTLY_ONCE);
        adapter.setOutputChannel(channel);
        return adapter;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5); // 最多重试 5 次
        retryTemplate.setRetryPolicy(retryPolicy);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(30000); // 最大间隔 30 秒
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public MessageHandler handler(RetryTemplate retryTemplate, MqttGateway mqttGateway) {
        return message -> retryTemplate.execute(
                context -> {
                    try {
                        log.info("Consume message: {}", message);
                        weighService.handleMessage(message);
                    } catch (Exception e) {
                        log.error("Fail to consume message: {}, error: {}", message, e.getMessage());
                        throw new RuntimeException(e);
                    }
                    return null;
                },
                context -> {
                    // 超过最大重试次数，触发这个回调
                    log.error("Fail to consume message after retry: {}", message);
                    Object payload = message.getPayload();
                    RecordAddVO data = new ObjectMapper().readValue(payload.toString(), RecordAddVO.class);
                    todoService.addTodo(data);
                    Throwable e = context.getLastThrowable();
                    MqttResult result = new MqttResult();
                    result.setSuccess(0);
                    if (e != null) {
                        result.setReason(e.getMessage());
                    }
                    result.setMessage(message);
                    mqttGateway.sendToMqtt(JSON.toJSONString(result));
                    return null;
                }
        );
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound(MqttPahoClientFactory factory) {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(UUID.randomUUID().toString(), factory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(Mqtt.TOPIC_RESULT);
        messageHandler.setDefaultQos(Mqtt.QOS_AT_LEAST_ONCE);
        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MqttGateway {
        void sendToMqtt(String data);
    }
}
