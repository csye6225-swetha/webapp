package com.csye6225.assignment3.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
public class SnsService {
	
	private final SnsClient snsClient;
	private final String topicArn;
	
	
	public SnsService(@Value("${sns.topic.arn:}") String topicArn) {
        this.snsClient = SnsClient.builder().build();
        this.topicArn = topicArn;
    }

    public String publishMessage(String message) {
        PublishRequest request = PublishRequest.builder()
                                               .topicArn(topicArn)
                                               .message(message)
                                               .build();
        PublishResponse response = snsClient.publish(request);
        return response.messageId();
    }
    
    
    public void notifySubmission(String email, String submissionUrl) {
        String message = "Submission received from " + email + " with URL: " + submissionUrl;
        publishMessage(message); // Using the existing publishMessage method
    }

}
