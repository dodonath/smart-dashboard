package com.synthesis.migration.smartdashboard.dto;

import java.util.List;

public class ValidationChartDto {

	private Long migrationId;
	private String migrationDate;
	private String clientCode;
	private String migrationDescription;
	
	private List<EntityValidationDto> entities;
	
	public static class EntityValidationDto {
		
		private Long entityId;
		private String entityType;
		private ValidationStatusDto migrationStats;
		private List<ValdationErrorDto> errors;
		
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
		
		public ValidationStatusDto getMigrationStats() {
			return migrationStats;
		}
		public void setMigrationStats(ValidationStatusDto migrationStats) {
			this.migrationStats = migrationStats;
		}
		public List<ValdationErrorDto> getErrors() {
			return errors;
		}
		public void setErrors(List<ValdationErrorDto> errors) {
			this.errors = errors;
		}
		
		
	}
	
	public static class ValdationErrorDto{
		
		private String errorCode;
		private Long errorId;
		private Long errorCount;
		
		public String getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
		public Long getErrorId() {
			return errorId;
		}
		public void setErrorId(Long errorId) {
			this.errorId = errorId;
		}
		public Long getErrorCount() {
			return errorCount;
		}
		public void setErrorCount(Long errorCount) {
			this.errorCount = errorCount;
		}
		
		
	}
	
	
	public static class ValidationStatusDto{
		
		private Long collected;
		private Long validated;
		private Long transformed;
		private Long loaded;
		
		public Long getCollected() {
			return collected;
		}
		public void setCollected(Long collected) {
			this.collected = collected;
		}
		public Long getValidated() {
			return validated;
		}
		public void setValidated(Long validated) {
			this.validated = validated;
		}
		public Long getTransformed() {
			return transformed;
		}
		public void setTransformed(Long transformed) {
			this.transformed = transformed;
		}
		public Long getLoaded() {
			return loaded;
		}
		public void setLoaded(Long loaded) {
			this.loaded = loaded;
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


	public List<EntityValidationDto> getEntities() {
		return entities;
	}


	public void setEntities(List<EntityValidationDto> entities) {
		this.entities = entities;
	}
	
	
	
}
