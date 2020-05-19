package com.zm.fileupload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @Author zhangming
 * @Date 2020/5/19 10:47
 */
@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        // 不限制上传文件的大小
        return new MultipartConfigElement("");
    }
}
