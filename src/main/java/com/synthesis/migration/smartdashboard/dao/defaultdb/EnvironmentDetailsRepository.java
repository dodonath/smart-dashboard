package com.synthesis.migration.smartdashboard.dao.defaultdb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.synthesis.migration.smartdashboard.entity.defaultdb.EnvironmentDetailsMaster;

public interface EnvironmentDetailsRepository extends CrudRepository<EnvironmentDetailsMaster, Long>,JpaRepository<EnvironmentDetailsMaster, Long>{

	List<EnvironmentDetailsMaster> findAllByOrderByEnvironmentDisplayOrderAsc();

}
