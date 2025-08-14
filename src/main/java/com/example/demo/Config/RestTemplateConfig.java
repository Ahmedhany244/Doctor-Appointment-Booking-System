package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
    @Bean	// inject this rest template each the project needes a rest template
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
