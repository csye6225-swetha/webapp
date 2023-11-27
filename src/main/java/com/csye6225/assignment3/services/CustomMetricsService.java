package com.csye6225.assignment3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class CustomMetricsService {
	
	private final Counter getAssignmentsCallCounter;
    private final Counter getAssignmentsIdCallCounter;
    private final Counter postAssignmentsCallCounter;
    private final Counter deleteAssignmentsCallCounter;
    private final Counter updateAssignmentsCallCounter;
    private final Counter healthzCallCounter;


    @Autowired
    public CustomMetricsService(MeterRegistry meterRegistry) {
        this.getAssignmentsCallCounter = Counter.builder("getassignments.call.counter")
            .description("Counter for getAssignments method")
            .register(meterRegistry);

        this.getAssignmentsIdCallCounter = Counter.builder("getassignmentsid.call.counter")
            .description("Counter for getAssignmentById method")
            .register(meterRegistry);

        this.postAssignmentsCallCounter = Counter.builder("postassignments.call.counter")
            .description("Counter for createAssignment method")
            .register(meterRegistry);

        this.deleteAssignmentsCallCounter = Counter.builder("deleteassignments.call.counter")
            .description("Counter for deleteAssignment method")
            .register(meterRegistry);

        this.updateAssignmentsCallCounter = Counter.builder("updateassignments.call.counter")
            .description("Counter for updateAssignment method")
            .register(meterRegistry);
        
        this.healthzCallCounter = Counter.builder("healthz.call.counter")
                .description("Counter for Health API")
                .register(meterRegistry);
    }

    public void incrementGetAssignmentsCallCounter() {
        getAssignmentsCallCounter.increment();
    }

    public void incrementGetAssignmentsIdCallCounter() {
        getAssignmentsIdCallCounter.increment();
    }

    public void incrementPostAssignmentsCallCounter() {
        postAssignmentsCallCounter.increment();
    }

    public void incrementDeleteAssignmentsCallCounter() {
        deleteAssignmentsCallCounter.increment();
    }

    public void incrementUpdateAssignmentsCallCounter() {
        updateAssignmentsCallCounter.increment();
    }
    
    public void incrementHealthCallCounter() {
    	healthzCallCounter.increment();
    }
	

}
