package com.example.true_home.exception;

import java.util.List;

/**
 * Exception class to be thrown during validation failures.
 */
public class trueHomeValidationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> errors;

	public trueHomeValidationException(List<String> errors) {
		super();
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	
}
