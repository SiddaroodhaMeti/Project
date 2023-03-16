package com.project.banking_system.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Login {

	int id;
	String password;
}
