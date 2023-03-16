package com.project.banking_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;
import com.project.banking_system.dto.BankAccount;
import com.project.banking_system.dto.Management;
import com.project.banking_system.exception.MyException;
import com.project.banking_system.helper.ResponseStructure;
import com.project.banking_system.service.ManagementService;

@RestController
@RequestMapping("management")
public class ManagementController {
	@Autowired
	ManagementService service;

	@PostMapping("add")
	public ResponseStructure<Management> save(@RequestBody Management management) {
		return service.save(management);

	}

	@PostMapping("login")
	public ResponseStructure<Management> login(@RequestBody Management management) throws Exception {
		return service.login(management);
	}

	@GetMapping("accounts")
	public ResponseStructure<List<BankAccount>> fetchAllAccounts() throws MyException {
		return service.fetchAllAccounts();
	}
	
	@PutMapping("BankAccount/{acno}")
	public ResponseStructure<BankAccount> changeStatus(@PathVariable long acno){
		return service.changeStatus(acno);
	}
}



