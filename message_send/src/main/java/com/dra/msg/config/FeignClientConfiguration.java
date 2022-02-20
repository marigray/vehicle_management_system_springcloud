package com.dra.msg.config;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 设置feign 承接上游请求头
 */
@Configuration
@Slf4j
public class FeignClientConfiguration extends FeignClientProperties.FeignClientConfiguration {

    /**
     *  配置动态地址
     *  请求头参数透传
     * @return
     */
    @Bean

    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
//            //替换路径中变量xxx为具体值
//            String yunId = request.getHeader("xxx");
//            requestTemplate.uri(requestTemplate.request().url().replace("//","/"+yunId+"/"));

            //透传请求header参数
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    requestTemplate.header(name, values);
                }
            }
            log.info("openFeign配置已生效");
        };
    }




}
