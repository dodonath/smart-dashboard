package com.synthesis.migration.smartdashboard.dao.defaultdb;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.synthesis.migration.smartdashboard.dto.TalendErrorDetailsDto;
import com.synthesis.migration.smartdashboard.entity.defaultdb.DataRejectionDetails;

public interface DataRejectionDetailsRepository extends CrudRepository<DataRejectionDetails, Long>,JpaRepository<DataRejectionDetails, Long>,PagingAndSortingRepository<DataRejectionDetails, Long>{

	@Query("select new com.synthesis.migration.smartdashboard.dto.TalendErrorDetailsDto(d.rejectionAccountId,d.errorMaster.errorCode,"
			+ "d.errorMaster.errorMessage ) "
			+ "from com.synthesis.migration.smartdashboard.entity.defaultdb.DataRejectionDetails d "
			+ "where "
			+ "d.migrationHistory.migrationId=:migrationId and "
			+ "d.entity.entityId=:entityId and ((:errorCode IS NULL ) OR (d.errorMaster.errorCode=:errorCode )) "
			//+ "d.errorMaster.errorCode=:errorCode "
			+ "")
	 Page<TalendErrorDetailsDto> findErrorDetails(
			 @Param("migrationId") Long migrationId, 
			 @Param("entityId")Long entityId,
			 @Param("errorCode")String errorCode,
			 Pageable pagingData);
	
	
	
	@Query("select distinct dr.migrationHistory.migrationId,dr.entity.entityId,dr.errorMaster.errorId,em.errorCode, " + 
			"(select count(errorId) from com.synthesis.migration.smartdashboard.entity.defaultdb.DataRejectionDetails where errorId = dr.errorMaster.errorId) " + 
			"from com.synthesis.migration.smartdashboard.entity.defaultdb.DataRejectionDetails dr " + 
			"join dr.migrationHistory m " + 
			"join dr.entity en " + 
			"join dr.errorMaster em " + 
			"where m.migrationId = (select max(dr1.migrationHistory.migrationId) from com.synthesis.migration.smartdashboard.entity.defaultdb.DataRejectionDetails dr1) " + 
			"and dr.source=:source ")
	 List<Object[]> findLatestRejectionDetails( @Param("source")String errorCode);
	
}

