package com.onerb.apiattornatus.error;

public class ErrorApi {
	
	private String errorMessage;
	
	private int errorCode;
	
	public ErrorApi() {
		super();
		
	}

	public ErrorApi(String errorMessage, int errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	} 
	
	
	

}
