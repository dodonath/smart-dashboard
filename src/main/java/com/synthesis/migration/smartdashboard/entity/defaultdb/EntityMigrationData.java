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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="EntityMigrationData")
public class EntityMigrationData implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 202360470663098418L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="entityMigrationId")
	private Long entityMigrationId;
	
	@Column(name="entityMigrationDescription")
	private String entityMigrationDescription;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="entityId")
	private EntityMaster entityMaster;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="entityMigrationData")
	private List<EnvironmentValueDetails> values = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="migrationId")
	private MigrationHistory migrationHistory;
	
	
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

	public Long getEntityMigrationId() {
		return entityMigrationId;
	}

	public void setEntityMigrationId(Long entityMigrationId) {
		this.entityMigrationId = entityMigrationId;
	}


	
	
	public String getEntityMigrationDescription() {
		return entityMigrationDescription;
	}

	public void setEntityMigrationDescription(String entityMigrationDescription) {
		this.entityMigrationDescription = entityMigrationDescription;
	}

	public EntityMaster getEntityMaster() {
		return entityMaster;
	}

	public void setEntityMaster(EntityMaster entityMaster) {
		this.entityMaster = entityMaster;
	}

	public List<EnvironmentValueDetails> getValues() {
		return values;
	}

	public void setValues(List<EnvironmentValueDetails> values) {
		this.values = values;
	}


	
	
	public MigrationHistory getMigrationHistory() {
		return migrationHistory;
	}

	public void setMigrationHistory(MigrationHistory migrationHistory) {
		this.migrationHistory = migrationHistory;
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
	
	
	

}
