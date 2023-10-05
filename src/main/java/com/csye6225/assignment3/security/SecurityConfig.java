package com.csye6225.assignment3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.csye6225.assignment3.services.AccountDetailsService;




@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private AccountDetailsService accountDetailsService;

	
	 @Autowired
	    private BCryptPasswordEncoder pwdEncoder;
	
	
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
	    

	     .csrf((csrf) -> csrf.disable())
	     .httpBasic(Customizer.withDefaults());
	  
	   
       return http.build();
	}
	
	
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		  .userDetailsService(accountDetailsService)
		  .passwordEncoder(pwdEncoder);
	   
	}
	
	
	
	

}
