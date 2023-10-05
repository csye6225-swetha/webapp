package com.csye6225.assignment3.pojo;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
public class Account {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "first_name", nullable = false)
	private String first_name;
	
	
	@Column(name = "last_name", nullable = false)
	private String last_name;
	
	@Column(name = "password")
	private String password;
	
	//validations if time 
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "account_created", nullable = false, updatable=false)
	private Date account_created;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "account_updated", nullable = false)
	private Date account_updated;
	
	@PrePersist
	protected void onCreate() {
	    this.account_created = new Date();
	    this.account_updated = this.account_created; 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setPassword(String password) {
		
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getAccount_created() {
		return account_created;
	}

	public void setAccount_created(Date account_created) {
		this.account_created = account_created;
	}

	public Date getAccount_updated() {
		return account_updated;
	}

	public void setAccount_updated(Date account_updated) {
		this.account_updated = account_updated;
	}
	
}


