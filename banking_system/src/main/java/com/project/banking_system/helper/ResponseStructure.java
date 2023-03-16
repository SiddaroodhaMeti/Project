package com.project.banking_system.helper;

import com.project.banking_system.dto.Customer;

import lombok.Data;

@Data
public class ResponseStructure<T> {

	int code;
	String message;
	T data;
}
