package com.backendbyte.userauth.exception;

public class EntityNotSaveException extends RuntimeException{
	
	public EntityNotSaveException(String message, Throwable cause) {
		super(message, cause);
	}

}
