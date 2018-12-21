package com.synthesis.migration.smartdashboard.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.synthesis.migration.smartdashboard.dao.csrdb.CsrRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.DataRejectionDetailsRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.DataValidationRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EntityMasterRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EntityMigrationRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EnvironmentDetailsRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EnvironmentDetailsValueRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.ErrorMasterRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.MigrationHistoryRepository;
import com.synthesis.migration.smartdashboard.dao.omniadb.OminaRepositoryCustom;
import com.synthesis.migration.smartdashboard.dao.rbmdb.RbmRepository;
import com.synthesis.migration.smartdashboard.dto.ConfigDto.FalloutDto;
import com.synthesis.migration.smartdashboard.dto.EntityMasterDto;
import com.synthesis.migration.smartdashboard.dto.EnvironmentDetailsDto;
import com.synthesis.migration.smartdashboard.dto.ErrorMasterDto;
import com.synthesis.migration.smartdashboard.dto.FalloutProgressChartDto;
import com.synthesis.migration.smartdashboard.dto.FalloutProgressChartDto.EntityDto;
import com.synthesis.migration.smartdashboard.dto.FalloutProgressChartDto.ProgressionDto;
import com.synthesis.migration.smartdashboard.dto.FetchErrorRequestDto;
import com.synthesis.migration.smartdashboard.dto.FetchErrorResponseDto;
import com.synthesis.migration.smartdashboard.dto.TalendErrorDetailsDto;
import com.synthesis.migration.smartdashboard.dto.TalendJobDetailsDto;
import com.synthesis.migration.smartdashboard.entity.defaultdb.DataRejectionDetails;
import com.synthesis.migration.smartdashboard.entity.defaultdb.DataValidationDetails;
import com.synthesis.migration.smartdashboard.entity.defaultdb.EntityMaster;
import com.synthesis.migration.smartdashboard.entity.defaultdb.EntityMigrationData;
import com.synthesis.migration.smartdashboard.entity.defaultdb.EnvironmentDetailsMaster;
import com.synthesis.migration.smartdashboard.entity.defaultdb.EnvironmentValueDetails;
import com.synthesis.migration.smartdashboard.entity.defaultdb.ErrorMaster;
import com.synthesis.migration.smartdashboard.entity.defaultdb.MigrationHistory;
import com.synthesis.migration.smartdashboard.exception.CustomValidationException;
import com.synthesis.migration.smartdashboard.service.DashBoardService;
import com.synthesis.migration.smartdashboard.util.ConfigUtil;


@Service
public class DashBoardServiceImpl implements DashBoardService {


	private static final String ADMIN = "Admin";
	private static final String TALEND_SOURCE = "Talend";

	@Autowired
	private EntityMasterRepository entityMasterRepository;

	@Autowired
	private CsrRepository csrRepository;

	@Autowired
	private RbmRepository rbmRepository;

	@Autowired
	private OminaRepositoryCustom ominaRepository;

	@Autowired
	private EnvironmentDetailsValueRepository environmentDetailsValueRepository;

	@Autowired
	private EnvironmentDetailsRepository environmentDetailsRepository;

	@Autowired
	private MigrationHistoryRepository migrationHistoryRepository;

	@Autowired
	private EntityMigrationRepository entityMigrationRepository;

	@Autowired
	private ErrorMasterRepository errorMasterRepository;

	@Autowired
	private DataValidationRepository dataValidationRepository;

	@Autowired
	private DataRejectionDetailsRepository dataRejectionRepository;

	@Override
	public List<FalloutProgressChartDto> fetchFalloutDataFromSmart()
	{
		List<FalloutProgressChartDto> list = new ArrayList<>();
		List<Object[]> repoData = environmentDetailsValueRepository.findDetailsByEntityIdCustomQuery();
		populateToDashBoardData(repoData,list);
		return list;
	}

	private void populateToDashBoardData(List<Object[]> repoData, List<FalloutProgressChartDto> list) 
	{
		if(repoData!=null)
		{
			FalloutProgressChartDto fallOutDto = null;
			Map<Long,FalloutProgressChartDto> migrationIdFalloutMap = new LinkedHashMap<>();
			Map<String,List<ProgressionDto>> migrationIdAndEntityIdMappingForProgression = new LinkedHashMap<>();
			Map<Long,Map<Long,EntityDto>> migrationIdMappingForEntities = new LinkedHashMap<>();
			EntityDto entity = null;
			List<ProgressionDto> progressionList = null;
			Map<Long, EntityDto> entityMap = null;
			for(Object[] objs : repoData)
			{

				Long migrationId = objs[0]!=null ? (Long) objs[0] : null;
				fallOutDto = migrationIdFalloutMap.get(migrationId);
				if(fallOutDto == null)
				{
					fallOutDto = new FalloutProgressChartDto();
					fallOutDto.setMigrationId(migrationId);
					fallOutDto.setMigrationDescription(objs[1]!=null ? objs[1].toString() : null);
					fallOutDto.setClientCode(objs[7]!=null ? objs[7].toString() : null);
					fallOutDto.setMigrationDate(objs[8]!=null ? Instant.ofEpochMilli(((Long)objs[8])).atZone(ZoneId.systemDefault()).toLocalDate().toString() : null);
					list.add(fallOutDto);
				}

				migrationIdFalloutMap.put(migrationId, fallOutDto);

				Long entityId = objs[2]!=null ? (Long) objs[2] : null;

				//This is for Progress chart
				progressionList = migrationIdAndEntityIdMappingForProgression.get(migrationId+"_"+entityId);
				if(progressionList==null || progressionList.isEmpty())
				{
					progressionList = new ArrayList<>();
				}
				ProgressionDto progess = new ProgressionDto();
				progess.setEnvironmentId(objs[4]!=null ? (Long) objs[4] : null);
				progess.setEnvironmentType(objs[5]!=null ? objs[5].toString() : null);
				progess.setEnvironmentCount(objs[6]!=null ? (Long) objs[6] : null);
				progressionList.add(progess);
				migrationIdAndEntityIdMappingForProgression.put(migrationId+"_"+entityId, progressionList);


				//This is for Entity
				entityMap = migrationIdMappingForEntities.get(migrationId);

				if(entityMap==null)
				{
					entityMap = new LinkedHashMap<>();
				}
				if(entityMap.get(entityId) == null)
				{
					entity = new EntityDto();
					entity.setEntityId(entityId);
					entity.setEntityType(objs[3]!=null ? objs[3].toString() : null);

					entityMap.put(entityId, entity);
					migrationIdMappingForEntities.put(migrationId, entityMap);
					fallOutDto.setEntities(entityMap.values().stream().collect(Collectors.toList()));
				}
				entity.setProgressionDetails(progressionList);
			}
		}
	}

	@Override
	@Transactional(transactionManager = "defaultSqlTransactionManager",propagation=Propagation.REQUIRED,rollbackFor= {Exception.class,CustomValidationException.class})
	public Boolean persistsFalloutDataFromSystem(MigrationHistory history,Long timestamp) throws Exception,CustomValidationException
	{
		List<FalloutDto> dtos = ConfigUtil.getConfigDetails();

		//Get the masterdata for 
		Map<String, EntityMaster> masterEntitiesMap = ConfigUtil.populateListToMapWithFieldNameKey( entityMasterRepository.findAll(), "entityType");

		//Get the environments
		Map<String, EnvironmentDetailsMaster> environmentDetailMap = ConfigUtil.populateListToMapWithFieldNameKey( environmentDetailsRepository.findAll(),"environmentType");

		//Process the data and get the counts
		Map<String,String> mapping = ConfigUtil.getEnvironmentMapMapping();

		for(FalloutDto fallout : dtos)
		{

			//set entity wise datas.
			EntityMigrationData entityMigrationData = new EntityMigrationData();
			entityMigrationData.setActive(Boolean.TRUE);
			entityMigrationData.setCreatedAt(timestamp);
			entityMigrationData.setCreatedBy(ADMIN);
			entityMigrationData.setUpdatedAt(timestamp);
			entityMigrationData.setUpdatedBy(ADMIN);
			entityMigrationData.setEntityMaster(masterEntitiesMap.get(fallout.getName()));
			entityMigrationData.setEntityMigrationDescription("Migration of "+fallout.getName()+" at timestamp");
			entityMigrationData.setMigrationHistory(history);
			//entityMigrationData.setValues(values); This will be ignored


			List<EnvironmentValueDetails> values = new ArrayList<>();
			//Set 3 datas according to environment
			for(Map.Entry<String,String> entry : mapping.entrySet())
			{
				EnvironmentValueDetails value = new EnvironmentValueDetails();
				value.setActive(Boolean.TRUE);
				value.setCreatedAt(timestamp);
				value.setCreatedBy(ADMIN);
				value.setUpdatedAt(timestamp);
				value.setUpdatedBy(ADMIN);

				value.setEntityMigrationData(entityMigrationData);
				value.setEnvironmentDetails(environmentDetailMap.get(entry.getValue()));
				value.setEnvironmentCount(getCount(entry.getKey(),entry.getValue(),fallout));
				values.add(value);
			}

			entityMigrationRepository.saveAndFlush(entityMigrationData);
			environmentDetailsValueRepository.saveAll(values);

		}
		return Boolean.TRUE;

	}


	private MigrationHistory createMigrationData(Long timestamp) 
	{
		MigrationHistory history = new MigrationHistory();
		history.setActive(Boolean.TRUE);
		history.setCreatedAt(timestamp);
		history.setCreatedBy(ADMIN);
		history.setUpdatedAt(timestamp);
		history.setUpdatedBy(ADMIN);
		history.setMigrationDescription("Migration on date "+Instant.now());
		history.setClientCode("Telecom");
		return history;
	}

	private Long getCount(String environmentKey, String environmentValue, FalloutDto fallout) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		Long value = null;
		String executingFunction = "fetch"+StringUtils.capitalize(environmentValue)+"Data";
		value = (Long) this.getClass().getMethod(executingFunction, String.class).invoke(this, (String)fallout.getClass().getMethod("get"+StringUtils.capitalize(environmentKey), null).invoke(fallout, null));
		/*switch(environmentValue) {
		case "omnia" : value = fetchOminaData((String)fallout.getClass().getMethod("get"+StringUtils.capitalize(environmentKey), null).invoke(fallout, null));break;
		case "csr" : value = fetchCsrData((String)fallout.getClass().getMethod("get"+StringUtils.capitalize(environmentKey), null).invoke(fallout, null));break;
		case "rbm" : value = fetchRbmData((String)fallout.getClass().getMethod("get"+StringUtils.capitalize(environmentKey), null).invoke(fallout, null));break;

		}*/
		return value;

	}

	@Override
	@Transactional(transactionManager = "omniaSqlTransactionManager",readOnly=true)
	public Long fetchOmniaData(String sql) 
	{
		return ominaRepository.getCount(sql);
	}

	@Override
	@Transactional(transactionManager = "csrSqlTransactionManager",readOnly=true)
	public Long fetchCsrData(String sql) 
	{
		return csrRepository.getCount(sql);
	}

	@Override
	@Transactional(transactionManager = "rbmSqlTransactionManager",readOnly=true)
	public Long fetchRbmData(String sql) 
	{
		return rbmRepository.getCount(sql);
	}

	@Override
	@Transactional(transactionManager = "defaultSqlTransactionManager",readOnly=true)
	public List<EntityMasterDto> fetchEntityData() 
	{
		List<EntityMasterDto> dtos = new ArrayList<>();
		List<EntityMaster> masterList = entityMasterRepository.findAll();
		masterList.stream().forEach(m ->
		{
			EntityMasterDto dto = new EntityMasterDto();
			BeanUtils.copyProperties(m, dto);
			dtos.add(dto);
		});
		return dtos;
	}

	@Override
	@Transactional(transactionManager = "defaultSqlTransactionManager",readOnly=true)
	public List<EnvironmentDetailsDto> fetchEnvironmentData() 
	{
		List<EnvironmentDetailsDto> dtos = new ArrayList<>();
		List<EnvironmentDetailsMaster> masterList = environmentDetailsRepository.findAll();
		masterList.stream().forEach(m ->
		{
			EnvironmentDetailsDto dto = new EnvironmentDetailsDto();
			BeanUtils.copyProperties(m, dto);
			dtos.add(dto);
		});
		return dtos;
	}

	@Override
	@Transactional(transactionManager = "defaultSqlTransactionManager",readOnly=true)
	public List<ErrorMasterDto> fetchErrorMasterData() 
	{
		List<ErrorMasterDto> dtos = new ArrayList<>();
		List<ErrorMaster> masterList = errorMasterRepository.findAll();
		masterList.stream().forEach(m -> 
		{
			ErrorMasterDto dto = new ErrorMasterDto();
			BeanUtils.copyProperties(m, dto);
			dtos.add(dto);
		});
		return dtos;
	}


	@Override
	@Transactional(rollbackFor= {Exception.class,CustomValidationException.class},propagation=Propagation.REQUIRED,transactionManager = "defaultSqlTransactionManager")
	public Boolean persistsTalendLogsIntoSystem(MigrationHistory history,Long timestamp) throws CustomValidationException,Exception{

		//Get the master data for 
		Map<String, EntityMaster> masterEntitiesMap = ConfigUtil.populateListToMapWithFieldNameKey(entityMasterRepository.findAll(), "entityCode");
		Map<String, ErrorMaster> masterErrorsMap = ConfigUtil.populateListToMapWithFieldNameKey(errorMasterRepository.findAll(), "errorCode");

		Map<String,List<TalendJobDetailsDto>> talendJobMap = new HashMap<>();
		Map<String,List<TalendErrorDetailsDto>> talendErrorMap = new HashMap<>();

		//Get the parsed the CSV
		fetchAndParseTalendLogs(talendJobMap,talendErrorMap,masterEntitiesMap);

		List<DataValidationDetails> details = new ArrayList<>();
		List<DataRejectionDetails> rejections = new ArrayList<>();

		if(MapUtils.isNotEmpty(talendJobMap))
		{
			talendJobMap.entrySet().stream().forEach(entry -> 
			{
				TalendJobDetailsDto job = entry.getValue().get(0);
				DataValidationDetails valid = new DataValidationDetails();
				BeanUtils.copyProperties(job, valid);
				valid.setActive(Boolean.TRUE);
				valid.setCreatedAt(timestamp);
				valid.setCreatedBy(ADMIN);
				valid.setUpdatedAt(timestamp);
				valid.setUpdatedBy(ADMIN);
				valid.setEntity(masterEntitiesMap.get(entry.getKey()));
				valid.setMigrationHistory(history);
				valid.setSource(TALEND_SOURCE);
				details.add(valid);
			});
		}

		if(MapUtils.isNotEmpty(talendErrorMap))
		{
			talendErrorMap.entrySet().stream().forEach(entry -> 
			{
				List<TalendErrorDetailsDto> errors = entry.getValue();
				EntityMaster entity = masterEntitiesMap.get(entry.getKey());
				errors.forEach(error -> 
				{
					DataRejectionDetails reject = new DataRejectionDetails();
					BeanUtils.copyProperties(error, reject);
					reject.setActive(Boolean.TRUE);
					reject.setCreatedAt(timestamp);
					reject.setCreatedBy(ADMIN);
					reject.setUpdatedAt(timestamp);
					reject.setUpdatedBy(ADMIN);
					reject.setEntity(entity);
					reject.setMigrationHistory(history);
					reject.setErrorMaster(masterErrorsMap.get(error.getErrorCode()));
					reject.setSource(TALEND_SOURCE);
					rejections.add(reject);
				});
			});
		}

		if(CollectionUtils.isNotEmpty(rejections))
		{
			dataRejectionRepository.saveAll(rejections);
		}

		if(CollectionUtils.isNotEmpty(details))
		{
			dataValidationRepository.saveAll(details);
		}

		return Boolean.TRUE;

	}

	private void fetchAndParseTalendLogs(Map<String, List<TalendJobDetailsDto>> talendJobMap,
			Map<String, List<TalendErrorDetailsDto>> talendErrorMap, Map<String, EntityMaster> masterEntitiesMap) 
	{
		String jobPath = "",errorPath="";
		String entityCode =null;
		String keyNm="";
		if(MapUtils.isNotEmpty(masterEntitiesMap))
		{
			for(Map.Entry<String, EntityMaster> entry : masterEntitiesMap.entrySet())
			{
				entityCode = entry.getValue().getEntityCode();
				keyNm = entry.getKey();
				jobPath = "./config/talend_logs/"+keyNm+"RunDetails.csv";
				errorPath = "./config/talend_logs/"+keyNm+"Error.csv";
				talendJobMap.put(entityCode, ConfigUtil.parseCsvToBean(jobPath, TalendJobDetailsDto.class));
				talendErrorMap.put(entityCode, ConfigUtil.parseCsvToBean(errorPath, TalendErrorDetailsDto.class));
			}
		}
	}

	@Override
	@Transactional(transactionManager = "defaultSqlTransactionManager",
	propagation=Propagation.REQUIRED,
	rollbackFor= {Exception.class,CustomValidationException.class})
	public String persistsAllData() throws CustomValidationException, Exception {
		String message = "Success";
		Long timestamp = Instant.now().toEpochMilli();

		MigrationHistory prevHistory = migrationHistoryRepository.findTopByOrderByCreatedAtDesc();

		//This is for extra check
		if(prevHistory!=null && prevHistory.getCreatedAt()!=null && prevHistory.getCreatedAt().longValue() > ConfigUtil.getTodaysMidnightValueInEpoch())
		{
			return "AlreadySaved";
		} 

		MigrationHistory history = createMigrationData(timestamp);
		migrationHistoryRepository.saveAndFlush(history);

		//persists Fallout data
		Boolean falloutMsg = persistsFalloutDataFromSystem(history, timestamp);

		//persists Talend data
		Boolean talendMsg = persistsTalendLogsIntoSystem(history, timestamp);

		if(!falloutMsg.equals(talendMsg))
		{
			message = "Not successful";
		}

		return message;
	}

	@Override
	@Transactional(transactionManager = "defaultSqlTransactionManager",
	propagation=Propagation.REQUIRED,readOnly=true,
	rollbackFor= {Exception.class,CustomValidationException.class})
	public FetchErrorResponseDto fetchErrorData(FetchErrorRequestDto request) 
	{
		FetchErrorResponseDto response = new FetchErrorResponseDto();
		PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
		Page<TalendErrorDetailsDto> result = dataRejectionRepository.findErrorDetails(request.getMigrationId(), request.getEntityId(), request.getErrorCode(), 
				pageRequest);	

		if(result!=null && CollectionUtils.isNotEmpty(result.getContent()))
		{
			response.setErrors(result.getContent());
			BeanUtils.copyProperties(request, response);
			response.setTotalPages(result.getTotalPages());
			response.setTotalElements(result.getTotalElements());
		}
		return response;
	}

}
