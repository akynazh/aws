package cn.edu.xidian.aws.util;

import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsNetworkException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.bo.ProduceRecResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
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

/**
 * @author akynazh@gmail.com
 * @date 2025/3/23
 * @description
 */
@Slf4j
public class ProduceUtil {
    public static final String API_KEY = "vUbXMbteYyjWCg1IKJpcaYoD";
    public static final String SECRET_KEY = "DXGjvxUPfjcjSn3LEE0bsJPLZoAx6CjA";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static String rec(String image, String image64) throws IOException {
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
                .url("https://aip.baidubce.com/rest/2.0/image-classify/v1/classify/ingredient?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            ProduceRecResult result = JSON.parseObject(Objects.requireNonNull(response.body()).string(),
                    ProduceRecResult.class);
            log.info("result: {}", result);
            if (result.getResultNum() == 0 || CollectionUtils.isEmpty(result.getResult())) {
                throw new AwsNotFoundException(AwsNetworkException.IMAGE_REC_ERR);
            }
            return result.getResult().get(0).getName();
        } catch (Exception e) {
            throw new AwsNetworkException(AwsNetworkException.REQ_ERR);
        }
    }

    /**
     * 从用户的 AK，SK 生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     */
    private static String getAccessToken() {
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create("grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY, mediaType);
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

//    public static void main(String[] args) throws IOException {
//        System.out.println(rec("https://akynazh.site/images/pub/apples_cd1cda61-5610-4c10-92f1-5485a7d9892a.jpg", null));
//    }
}


