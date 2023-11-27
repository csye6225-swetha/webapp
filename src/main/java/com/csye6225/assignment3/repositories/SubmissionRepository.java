package com.csye6225.assignment3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csye6225.assignment3.pojo.Account;
import com.csye6225.assignment3.pojo.Assignment;
import com.csye6225.assignment3.pojo.Submission;

public interface SubmissionRepository extends JpaRepository<Submission,Long>{

	int countByAssignmentAndUser(Assignment existingAssignment, Account currentUser);
	

}
