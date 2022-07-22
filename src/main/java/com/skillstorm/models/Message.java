package com.skillstorm.models;

/**
 * Class used for sending error messages to the front-end
 */
public class Message {
	
	private String message;
	
	public Message() {
		
	}
	
	public Message(String message) {
		setMessage(message);
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
