package cn.edu.xidian.aws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description 公共模块
 */
@RestController
@Slf4j
@Tag(name = "公共模块")
@RequestMapping("/common")
public class CommonController {
    @Value("${aws.name}")
    private String awsName;
    @Value("${aws.farmName}")
    private String awsFarmName;

    @Operation(summary = "信息")
    @GetMapping("/info")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok(awsName + ": " + awsFarmName);
    }
}
