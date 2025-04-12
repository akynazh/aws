package cn.edu.xidian.aws.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author akynazh@gmail.com
 * @date 2025/4/12
 * @description
 */
@Component
public class ImageService {
    @Value("${minio.server.name}")
    public String serverName;
    @Value("${minio.server.url}")
    public String serverUrl;

    public String handle(String image) {
        if (image.startsWith(serverUrl)) {
            return image;
        }
        try {
            int i = image.indexOf("/");
            String objectKey = image.substring(i + 1);
            String target = serverName + objectKey;
            String command = "mc cp " + image + " " + target;
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return serverUrl + objectKey;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
