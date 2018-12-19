package com.synthesis.migration.smartdashboard.dto;

import java.util.List;

public class FalloutProgressChartDto 
{
	
	private Long migrationId;
	private String migrationDate;
	private String clientCode;
	private String migrationDescription;
	
	private List<EntityDto> entities;
	
	public static class EntityDto
	{
		private Long entityId;
		private String entityType;
		private List<ProgressionDto> progressionDetails;
		
		public Long getEntityId() {
			return entityId;
		}
		public void setEntityId(Long entityId) {
			this.entityId = entityId;
		}
		public String getEntityType() {
			return entityType;
		}
		public void setEntityType(String entityType) {
			this.entityType = entityType;
		}
		public List<ProgressionDto> getProgressionDetails() {
			return progressionDetails;
		}
		public void setProgressionDetails(List<ProgressionDto> progressionDetails) {
			this.progressionDetails = progressionDetails;
		}
		
		
		
		
	}
	
	public static class ProgressionDto
	{
		private Long environmentId;
		private String environmentType;
		private Long environmentCount;
		
		public Long getEnvironmentId() {
			return environmentId;
		}
		public void setEnvironmentId(Long environmentId) {
			this.environmentId = environmentId;
		}
		public String getEnvironmentType() {
			return environmentType;
		}
		public void setEnvironmentType(String environmentType) {
			this.environmentType = environmentType;
		}
		public Long getEnvironmentCount() {
			return environmentCount;
		}
		public void setEnvironmentCount(Long environmentCount) {
			this.environmentCount = environmentCount;
		}
		
		
	}

	public Long getMigrationId() {
		return migrationId;
	}

	public void setMigrationId(Long migrationId) {
		this.migrationId = migrationId;
	}

	public String getMigrationDate() {
		return migrationDate;
	}

	public void setMigrationDate(String migrationDate) {
		this.migrationDate = migrationDate;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getMigrationDescription() {
		return migrationDescription;
	}

	public void setMigrationDescription(String migrationDescription) {
		this.migrationDescription = migrationDescription;
	}

	public List<EntityDto> getEntities() {
		return entities;
	}

	public void setEntities(List<EntityDto> entities) {
		this.entities = entities;
	}
	
	
	
	
	
	
	

}
