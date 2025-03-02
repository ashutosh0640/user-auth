package com.backendbyte.userauth.exception;

public class EntityNotSaveException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotSaveException(String message, Throwable cause) {
		super(message, cause);
	}

}
