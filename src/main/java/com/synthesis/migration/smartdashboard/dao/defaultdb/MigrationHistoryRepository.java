package com.synthesis.migration.smartdashboard.dao.defaultdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.synthesis.migration.smartdashboard.entity.defaultdb.MigrationHistory;

public interface MigrationHistoryRepository extends CrudRepository<MigrationHistory, Long>,JpaRepository<MigrationHistory, Long>{

	
	public MigrationHistory findTopByOrderByCreatedAtDesc();
}
