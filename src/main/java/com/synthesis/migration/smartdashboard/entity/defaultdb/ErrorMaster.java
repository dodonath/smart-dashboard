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
@Table(name="ERROR_MASTER")
public class ErrorMaster implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6298723166406490989L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="errorId")
	private Long errorId;
	
	@Column(name="errorCode")
	private String errorCode;
	
	@Column(name="errorMessage")
	private String errorMessage;
	
	
	@Column(name="source")
	private String source;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entityId")
	private EntityMaster entityMaster;
	
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

	public EntityMaster getEntityMaster() {
		return entityMaster;
	}

	public void setEntityMaster(EntityMaster entityMaster) {
		this.entityMaster = entityMaster;
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
	
	

}
