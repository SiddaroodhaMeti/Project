package com.project.banking_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.banking_system.helper.ResponseStructure;

@ControllerAdvice
public class ExceptionController {

	
	

		@ExceptionHandler(value = MyException.class)
		public ResponseEntity<ResponseStructure<String>> userDefinedExceptionHandler(MyException ie) {
			ResponseStructure<String> responseStructure = new ResponseStructure<String>();
			responseStructure.setCode(HttpStatus.NOT_ACCEPTABLE.value());
			responseStructure.setMessage("Request failed");
			responseStructure.setData(ie.toString());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_ACCEPTABLE);

		}

	}

