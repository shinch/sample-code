package com.gmail.shinch.report.config;

import com.gmail.shinch.report.interceptor.HttpLogUtil;
import com.gmail.shinch.report.interceptor.RestTemplateInterceptor;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {

    private final HttpLogUtil httpLogUtil;

    @Autowired
    public RestTemplateConfig(HttpLogUtil httpLogUtil) {
        this.httpLogUtil = httpLogUtil;
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3 * 1000);
        requestFactory.setReadTimeout(60 * 1000);

        RestTemplate restTemplate = new RestTemplateBuilder().requestFactory(() -> new BufferingClientHttpRequestFactory(requestFactory)).build();
        List<ClientHttpRequestInterceptor> interceptors = Lists.newArrayList(new RestTemplateInterceptor(httpLogUtil));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

}
