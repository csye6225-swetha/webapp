package com.csye6225.assignment3.controllers;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csye6225.assignment3.pojo.Account;
import com.csye6225.assignment3.pojo.Assignment;
import com.csye6225.assignment3.repositories.AccountRepository;
import com.csye6225.assignment3.repositories.AssignmentRepository;
import com.csye6225.assignment3.services.CustomMetricsService;





@RestController
@RequestMapping("v1")
public class AssignmentsController {
	
	@Autowired
    private AssignmentRepository assignmentRepository;
	

	@Autowired
    private AccountRepository accountRepository;
	

    @Autowired
    private CustomMetricsService customMetricsService;

	
	
	
	@GetMapping(value = "/assignments")
    public Iterable<Assignment> getAllAssignments() {
		
		Iterable<Assignment> assignments = assignmentRepository.findAll();

		 customMetricsService.incrementGetAssignmentsCallCounter();
		
        return assignments;
        
        
    }
	
	
	@GetMapping("/assignments/{id}")
	public ResponseEntity<Assignment> getAssignmentById(@PathVariable("id") String id) {
		
		 customMetricsService.incrementGetAssignmentsIdCallCounter();
		 
	    Optional<Assignment> assignmentOptional = assignmentRepository.findById(id);

	    if (assignmentOptional.isPresent()) {
	        Assignment assignment = assignmentOptional.get();
	        System.out.println("Assignment" + assignment.getName());
	        return ResponseEntity.ok(assignment);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	@PostMapping("/assignments")
	public ResponseEntity<String> createAssignment(@RequestBody Assignment assignment) {
		
		
		customMetricsService.incrementPostAssignmentsCallCounter();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
		    
		    if (authentication.getPrincipal() instanceof UserDetails) {
		        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		        
		        
		        String username = userDetails.getUsername();

		        System.out.println(username);
		       
		        Account currentUser = accountRepository.findByEmail(username).orElse(null);

		        if (currentUser != null) {
		           
		            assignment.setAccount(currentUser);
		            assignmentRepository.save(assignment);

		            return ResponseEntity.status(HttpStatus.CREATED).build();
		        } 
		        
		    }
		}
		
           
            return ResponseEntity.badRequest().body("User not found.");
	         
	
    }
	
	
	@DeleteMapping("/assignments/{id}")
	public ResponseEntity<String> deleteAssignment(@PathVariable("id") String id) {
		
		customMetricsService.incrementDeleteAssignmentsCallCounter();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
		    if (authentication.getPrincipal() instanceof UserDetails) {
		        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		        String username = userDetails.getUsername();

		        Account currentUser = accountRepository.findByEmail(username).orElse(null);

		        if (currentUser != null) {
		            Assignment assignmentToDelete = assignmentRepository.findById(id).orElse(null);

		            if (assignmentToDelete != null && assignmentToDelete.getAccount().equals(currentUser)) {
		                
		                assignmentRepository.delete(assignmentToDelete);
		                return ResponseEntity.noContent().build();
		            } else{
		               
		                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this assignment.");
		            }
		        }
		    }
		}

		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
  
	}
	
	
	
	@PutMapping("/assignments/{id}")
	public ResponseEntity<String> updateAssignment(
	    @PathVariable("id") String id,
	    @RequestBody Assignment updatedAssignment
	) {
		
		customMetricsService.incrementUpdateAssignmentsCallCounter();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
		    if (authentication.getPrincipal() instanceof UserDetails) {
		        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		        String username = userDetails.getUsername();

		        Account currentUser = accountRepository.findByEmail(username).orElse(null);

		        if (currentUser != null) {
		            Assignment assignmentToUpdate = assignmentRepository.findById(id).orElse(null);

		            if (assignmentToUpdate != null && assignmentToUpdate.getAccount().equals(currentUser)) {
		                
		                assignmentToUpdate.setName(updatedAssignment.getName());
		                assignmentToUpdate.setNum_of_attempts(updatedAssignment.getNum_of_attempts());
		                assignmentToUpdate.setPoints(updatedAssignment.getPoints());
		                assignmentToUpdate.setDeadline(updatedAssignment.getDeadline());
		                
		                assignmentRepository.save(assignmentToUpdate);

		                return ResponseEntity.noContent().build();
		            } else {
		                // User is not the owner of the assignment
		                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to update this assignment.");
		            }
		        }
		    }
		}

		// User is not authenticated or not found
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
	    
	}
	
	
}