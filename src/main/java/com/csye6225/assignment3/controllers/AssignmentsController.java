package com.csye6225.assignment3.controllers;



import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
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
import com.csye6225.assignment3.pojo.Submission;
import com.csye6225.assignment3.pojo.SubmissionResponse;
import com.csye6225.assignment3.repositories.AccountRepository;
import com.csye6225.assignment3.repositories.AssignmentRepository;

import com.csye6225.assignment3.repositories.SubmissionRepository;
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

	
	
	@Autowired
	private SubmissionRepository submissionRepository;
	

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
	
	

	@PostMapping("/assignments/{id}/submission")
	public ResponseEntity<?> submitAssignment( @PathVariable("id") String id, @RequestBody Map<String, String> submissionData) {
		
		
		//customMetricsService.incrementPostSuCallCounter();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		
		 if (authentication == null || !authentication.isAuthenticated()) {
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
		    }

		
		    
		   
		        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		        
		        
		        String username = userDetails.getUsername();

		        System.out.println(username);
		       
		        Account currentUser = accountRepository.findByEmail(username).orElse(null);

		        if (currentUser == null) {
		        	
		        	 return ResponseEntity.badRequest().body("User not found.");
		        } 
		        
 
		
		
		Assignment existingAssignment = assignmentRepository.findById(id).orElse(null);
	    if (existingAssignment == null) {
	        return ResponseEntity.badRequest().body("Assignment not found.");
	    }
	    
	    LocalDateTime localDateTime = LocalDateTime.now();
	    
	    
	    Date dateFromLocalDateTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	    
	    
	    
	    if (existingAssignment.getDeadline().before(dateFromLocalDateTime)) {
	        return ResponseEntity.badRequest().body("Submission deadline has passed.");
	    }
	    
	    int numberOfSubmissions = submissionRepository.countByAssignmentAndUser(existingAssignment, currentUser);
	    if (numberOfSubmissions >= existingAssignment.getNum_of_attempts()) {
	        return ResponseEntity.badRequest().body("Retry limit exceeded.");
	    }
	    
	    
	    String submissionUrl = submissionData.get("submission_url");
	    if (submissionUrl == null || submissionUrl.isEmpty()) {
	        return ResponseEntity.badRequest().body("Submission URL is required.");
	    }
	    
	    
	    Submission submission = new Submission();
	    submission.setUser(currentUser);
	    submission.setAssignment(existingAssignment);
	    submission.setSubmissionUrl(submissionUrl);

	    
	    submissionRepository.save(submission);
	    
	    
	    
	    SubmissionResponse response = new SubmissionResponse();
	    response.setId(submission.getId()); 
	    response.setUserId(submission.getUser().getId()); 
	    response.setAssignmentId(submission.getAssignment().getId());
	    response.setSubmissionUrl(submission.getSubmissionUrl());
	    response.setSubmissionDate(submission.getSubmissionDate());
	    response.setSubmissionUpdatedDate(submission.getSubmissionUpdatedDate());

	   

	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    
	   
	    
	    
	
    }
	
	
	
	
	
	
	
	
}