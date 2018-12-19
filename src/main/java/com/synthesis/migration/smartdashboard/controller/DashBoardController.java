package com.synthesis.migration.smartdashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.synthesis.migration.smartdashboard.dto.EntityMasterDto;
import com.synthesis.migration.smartdashboard.dto.FalloutProgressChartDto;
import com.synthesis.migration.smartdashboard.exception.CustomValidationException;
import com.synthesis.migration.smartdashboard.service.DashBoardService;



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
	@RequestMapping(value = "/persistsFalloutDataFromSystem", method = RequestMethod.GET)
	public ResponseEntity<String> fetchAndPersistsFalloutDataFromSystem(HttpServletRequest httpServletRequest) throws CustomValidationException,Exception
	{
		return new ResponseEntity<>(dashBoardService.persistsFalloutDataFromSystem(), HttpStatus.OK);
	}


}
