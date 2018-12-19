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
@Table(name="EnvironmentValueDetails")
public class EnvironmentValueDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1984380601833111939L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="historyId")
	private Long historyId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="environmentId")
	private EnvironmentDetails environmentDetails;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entityMigrationId")
	private EntityMigrationData entityMigrationData;
	
	@Column(name="environmentCount")
	private Long environmentCount;
	
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
	
	

	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	

	public Long getEnvironmentCount() {
		return environmentCount;
	}

	public void setEnvironmentCount(Long environmentCount) {
		this.environmentCount = environmentCount;
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

	public EnvironmentDetails getEnvironmentDetails() {
		return environmentDetails;
	}

	public void setEnvironmentDetails(EnvironmentDetails environmentDetails) {
		this.environmentDetails = environmentDetails;
	}

	public EntityMigrationData getEntityMigrationData() {
		return entityMigrationData;
	}

	public void setEntityMigrationData(EntityMigrationData entityMigrationData) {
		this.entityMigrationData = entityMigrationData;
	}

	

	
	
	
	
	
	

}
