package com.project.banking_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.banking_system.dto.BankAccount;
import com.project.banking_system.dto.Customer;
import com.project.banking_system.dto.Login;
import com.project.banking_system.exception.MyException;
import com.project.banking_system.helper.ResponseStructure;
import com.project.banking_system.repository.CustomerRepository;
import com.project.banking_system.service.CustomerService;

@RestController
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	CustomerService service;
	

	@PostMapping("add")
	public ResponseStructure<Customer> save(@RequestBody Customer customer) {
		return service.save(customer);
	}

	@PutMapping("otp/{cust_id}/{otp}")
	public ResponseStructure<Customer> otpVerify(@PathVariable int cust_id, @PathVariable int otp) throws MyException {
		return service.verify(cust_id, otp);
	}

	@PostMapping("login")
	public ResponseStructure<Customer> login(@RequestBody Login login) throws MyException {
		return service.login(login);
	}
	
	@PostMapping("account/{cust_id}/{type}")
	public ResponseStructure<Customer> createAccount(@PathVariable int cust_id,@PathVariable String type) throws MyException{
		return service.createAccount(cust_id,type);
	}
	
	@GetMapping("account/{cust_id}")
	public ResponseStructure<List<BankAccount>> fetchAll(@PathVariable int cust_id) throws MyException{
		return service.fetchAll(cust_id);
	}
	
	@GetMapping("accpunt/check/{acno}")
	public ResponseStructure<Double> checkBalance(long acno){
		return service.checkBalance(acno);
	}
}
