package com.skillstorm.models;

public class NotFound {
	
	private String message;
	
	public NotFound() {
		
	}
	
	public NotFound(String message) {
		setMessage(message);
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
