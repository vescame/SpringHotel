package edu.les.exception;

import java.sql.BatchUpdateException;
import java.util.List;

public class ExceptionHandler extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private List<String> fieldsWithError;

	public ExceptionHandler() {
		message = super.getMessage();
	}

	public ExceptionHandler(List<String> fieldsWithError) {
		this.fieldsWithError = fieldsWithError;
		this.message = "Fields with error: " + fieldsWithError.toString();
	}

	public ExceptionHandler(Exception sqlThrownException) {
		if (sqlThrownException instanceof BatchUpdateException) {
			BatchUpdateException e = (BatchUpdateException) sqlThrownException;
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

	public List<String> getErrors() {
		return fieldsWithError;
	}

}
