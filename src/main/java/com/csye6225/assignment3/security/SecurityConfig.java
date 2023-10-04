package com.csye6225.assignment3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;




@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .sessionManagement((session) -> session
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        )
	    
	    .authorizeHttpRequests(authz -> authz
				  .requestMatchers("/healthz").permitAll()
				  .requestMatchers("/error").permitAll()
				  .anyRequest().authenticated()
				  
		   )
	    

	     .csrf((csrf) -> csrf.disable()
	
				  
	    );
	   
       return http.build();
	}
	
	
	
	

}