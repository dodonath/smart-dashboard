package com.synthesis.migration.smartdashboard.dao.defaultdb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.synthesis.migration.smartdashboard.entity.defaultdb.EnvironmentValueDetails;

public interface EnvironmentDetailsValueRepository extends CrudRepository<EnvironmentValueDetails, Long>,JpaRepository<EnvironmentValueDetails, Long>{

	/*
	@Query("select m.migrationId,m.migrationDescription, " + 
			"emd.entityMaster.entityId,emd.entityMaster.entityType, " + 
			"env.environmentDetails.environmentId,env.environmentDetails.environmentType, " + 
			"env.environmentCount from MigrationHistory m " + 
			"inner join m.entityMigrationData emd " + 
			"inner join emd.values env " + 
			"inner join emd.entityMaster em  " + 
			"inner join env.environmentDetails en " +
			"where " + 
			"emd.entityMaster.entityId = :entityId order by m.createdAt desc ")
	public List<Object[]> findDetailsByEntityIdCustomQuery(@Param("entityId") Long entityId);*/
	
	
	
	
	@Query("select m.migrationId,m.migrationDescription, " + 
			"emd.entityMaster.entityId,emd.entityMaster.entityType, " + 
			"env.environmentDetails.environmentId,env.environmentDetails.environmentType, " + 
			"env.environmentCount ,m.clientCode , m.createdAt from MigrationHistory m " + 
			"inner join m.entityMigrationData emd " + 
			"inner join emd.values env " + 
			/*"inner join emd.entityMaster em  " + 
			"inner join env.environmentDetails en " +
			"where " + 
			"emd.entityMaster.entityId = :entityId " +*/
			"order by m.createdAt asc,m.migrationId asc,emd.entityMaster.entityId asc,env.environmentDetails.environmentDisplayOrder asc")
	public List<Object[]> findDetailsByEntityIdCustomQuery();
}
