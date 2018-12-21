package com.synthesis.migration.smartdashboard.dao.defaultdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.synthesis.migration.smartdashboard.entity.defaultdb.DataValidationDetails;

public interface DataValidationRepository extends CrudRepository<DataValidationDetails, Long>,JpaRepository<DataValidationDetails, Long>{

}

