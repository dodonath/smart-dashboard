package com.synthesis.migration.smartdashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.synthesis.migration.smartdashboard.dto.EntityMasterDto;
import com.synthesis.migration.smartdashboard.dto.FalloutProgressChartDto;
import com.synthesis.migration.smartdashboard.dto.FetchErrorRequestDto;
import com.synthesis.migration.smartdashboard.dto.FetchErrorResponseDto;
import com.synthesis.migration.smartdashboard.dto.TalendErrorDetailsDto;
import com.synthesis.migration.smartdashboard.dto.ValidationChartDto;
import com.synthesis.migration.smartdashboard.exception.CustomValidationException;
import com.synthesis.migration.smartdashboard.service.DashBoardService;
import com.synthesis.migration.smartdashboard.util.ConfigUtil;



@RestController
@RequestMapping(value = "/dashboard")
public class DashBoardController {


	@Autowired
	private DashBoardService dashBoardService;

	@CrossOrigin
	@RequestMapping(value = "/fetchProgressChart",  method = RequestMethod.GET)
	public ResponseEntity<List<FalloutProgressChartDto>> fetchProgressChart(HttpServletRequest httpServletRequest) throws CustomValidationException
	{

		List<FalloutProgressChartDto> fallouts = dashBoardService.fetchFalloutDataFromSmart();
		return new ResponseEntity<>(fallouts, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/fetchEntityData", method = RequestMethod.GET)
	public ResponseEntity<List<EntityMasterDto>> fetchEntityData(HttpServletRequest httpServletRequest) throws CustomValidationException
	{
		List<EntityMasterDto> entities = dashBoardService.fetchEntityData();;
		return new ResponseEntity<>(entities, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/fetchErrorData", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<FetchErrorResponseDto> fetchErrorData(@RequestBody(required = false) FetchErrorRequestDto request,
			HttpServletRequest httpServletRequest) throws CustomValidationException
	{
		FetchErrorResponseDto errors = dashBoardService.fetchErrorData(request);
		return new ResponseEntity<>(errors, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/downloadErrorData", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST,produces="text/csv")
	public void downloadErrorData(@RequestBody FetchErrorRequestDto request,
			HttpServletRequest httpServletRequest,HttpServletResponse response) throws CustomValidationException,Exception
	{

		String csvFileName = "errors.csv";

		response.setContentType("text/csv");

		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",csvFileName);
		response.setHeader(headerKey, headerValue);
		FetchErrorResponseDto errors = dashBoardService.fetchErrorData(request);
		ConfigUtil.writeBeanToCsv(response.getWriter(), errors.getErrors(), TalendErrorDetailsDto.class);
	}


	@CrossOrigin
	@RequestMapping(value = "/persistsAllData", method = RequestMethod.GET)
	public ResponseEntity<String> persistsAllData(HttpServletRequest httpServletRequest) throws CustomValidationException,Exception
	{
		return new ResponseEntity<>(dashBoardService.persistsAllData(), HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/fetchMigrationValidationData", method = RequestMethod.GET)
	public ResponseEntity<List<ValidationChartDto>> fetchMigrationValidationData(HttpServletRequest httpServletRequest) throws CustomValidationException,Exception
	{
		return new ResponseEntity<>(dashBoardService.fetchMigrationValidationData(), HttpStatus.OK);
	}



}
