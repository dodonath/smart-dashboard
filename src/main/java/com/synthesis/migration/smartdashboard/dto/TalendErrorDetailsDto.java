package com.synthesis.migration.smartdashboard.dto;

public class TalendErrorDetailsDto {
	
	private Long rejectionAccountId;
	private String errorCode;
	
	
	public Long getRejectionAccountId() {
		return rejectionAccountId;
	}
	public void setRejectionAccountId(Long rejectionAccountId) {
		this.rejectionAccountId = rejectionAccountId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
	public String toString()
	{
		return "TalendErrorDetailsDto = { \n"
				+ "  rejectionAccountId = " + rejectionAccountId + " , \n"
				+ "  errorCode = " + errorCode + "  \n"
				+ "}";
	}
	

}
