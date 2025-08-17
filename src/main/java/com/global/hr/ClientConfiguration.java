package com.global.hr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@Configuration
public class ClientConfiguration {

    private final int connectTimeout;
    private final int readTimeout;

    public ClientConfiguration(  @Value("${admin.rest.connect-timeout:3000}") int connectTimeout, @Value("${admin.rest.read-timeout:5000}") int readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    // For Eureka-discovered calls ( patient_service) 
    @Bean
    @LoadBalanced
    RestTemplate patientRestTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    // For direct host:port calls ( doctor service )
    @Bean
    RestTemplate doctorRestTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean		// this is used so now spring sees this method as a bean and @Autowire will automatically inject it
    ModelMapper modelMapper() {
		return new ModelMapper();
		
	}
}