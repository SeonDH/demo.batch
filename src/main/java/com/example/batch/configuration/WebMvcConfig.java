package com.example.batch.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebMvcConfig {

    @Bean
    SimpleClientHttpRequestFactory httpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(1000);
        factory.setBufferRequestBody(false);
        return factory;
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(httpRequestFactory());
    }

}
