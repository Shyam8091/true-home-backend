package com.example.true_home.exception;

/**
 * Exception class to be used as a generic exception across system.
 * 
 * 
 *
 */
public class trueHomeException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	private String message;

	public trueHomeException() {
		super();
	}

	public trueHomeException(Throwable e) {
		super(e);
	}

	public trueHomeException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public trueHomeException(String code, String message,Throwable e) {
		super(message,e);
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
