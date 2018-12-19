package com.synthesis.migration.smartdashboard.dao.defaultdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.synthesis.migration.smartdashboard.entity.defaultdb.EntityMaster;

public interface EntityMasterRepository extends CrudRepository<EntityMaster, Long>,JpaRepository<EntityMaster, Long>{

}
