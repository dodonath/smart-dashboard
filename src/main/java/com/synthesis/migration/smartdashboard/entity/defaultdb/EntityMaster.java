package com.synthesis.migration.smartdashboard.entity.defaultdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="ENTITY_MASTER")
public class EntityMaster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5159320078994649297L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="entityId")
	private Long entityId;
	
	@Column(name="entityType")
	private String entityType;
	
	@Column(name="entityDescription")
	private String entityDescription;
	
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
	
	@Column(name="entityCodeInValidation")
	private String entityCodeInValidation;
	
	@Column(name="entityCodeInTarget")
	private String entityCodeInTarget;


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

	public String getEntityDescription() {
		return entityDescription;
	}

	public void setEntityDescription(String entityDescription) {
		this.entityDescription = entityDescription;
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

	public String getEntityCodeInValidation() {
		return entityCodeInValidation;
	}

	public void setEntityCodeInValidation(String entityCodeInValidation) {
		this.entityCodeInValidation = entityCodeInValidation;
	}

	public String getEntityCodeInTarget() {
		return entityCodeInTarget;
	}

	public void setEntityCodeInTarget(String entityCodeInTarget) {
		this.entityCodeInTarget = entityCodeInTarget;
	}


	
	
	
	

}
