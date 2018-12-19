package com.synthesis.migration.smartdashboard.entity.defaultdb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="MigrationHistory")
public class MigrationHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9171361402665876879L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="migrationId")
	private Long migrationId;
	
	@Column(name="migrationDescription")
	private String migrationDescription;
	
	@Column(name="clientCode")
	private String clientCode;

	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="migrationHistory")
	private List<EntityMigrationData> entityMigrationData = new ArrayList<>();
	
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

	public Long getMigrationId() {
		return migrationId;
	}

	public void setMigrationId(Long migrationId) {
		this.migrationId = migrationId;
	}

	public List<EntityMigrationData> getEntityMigrationData() {
		return entityMigrationData;
	}

	public void setEntityMigrationData(List<EntityMigrationData> entityMigrationData) {
		this.entityMigrationData = entityMigrationData;
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

	public String getMigrationDescription() {
		return migrationDescription;
	}

	public void setMigrationDescription(String migrationDescription) {
		this.migrationDescription = migrationDescription;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
	

}
