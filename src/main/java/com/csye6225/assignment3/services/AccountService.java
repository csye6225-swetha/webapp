package com.csye6225.assignment3.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import com.csye6225.assignment3.pojo.Account;
import com.csye6225.assignment3.repositories.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
    private AccountRepository accountRepository;
	
	public void loadUsersFromCSV() {
		
		String csvFilePath = "./accounts.csv";
		
		
		 try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] data = line.split(","); // Assuming CSV data is comma-separated

	               
	                if (data.length >= 4) {
	                    String email = data[0].trim();
	                    String password = data[1].trim();
	                    String firstName = data[2].trim();
	                    String lastName = data[3].trim();

	                    
	                    Optional<Account> existingAccount = accountRepository.findByEmail(email);

	                    if (existingAccount.isPresent()) {
	                    	
	                    	System.out.println("User already exists: " + email);
	                       
	                    } else {
	                       
	                    	 Account newAccount = new Account();
		                        
		                        newAccount.setEmail(email);
		                        newAccount.setPassword(passwordEncoder.encode(password));
		                        newAccount.setFirst_name(firstName);
		                        newAccount.setLast_name(lastName);
		                        accountRepository.save(newAccount);
	                    }
		
	                }
	            }
		 } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
}
