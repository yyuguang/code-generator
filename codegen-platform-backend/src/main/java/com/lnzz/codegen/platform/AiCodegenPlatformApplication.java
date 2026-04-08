package com.lnzz.codegen.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @classname: AiCodegenPlatformApplication
 * @author: Fantasy
 * @date: 2026/4/8 21:05
 * @description: Backend bootstrap class for the AI business system generator.
 */
@SpringBootApplication
@MapperScan("com.lnzz.codegen.platform.infrastructure.persistence")
public class AiCodegenPlatformApplication {

    /**
     * Start the Spring Boot application.
     *
     * @param args startup arguments
     * @return void
     * @author Fantasy
     * @date 2026/4/8 21:05
     */
    public static void main(String[] args) {
        SpringApplication.run(AiCodegenPlatformApplication.class, args);
    }
}
