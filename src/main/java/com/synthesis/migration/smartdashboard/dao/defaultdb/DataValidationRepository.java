package com.synthesis.migration.smartdashboard.dao.defaultdb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.synthesis.migration.smartdashboard.entity.defaultdb.DataValidationDetails;

public interface DataValidationRepository extends CrudRepository<DataValidationDetails, Long>,JpaRepository<DataValidationDetails, Long>{

	
	@Query("select " + 
			"dv.migrationHistory.migrationId,m.createdAt," + 
			"m.clientCode,m.migrationDescription, " + 
			"dv.entity.entityId,en.entityType, " + 
			"dv.collectedCount,dv.validatedCount,dv.transformedCount,dv.loadedCount " + 
			"from DataValidationDetails dv " + 
			"join dv.migrationHistory m " + 
			"join dv.entity en " + 
			"where m.migrationId = (select max(dv1.migrationHistory.migrationId) from DataValidationDetails dv1) " )
	 List<Object[]> findLatestValidationDetails();
	
}

