package edu.les.exception;

import java.sql.BatchUpdateException;

public class ExceptionHandler extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;	
	
	public ExceptionHandler() {
		super();
	}
	
	public ExceptionHandler(String message) {
		this.message = message;
	}
	
	public ExceptionHandler(Exception thrownException) {
		if (thrownException instanceof BatchUpdateException) {
			BatchUpdateException e = (BatchUpdateException) thrownException;
			int error = e.getErrorCode();
			switch (error) {
			case 1406:
				this.message = "Coluna muito longa";
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
	
	
}
