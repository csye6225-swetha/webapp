package com.csye6225.assignment3.pojo;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;


@Entity
public class Submission {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	

	 @ManyToOne
	 @JoinColumn(name = "user_id", nullable = false)
	  private Account user; 

	 @ManyToOne
	 @JoinColumn(name = "assignment_id", nullable = false)
	  private Assignment assignment;

	 @Column(name = "submission_date")
	  private Date submissionDate;
	 
	 @Column(name = "submission_url") 
	  private String submissionUrl;
	 
	 @Column(name = "submission_updated")
	  private Date submissionUpdatedDate;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSubmissionUpdatedDate() {
		return submissionUpdatedDate;
	}

	public void setSubmissionUpdatedDate(Date submissionUpdatedDate) {
		this.submissionUpdatedDate = submissionUpdatedDate;
	}

	public Account getUser() {
		return user;
	}

	public String getSubmissionUrl() {
		return submissionUrl;
	}

	public void setSubmissionUrl(String submissionUrl) {
		this.submissionUrl = submissionUrl;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	
	@PrePersist
	protected void onCreate() {
		this.submissionDate = new Date();
	    this.submissionUpdatedDate = this.submissionDate; 
	}

	
	 


}
