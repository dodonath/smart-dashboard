package com.synthesis.migration.smartdashboard.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String ERROR = "Oops!";

	
	@ExceptionHandler(value = CustomValidationException.class)
	public ResponseEntity<ErrorMessage> handleBaseException(Exception ex) {
		logger.error(ERROR, ex);
		CustomValidationException validationException = (CustomValidationException) ex;
		ErrorHandle errorHandle = validationException.getErrorCode();
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(errorHandle.getErrorCode());
		errorMessage.setMessage(errorHandle.getMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({Exception.class, RuntimeException.class})
	public ResponseEntity<ErrorMessage> handleRuntimeException(Exception ex) {
		logger.error(ERROR, ex);
		ErrorHandle errorHandle = ErrorCode.DASHBOARD_2001;
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(errorHandle.getErrorCode());
		errorMessage.setMessage(errorHandle.getMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
