package com.global.hr.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
@Configuration
public class AppConfig {
	/*
}
	  @Bean
	   // @LoadBalanced  // important for Eureka name resolution
	    public RestTemplate restTemplate() {
	        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	    }
	    */
	    @Bean
	    @LoadBalanced
	    RestTemplate restTemplate() {
	        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	        restTemplate.getInterceptors().add((request, body, execution) -> {
	            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	            if (attrs != null) {
	                HttpServletRequest currentRequest = attrs.getRequest();
	                String authHeader = currentRequest.getHeader("Authorization");
	                if (authHeader != null) {
	                    request.getHeaders().add("Authorization", authHeader);
	                }
	            }
	            return execution.execute(request, body);
	        });
	        return restTemplate;
	    }

}
