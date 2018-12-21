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

@Table
@Entity(name="DATA_REJECTION_DETAILS")
public class DataRejectionDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3137448528882353038L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rejectionId")
	private Long rejectionId;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entityId")
	private EntityMaster entity;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="migrationId")
	private MigrationHistory migrationHistory;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="errorId")
	private ErrorMaster errorMaster;
	
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
	
	@Column(name="rejectionAccountId")
	private Long rejectionAccountId; 
	
	@Column(name="source")
	private String source;

	public Long getRejectionId() {
		return rejectionId;
	}

	public void setRejectionId(Long rejectionId) {
		this.rejectionId = rejectionId;
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

	public ErrorMaster getErrorMaster() {
		return errorMaster;
	}

	public void setErrorMaster(ErrorMaster errorMaster) {
		this.errorMaster = errorMaster;
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

	public Long getRejectionAccountId() {
		return rejectionAccountId;
	}

	public void setRejectionAccountId(Long rejectionAccountId) {
		this.rejectionAccountId = rejectionAccountId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	
	
}
