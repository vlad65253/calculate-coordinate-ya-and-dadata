package com.test.gigacom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Value("${dadata.token}")
    private String dadataToken;

    @Value("${dadata.secret}")
    private String dadataSecret;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Token " + dadataToken);
            request.getHeaders().add("X-Secret", dadataSecret);
            return execution.execute(request, body);
        };

        restTemplate.setInterceptors(List.of(interceptor));
        return restTemplate;
    }
}
