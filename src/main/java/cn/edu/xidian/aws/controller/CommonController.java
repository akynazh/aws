package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
@RestController
@Slf4j
public class CommonController {
    @Value("${aws.name}")
    private String awsName;
    @Value("${aws.farmName}")
    private String awsFarmName;

    @GetMapping({"/", "/index"})
    public ResponseEntity<String> index() {
        return ResponseEntity.ok(awsName + ": " + awsFarmName);
    }
}
