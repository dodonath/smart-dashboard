package com.synthesis.migration.smartdashboard.dto;

public class FetchErrorRequestDto {
	
	private Long migrationId;
	private Long entityId;
	private String errorCode;
	private Integer pageSize;
	private Integer pageNumber;
	
	public Long getMigrationId() {
		return migrationId;
	}
	public void setMigrationId(Long migrationId) {
		this.migrationId = migrationId;
	}
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
	
	
	

}
