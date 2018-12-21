package com.synthesis.migration.smartdashboard.dto;



public class TalendErrorDetailsDto {
	
	private Long rejectionAccountId;
	private String errorCode;
	private String errorMessage;
	
	public TalendErrorDetailsDto()
	{
		
	}
	
	public TalendErrorDetailsDto(Long rejectionAccountId,String errorCode,String errorMessage)
	{
		this.rejectionAccountId = rejectionAccountId;
		this.errorCode = errorCode;
		this.errorMessage=errorMessage;
	}
	
	
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
				+ "  errorMessage = " + errorMessage + " , \n"
				+ "  errorCode = " + errorCode + "  \n"
				+ "}";
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	

}
