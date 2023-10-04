package com.csye6225.assignment3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.csye6225.assignment3.services.AccountService;

@Component
public class CsvLoader implements CommandLineRunner  {
	
	@Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        accountService.loadUsersFromCSV();
    }

}
