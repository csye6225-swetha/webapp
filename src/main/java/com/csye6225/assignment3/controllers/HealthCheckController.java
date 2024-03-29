package com.csye6225.assignment3.controllers;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.csye6225.assignment3.services.CustomMetricsService;


@RestController
@RequestMapping("/healthz")
public class HealthCheckController {
	
	
	@Autowired
	private CustomMetricsService customMetricsService;
	
	private final DataSource dataSource;

	 public HealthCheckController(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }
	
	 @GetMapping
	 public ResponseEntity<Void> healthCheck() {
		 
		    customMetricsService.incrementHealthCallCounter();
		   
		 
	        boolean isDatabaseConnected = isDatabaseConnected();

	        if (isDatabaseConnected) {
	        	
	            return ResponseEntity.status(HttpStatus.OK)
	                .cacheControl(CacheControl.noCache())
	                .build();
	        	
	        	
	        } else {
	            
	            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
	                .cacheControl(CacheControl.noCache())
	                .build();
	        }
	    }
	 
	 
	 private boolean isDatabaseConnected() {
		 
		 try (Connection connection = dataSource.getConnection()) {
	            return true;
	        } catch (SQLException e) {
	            return false;
	        }
		 
		 
	 }
	 
	 @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
	    public ResponseEntity<Void> rejectOtherMethods() {
	      
	        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).cacheControl(CacheControl.noCache()).build();
	    }


}
