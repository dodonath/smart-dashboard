package com.synthesis.migration.smartdashboard.service;

import java.util.List;

import com.synthesis.migration.smartdashboard.dto.EntityMasterDto;
import com.synthesis.migration.smartdashboard.dto.EnvironmentDetailsDto;
import com.synthesis.migration.smartdashboard.dto.FalloutProgressChartDto;

public interface DashBoardService {

	
	String persistsFalloutDataFromSystem() throws Exception;

	List<FalloutProgressChartDto> fetchFalloutDataFromSmart();

	List<EntityMasterDto> fetchEntityData();

	//Long fetchOminaData(String sql);

	List<EnvironmentDetailsDto> fetchEnvironmentData();

	Long fetchCsrData(String sql);

	Long fetchRbmData(String sql);

	Long fetchOmniaData(String sql);
	
	

}
