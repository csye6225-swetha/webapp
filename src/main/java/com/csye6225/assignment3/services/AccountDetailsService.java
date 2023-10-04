package com.csye6225.assignment3.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.csye6225.assignment3.pojo.Account;
import com.csye6225.assignment3.repositories.AccountRepository;



@Service
public class AccountDetailsService implements UserDetailsService {
	
	
	@Autowired
    private AccountRepository accountRepository;
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            
        	return new org.springframework.security.core.userdetails.User(
                    account.getEmail(), account.getPassword(),authorities
        	);
        } else {
        	throw new UsernameNotFoundException("Account not found with email: " + email);
        }
        
    }

}
