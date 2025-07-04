package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.Produces;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsNetworkException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.bo.PredictForm;
import cn.edu.xidian.aws.pojo.bo.PredictResult;
import cn.edu.xidian.aws.pojo.bo.PredictResultBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author akynazh@gmail.com
 * @date 2025/3/23
 * @description
 */
@Slf4j
@Component
public class PredictService {
    @Value("${aws.predict.baidu.key}")
    private String BAIDU_PREDICT_KEY;
    @Value("${aws.predict.baidu.secret}")
    private String BAIDU_PREDICT_SECRET;
    @Value("${aws.predict.baidu.url}")
    private String BAIDU_PREDICT_URL;
    @Value("${aws.predict.self.url}")
    private String SELF_PREDICT_URL;

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)  // 设置连接超时为60秒
            .readTimeout(60, TimeUnit.SECONDS)     // 设置读取超时为60秒
            .writeTimeout(60, TimeUnit.SECONDS)    // 设置写入超时为60秒
            .build();

    private static String resolve(Request request) {
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            ResponseBody body = response.body();
            PredictResult result = JSON.parseObject(Objects.requireNonNull(body).string(),
                    PredictResult.class);
            log.info("result: {}", result);
            if (result.getResultNum() == 0 || CollectionUtils.isEmpty(result.getResult())) {
                throw new AwsNotFoundException(AwsNetworkException.IMAGE_REC_ERR);
            }
            PredictResultBody bestPredictResult = result.getResult().get(0);
            return Produces.get(Math.toIntExact(bestPredictResult.getClazz())).getName();
        } catch (AwsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AwsNetworkException(AwsNetworkException.REQ_ERR);
        }
    }

    public String predict(String image, String image64) {
        if (!StringUtils.hasText(image) && !StringUtils.hasText(image64)) {
            throw new AwsArgumentException(AwsArgumentException.PARAM_MISSING);
        }
        if (!StringUtils.hasText(image)) {
            image = "";
        }
        if (!StringUtils.hasText(image64)) {
            image64 = "";
        }
        PredictForm form = new PredictForm();
        form.setImage(image);
        form.setImage64(image64);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(JSON.toJSONString(form), mediaType);
        Request request = new Request.Builder()
                .url(SELF_PREDICT_URL)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        return resolve(request);
    }

    public String predict3(String image, String image64) throws IOException {
        if (!StringUtils.hasText(image) && !StringUtils.hasText(image64)) {
            throw new AwsArgumentException(AwsArgumentException.PARAM_MISSING);
        }
        if (!StringUtils.hasText(image64)) {
            image64 = getNetFileContentAsBase64(image);
        }
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create("image="
                + URLEncoder.encode(image64, StandardCharsets.UTF_8), mediaType);
        Request request = new Request.Builder()
                .url(BAIDU_PREDICT_URL + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        return resolve(request);
    }

    /**
     * 从用户的 AK，SK 生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     */
    private String getAccessToken() {
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create("grant_type=client_credentials&client_id=" + BAIDU_PREDICT_KEY
                + "&client_secret=" + BAIDU_PREDICT_SECRET, mediaType);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            JSONObject jsonObject = JSON.parseObject(Objects.requireNonNull(response.body()).string());
            return jsonObject.get("access_token").toString();
        } catch (Exception e) {
            throw new AwsNetworkException(AwsNetworkException.REQ_ERR);
        }
    }

    /**
     * 获取文件 base64 编码
     *
     * @param path 文件路径
     * @return base64 编码信息，不带文件头
     * @throws IOException IO 异常
     */
    public static String getFileContentAsBase64(String path) throws IOException {
        byte[] b = Files.readAllBytes(Paths.get(path));
        return Base64.getEncoder().encodeToString(b);
    }

    /**
     * 获取网络文件 base64 编码
     *
     * @param path 网络文件路径
     * @return base64 编码信息，不带文件头
     * @throws IOException IO 异常
     */
    public static String getNetFileContentAsBase64(String path) throws IOException {
        try (InputStream inputStream = new URL(path).openStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        }
    }
}


