package com.csye6225.assignment3.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csye6225.assignment3.pojo.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account,String> {
	
	 Optional<Account> findByEmail(String emal);
	
}
