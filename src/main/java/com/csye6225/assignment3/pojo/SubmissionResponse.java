package com.csye6225.assignment3.pojo;

import java.util.Date;

public class SubmissionResponse {
	
	 private String id;
	 private String userId;
	 private String assignmentId;
	 private Date submissionDate;
	 private String submissionUrl;
	 private Date submissionUpdatedDate;
	 
	 
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}
	public Date getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	public String getSubmissionUrl() {
		return submissionUrl;
	}
	public void setSubmissionUrl(String submissionUrl) {
		this.submissionUrl = submissionUrl;
	}
	public Date getSubmissionUpdatedDate() {
		return submissionUpdatedDate;
	}
	public void setSubmissionUpdatedDate(Date submissionUpdatedDate) {
		this.submissionUpdatedDate = submissionUpdatedDate;
	}
	

}
