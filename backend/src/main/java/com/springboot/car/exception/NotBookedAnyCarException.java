package com.springboot.car.exception;

public class NotBookedAnyCarException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String message;

	public NotBookedAnyCarException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	} 
	
}
