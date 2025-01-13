package cn.edu.xidian.aws.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
@RestController
public class CommonController {
    @GetMapping({"/", "/index"})
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Agricultural Weighing System");
    }
}
