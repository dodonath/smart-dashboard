package com.synthesis.migration.smartdashboard.dto;

public class ErrorMasterDto {
	
	private Long errorId;
	
	private String errorCode;
	
	private String errorMessage;
	
	private EntityMasterDto entityMaster;
	
	private Boolean active;

	public Long getErrorId() {
		return errorId;
	}

	public void setErrorId(Long errorId) {
		this.errorId = errorId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public EntityMasterDto getEntityMaster() {
		return entityMaster;
	}

	public void setEntityMaster(EntityMasterDto entityMaster) {
		this.entityMaster = entityMaster;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
	

}
