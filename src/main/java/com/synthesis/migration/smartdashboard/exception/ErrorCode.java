package com.synthesis.migration.smartdashboard.exception;

public enum ErrorCode implements ErrorHandle {
	DASHBOARD_2001(2001, "Oops!!! Something went wrong, Please contact administrator"),
	DASHBOARD_2002(2002, "Unable to save fetch data");	
	

	private final int code;
	private final String message;

	ErrorCode(int errorCode, String message) {
		this.code = errorCode;
		this.message = message;
	}

	@Override
	public int getErrorCode() {

		return this.code;
	}

	@Override
	public String getMessage() {

		return this.message;
	}

}
