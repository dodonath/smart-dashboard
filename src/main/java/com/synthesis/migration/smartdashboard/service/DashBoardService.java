package com.synthesis.migration.smartdashboard.service;

import java.util.List;

import com.synthesis.migration.smartdashboard.dto.EntityMasterDto;
import com.synthesis.migration.smartdashboard.dto.EnvironmentDetailsDto;
import com.synthesis.migration.smartdashboard.dto.ErrorMasterDto;
import com.synthesis.migration.smartdashboard.dto.FalloutProgressChartDto;
import com.synthesis.migration.smartdashboard.dto.FetchErrorRequestDto;
import com.synthesis.migration.smartdashboard.dto.FetchErrorResponseDto;
import com.synthesis.migration.smartdashboard.entity.defaultdb.MigrationHistory;
import com.synthesis.migration.smartdashboard.exception.CustomValidationException;

public interface DashBoardService {

	
	List<FalloutProgressChartDto> fetchFalloutDataFromSmart();

	List<EntityMasterDto> fetchEntityData();

	//Long fetchOminaData(String sql);

	List<EnvironmentDetailsDto> fetchEnvironmentData();

	Long fetchCsrData(String sql);

	Long fetchRbmData(String sql);

	Long fetchOmniaData(String sql);


	List<ErrorMasterDto> fetchErrorMasterData();

	String persistsAllData() throws CustomValidationException, Exception;

	Boolean persistsTalendLogsIntoSystem(MigrationHistory history, Long timestamp)
			throws CustomValidationException, Exception;

	Boolean persistsFalloutDataFromSystem(MigrationHistory history, Long timestamp)
			throws Exception, CustomValidationException;

	FetchErrorResponseDto fetchErrorData(FetchErrorRequestDto request);
	
	

}
