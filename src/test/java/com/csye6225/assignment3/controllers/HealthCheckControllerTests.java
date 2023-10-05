package com.csye6225.assignment3.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HealthCheckControllerTests {
	
	
	@LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testHealthzEndpoint() {
    	
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + "8080" + "/healthz", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
