package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: hlt
 * @Description: 修改RFC 3986规范
 * @Date: 2020/2/16 23:08
 */
@Configuration
public class RfcConfig {
    @Bean
    public Integer setRfc(){
        // 指定jre系统属性，允许特殊符号， 如{} 做入参，其他符号按需添加。见 tomcat的HttpParser源码。
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}");
        return 0;
    }
}
