package com.csye6225.assignment3.dependencies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class BeanFactory {
	
	
	@Bean
    public BCryptPasswordEncoder pwdEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	 @Bean
	 public CountedAspect countedAspect(MeterRegistry registry) {
	        return new CountedAspect(registry);
	    }

}
