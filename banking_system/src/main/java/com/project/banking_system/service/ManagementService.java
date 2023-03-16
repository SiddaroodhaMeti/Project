package com.project.banking_system.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.banking_system.dto.BankAccount;
import com.project.banking_system.dto.Customer;
import com.project.banking_system.dto.Management;
import com.project.banking_system.exception.MyException;
import com.project.banking_system.helper.ResponseStructure;
import com.project.banking_system.repository.BankRepository;
import com.project.banking_system.repository.ManagementRepository;

@Service
public class ManagementService {

	@Autowired
	ManagementRepository repository;
	
	@Autowired
	BankRepository repository2;

	public ResponseStructure<Management> save(Management management) {
		ResponseStructure<Management> structure = new ResponseStructure<>();
		structure.setCode(HttpStatus.CREATED.value());
		structure.setMessage("Account Created Successfully");
		structure.setData(repository.save(management));
		return structure;
	}

	public ResponseStructure<Management> login(Management management) throws Exception {
		ResponseStructure<Management> structure = new ResponseStructure<>();
		Management management1 = repository.findByEmail(management.getEmail());
		if (management1==null) {

			throw new MyException("Invalid management mail");
		} else {
			
			if (management.getPassword().equals(management.getPassword())) {
				 
					structure.setCode(HttpStatus.ACCEPTED.value());
					structure.setMessage("Login Success");
					structure.setData(management);
				}

			else {
				throw new Exception("Invalid Password");
			}
		}

		return structure;
	}

	public ResponseStructure<List<BankAccount>> fetchAllAccounts() throws MyException {
		ResponseStructure<List<BankAccount>> structure=new ResponseStructure<List<BankAccount>>();
		
		List<BankAccount> list=repository2.findAll();
		if(list.isEmpty()) {
			throw new MyException("No accounts are present");
		}
		else {
			structure.setCode(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);
		}
		return structure;
		
	}

	public ResponseStructure<BankAccount> changeStatus(long acno) {
		ResponseStructure<BankAccount> structure=new ResponseStructure<BankAccount>();
		Optional<BankAccount> optional=repository2.findById(acno);
		BankAccount account=optional.get();
		if(account.isStatus()) {
			account.setStatus(false);
		}
		else {
			account.setStatus(true);
		}
		structure.setCode(HttpStatus.OK.value());
		structure.setMessage("Changed successfully");
		structure.setData(repository2.save(account));
		return structure;
	}

	}


