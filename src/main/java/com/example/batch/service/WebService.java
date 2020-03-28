package com.example.batch.service;

import com.example.batch.configuration.model.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
public class WebService {

    @Value("${web.url}")
    String webUrl;


    @Autowired
    private RestTemplate restTemplate;

    public void sendRequest(RequestBody requestBody) {
        HttpHeaders requestHeaders = getDefaultHttpHeaders();
        String url = webUrl + "/";

        restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(requestBody, requestHeaders), Map.class);
    }


    private HttpHeaders getDefaultHttpHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return requestHeaders;
    }


}
