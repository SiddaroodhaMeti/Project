package com.project.banking_system.service;

import java.time.LocalDate;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.banking_system.dto.BankAccount;
import com.project.banking_system.dto.Customer;
import com.project.banking_system.dto.Login;
import com.project.banking_system.exception.MyException;
import com.project.banking_system.helper.MAilVerification;
import com.project.banking_system.helper.ResponseStructure;
import com.project.banking_system.repository.BankRepository;
import com.project.banking_system.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	CustomerRepository repository;
	@Autowired
	MAilVerification mailverification;

	@Autowired
	BankAccount account;

	public ResponseStructure<Customer> save(Customer customer) {
		ResponseStructure<Customer> structure = new ResponseStructure<>();
		int age = Period.between(customer.getDob().toLocalDate(), LocalDate.now()).getYears();
		customer.setAge(age);
		if (age < 18) {
			structure.setMessage("You should be 18+ years old");
			structure.setCode(HttpStatus.CONFLICT.value());
			structure.setData(customer);
		} else {
			Random random = new Random();
			int otp = random.nextInt(100000, 999999);
			customer.setOtp(otp);

//			mailverification.sendMail(customer);

			structure.setMessage("Verification mail sent");
			structure.setCode(HttpStatus.PROCESSING.value());
			structure.setData(repository.save(customer));
		}

		return structure;
	}

	public ResponseStructure<Customer> verify(int cust_id, int otp) throws MyException {
		ResponseStructure<Customer> structure = new ResponseStructure<>();

		Optional<Customer> optional = repository.findById(cust_id);
		if (optional.isEmpty()) {

			throw new MyException("Check Id and try again");
		} else {
			Customer customer = optional.get();
			if (customer.getOtp() == otp) {
				structure.setCode(HttpStatus.CREATED.value());
				structure.setMessage("Account created successfully");
				customer.setStatus(true);
				structure.setData(repository.save(customer));
			} else {
				throw new MyException("OTP mismatch");
			}
		}
		return structure;
	}

	public ResponseStructure<Customer> login(Login login) throws MyException {
		ResponseStructure<Customer> structure = new ResponseStructure<>();
		Optional<Customer> optional = repository.findById(login.getId());
		if (optional.isEmpty()) {

			throw new MyException("Invalid Customer id");
		} else {
			Customer customer = optional.get();
			if (customer.getPassword().equals(login.getPassword())) {
				if (customer.isStatus()) {
					structure.setCode(HttpStatus.ACCEPTED.value());
					structure.setMessage("Login Success");
					structure.setData(customer);
				} else {
					throw new MyException("Verify your email first");
				}

			} else {
				throw new MyException("Invalid Password");
			}
		}

		return structure;
	}

	public ResponseStructure<Customer> createAccount(int cust_id, String type) throws MyException {
		ResponseStructure<Customer> structure = new ResponseStructure<Customer>();

		Optional<Customer> optional = repository.findById(cust_id);
		if (optional.isEmpty()) {

			throw new MyException("Invalid Customer id");
		} else {
			Customer customer = optional.get();
			List<BankAccount> list = customer.getAccounts();
			boolean flag = true;
			for (BankAccount account : list) {
				if (account.getType().equals(type)) {
					flag = false;
					break;
				}
			}
			if (!flag) {
				throw new MyException(type + " Account already exists");
			} else {
				account.setType(type);
				if (type.equals("savings")) {
					account.setBanklimit(5000);

				} else {
					account.setBanklimit(10000);
				}

				list.add(account);
				customer.setAccounts(list);
			}

			structure.setCode(HttpStatus.ACCEPTED.value());
			structure.setMessage("Account created wait for management approval...");
			structure.setData(repository.save(customer));

		}

		return structure;
	}

	public ResponseStructure<List<BankAccount>> fetchAll(int cust_id) throws MyException {
		ResponseStructure<List<BankAccount>> structure = new ResponseStructure<List<BankAccount>>();

		Optional<Customer> optional = repository.findById(cust_id);
		Customer customer = optional.get();
		List<BankAccount> list = customer.getAccounts();

		List<BankAccount> res = new ArrayList<BankAccount>();
		for (BankAccount account : list) {
			if (account.isStatus()) {
				res.add(account);
			}
		}
		if (res.isEmpty()) {
			throw new MyException("No Active accounts found");
		} else {
			structure.setCode(HttpStatus.FOUND.value());
			structure.setMessage("accounts found");
			structure.setData(res);

		}
		return structure;
	}

	public ResponseStructure<Double> checkBalance(long acno) {
		// TODO Auto-generated method stub
		ResponseStructure<Double> structure=new ResponseStructure<Double>();
		
		Optional<BankAccount> optional=BankRepository.findById(acno);
		BankAccount account=optional.get();
		
		structure.setCode(HttpStatus.FOUND.value());
		structure.setMessage("Data found");
		structure.setData(account.getAmount());
		
		
		return structure;
	}

}
