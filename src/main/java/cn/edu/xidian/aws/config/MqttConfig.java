package cn.edu.xidian.aws.config;

import cn.edu.xidian.aws.service.MqttWeighService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

/**
 * @author akynazh@gmail.com
 * @date 1/31/25
 * @description
 */
@Configuration
@Slf4j
public class MqttConfig {
    @Value("${mqtt.broker.url}")
    private String brokerUrl;
    @Value("${mqtt.clientId}")
    private String clientId;
    @Value("${mqtt.topic}")
    private String topic;
    @Autowired
    private MqttWeighService weighService;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(brokerUrl, clientId, topic);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler(RetryTemplate retryTemplate) {
        return message -> retryTemplate.execute(context -> {
            try {
                weighService.handleMessage(message);
            } catch (Exception e) {
                log.error("Error handling message: ", e);
                throw new MessagingException("Message handling failed", e);
            }
            return null;
        });
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // 设置重试策略
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(Integer.MAX_VALUE);
        retryTemplate.setRetryPolicy(retryPolicy);

        // 设置重试间隔
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2.0); // 间隔指数增长
        backOffPolicy.setMaxInterval(10 * 60 * 1000);

        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
