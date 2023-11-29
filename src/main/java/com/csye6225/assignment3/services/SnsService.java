package com.csye6225.assignment3.services;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Service
public class SnsService {
	
	private final SnsClient snsClient;
	private final String topicArn;
	

	public SnsService(@Value("${sns.topic.arn}") String topicArn) {
        this.snsClient = SnsClient.builder().build();
        this.topicArn = topicArn;
    }
	
	private static final Logger logger = LoggerFactory.getLogger(SnsService.class);

	public String publishMessage(String message) {
        try {
            PublishRequest request = PublishRequest.builder()
                                                   .topicArn(topicArn)
                                                   .message(message)
                                                   .build();
            PublishResponse response = snsClient.publish(request);
            return response.messageId();
        } catch (SnsException e) {
            logger.error("Error while publishing message to SNS: " + e.awsErrorDetails().errorMessage(), e);
            return null; // Or handle the exception as needed
        }
    }

    public void notifySubmission(String email, String submissionUrl) {
        String message = "Submission received from " + email + " with URL: " + submissionUrl;
        String messageId = publishMessage(message);
        System.out.println("MessageId:" + messageId);
        if (messageId == null) {
            logger.error("Failed to send notification for submission from " + email);
        }
    }
                                         

}
