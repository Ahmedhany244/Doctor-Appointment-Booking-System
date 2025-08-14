package com.example.demo.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean		// this is used so now spring sees this method as a bean and @Autowire will automatically inject it
    ModelMapper modelMapper() {
		return new ModelMapper();
		
	}
    
}
