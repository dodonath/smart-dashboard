package com.synthesis.migration.smartdashboard.entity.defaultdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="DATA_VALIDATION_DETAILS")
public class DataValidationDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7125898131152792661L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="validationId")
	private Long validationId;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entityId")
	private EntityMaster entity;
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="migrationId")
	private MigrationHistory migrationHistory;
	
	@Column(name="collectedCount")
	private Long collectedCount;
	
	@Column(name="validatedCount")
	private Long validatedCount;
	
	@Column(name="transformedCount")
	private Long transformedCount;
	
	@Column(name="loadedCount")
	private Long loadedCount;
	
	@Column(name="startedAt")
	private Long startedAt;
	
	@Column(name="endedAt")
	private Long endedAt;
	
	
	@Column(name="createdBy")
	private String createdBy;
	
	@Column(name="createdAt")
	private Long createdAt;
	
	@Column(name="updatedBy")
	private String updatedBy;
	
	@Column(name="updatedAt")
	private Long updatedAt;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "active")
	private Boolean active;
	
	@Column(name="source")
	private String source;

	public Long getValidationId() {
		return validationId;
	}

	public void setValidationId(Long validationId) {
		this.validationId = validationId;
	}

	public EntityMaster getEntity() {
		return entity;
	}

	public void setEntity(EntityMaster entity) {
		this.entity = entity;
	}

	public MigrationHistory getMigrationHistory() {
		return migrationHistory;
	}

	public void setMigrationHistory(MigrationHistory migrationHistory) {
		this.migrationHistory = migrationHistory;
	}

	public Long getCollectedCount() {
		return collectedCount;
	}

	public void setCollectedCount(Long collectedCount) {
		this.collectedCount = collectedCount;
	}

	public Long getValidatedCount() {
		return validatedCount;
	}

	public void setValidatedCount(Long validatedCount) {
		this.validatedCount = validatedCount;
	}

	public Long getTransformedCount() {
		return transformedCount;
	}

	public void setTransformedCount(Long transformedCount) {
		this.transformedCount = transformedCount;
	}

	public Long getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Long startedAt) {
		this.startedAt = startedAt;
	}

	public Long getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(Long endedAt) {
		this.endedAt = endedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getLoadedCount() {
		return loadedCount;
	}

	public void setLoadedCount(Long loadedCount) {
		this.loadedCount = loadedCount;
	}
	
	
	
	
}
