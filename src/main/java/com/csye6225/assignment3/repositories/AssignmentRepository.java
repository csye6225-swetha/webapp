package com.csye6225.assignment3.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csye6225.assignment3.pojo.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment,String> {


	
}
