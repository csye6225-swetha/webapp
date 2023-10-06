package com.csye6225.assignment3.pojo;

import java.util.Date;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Assignment {
	
	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}


	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	@JsonProperty("id")
	private String id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Min(value = 1, message = "Points must be at least 1")
	@Max(value = 100, message = "Points cannot exceed 100")
	@Column(name = "points", nullable = false)
	private int points;
	
	
	@Column(name = "num_of_attempts", nullable = false)
    @Min(value = 1, message = "Number of attempts must be at least 1")
    @Max(value = 100, message = "Number of attempts cannot exceed 100")
	private int num_of_attempts;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deadline", nullable = false)
	private Date deadline;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assignment_created", nullable = false)
	private Date assignment_created;
	
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assignment_updated", nullable = false)
	private Date assignment_updated;
	
	@PrePersist
	protected void onCreate() {
	    this.assignment_created = new Date();
	    this.assignment_updated = this.assignment_created; 
	}

 

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}


	public int getNum_of_attempts() {
		return num_of_attempts;
	}


	public void setNum_of_attempts(int num_of_attempts) {
		this.num_of_attempts = num_of_attempts;
	}


	public Date getDeadline() {
		return deadline;
	}


	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}


	public Date getAssignment_created() {
		return assignment_created;
	}


	public void setAssignment_created(Date assignment_created) {
		this.assignment_created = assignment_created;
	}


	public Date getAssignment_updated() {
		return assignment_updated;
	}


	public void setAssignment_updated(Date assignment_updated) {
		this.assignment_updated = assignment_updated;
	}
	
	
	

}
