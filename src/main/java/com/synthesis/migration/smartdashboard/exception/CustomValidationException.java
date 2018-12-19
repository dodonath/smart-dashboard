package com.synthesis.migration.smartdashboard.exception;

public class CustomValidationException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8004236195982982934L;
	private final ErrorCode errorCode;

	public CustomValidationException(ErrorCode codes) {
		super(getMessage(codes));
		this.errorCode = codes;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	private static String getMessage(ErrorCode errorCode) {
		if (errorCode.getMessage() != null)
			return errorCode.getMessage();
		else
			return null;
	}
}
