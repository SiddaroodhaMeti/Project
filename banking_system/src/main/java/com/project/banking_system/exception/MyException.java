package com.project.banking_system.exception;

public class MyException extends Exception {

	String msg = "";

	public MyException(String msg) {
		this.msg = msg;
	}

	public MyException() {

	}

	@Override
	public String toString() {

		return msg;
	}
}
