package com.example.true_home.dto;

import java.util.Date;
import java.util.List;

/**
 * Class exposing parameters detailing error.
 */

public class ErrorDetailBO {
	
	private String code;
	private String message;
	private List<String> validationErrors;
	private String details;
	private Date timestamp;
		
	public ErrorDetailBO(Date timestamp, String details) {
		super();
		this.details = details;
		this.timestamp = timestamp;
	}
	
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
	public List<String> getValidationErrors() {
		return validationErrors;
	}
	public void setValidationErrors(List<String> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
