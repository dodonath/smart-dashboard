package com.synthesis.migration.smartdashboard.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import com.synthesis.migration.smartdashboard.dto.ValidationChartDto;
import com.synthesis.migration.smartdashboard.dto.ValidationChartDto.EntityValidationDto;
import com.synthesis.migration.smartdashboard.dto.ValidationChartDto.ValdationErrorDto;
import com.synthesis.migration.smartdashboard.dto.ValidationChartDto.ValidationStatusDto;
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
import com.synthesis.migration.smartdashboard.util.DashBoardConstants;


@Service
public class DashBoardServiceImpl implements DashBoardService {


	private static final String ADMIN = "Admin";


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
			Map<Long,FalloutProgressChartDto> migrationIdFalloutMap = new HashMap<>();
			Map<String,List<ProgressionDto>> migrationIdAndEntityIdMappingForProgression = new HashMap<>();
			Map<Long,Map<Long,EntityDto>> migrationIdMappingForEntities = new HashMap<>();
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
					entityMap = new HashMap<>();
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
	public Boolean persistsLogsIntoSystem(MigrationHistory history,Long timestamp) throws CustomValidationException,Exception{

		//Get the master data for 
		List<EntityMaster> entities = entityMasterRepository.findAll();
		Map<String, EntityMaster> masterEntitiesMap = ConfigUtil.populateListToMapWithFieldNameKey(entities, "entityCodeInValidation");
		Map<String, ErrorMaster> masterErrorsMap = ConfigUtil.populateListToMapWithFieldNameKey(errorMasterRepository.findAll(), "errorCode");
		Map<String, EntityMaster> entityCodeInTargetMap = ConfigUtil.populateListToMapWithFieldNameKey(entities, "entityCodeInTarget");


		Map<String,List<TalendJobDetailsDto>> talendJobMap = new HashMap<>();
		Map<String,List<TalendErrorDetailsDto>> talendErrorMap = new HashMap<>();
		Map<String,List<String>> concurrentMap = new ConcurrentHashMap<>();


		List<Runnable> tasks = new ArrayList<>();
		tasks.add(() -> fetchAndParseTalendLogs(talendJobMap,talendErrorMap,masterEntitiesMap));
		tasks.add(() -> ConfigUtil.parseAndFetchTargetData("./config/target_rbm_logs/",entityCodeInTargetMap,masterErrorsMap,concurrentMap));

		ExecutorService service = Executors.newFixedThreadPool(3);
		CompletableFuture.allOf(
				tasks
				.stream()
				.map(task -> CompletableFuture.runAsync(task, service))
				.toArray(CompletableFuture[]::new))
		.join();  
		service.shutdown();


		List<DataRejectionDetails> listTargetRejectionDatas = null;
		Map<String,Long> loadedErrorCountMap = new HashMap<>();

		//parse and form target data
		if(MapUtils.isNotEmpty(concurrentMap))
		{
			listTargetRejectionDatas = concurrentMap.entrySet().stream().map(entry -> { 

				List<String> values = entry.getValue();
				loadedErrorCountMap.put(entry.getKey(), CollectionUtils.isNotEmpty(values) ?Long.valueOf(values.size())  : 0l);
				return values;
			})
					.flatMap(f -> f.stream())
					.map(data -> {
						String arr[] = StringUtils.split(data, "|");
						String entityCode = arr[0];
						DataRejectionDetails reject = new DataRejectionDetails();
						reject.setActive(Boolean.TRUE);
						reject.setCreatedAt(timestamp);
						reject.setCreatedBy(ADMIN);
						reject.setUpdatedAt(timestamp);
						reject.setUpdatedBy(ADMIN);
						reject.setEntity(entityCodeInTargetMap.get(entityCode));
						reject.setMigrationHistory(history);
						reject.setErrorMaster(masterErrorsMap.get(arr[2]));
						reject.setSystemErrorDetails(arr[3]);
						reject.setRejectionAccountId(arr[1]!=null ? Long.valueOf(arr[1]) : null);
						reject.setSource(DashBoardConstants.RBM_SOURCE);
						return reject;
					})
					.collect(Collectors.toList());
		}

		List<DataValidationDetails> details = new ArrayList<>();
		List<DataRejectionDetails> rejections = new ArrayList<>();

		//This is for validation data
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
				Long loadedErrorCount = loadedErrorCountMap.get(valid.getEntity().getEntityCodeInTarget());
				valid.setLoadedCount(valid.getCollectedCount()==null || loadedErrorCount ==null ? null : Math.subtractExact(valid.getCollectedCount(),loadedErrorCount) );
				details.add(valid);
			});
		}

		//This is for rejection data of talend Logs
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
					reject.setSource(DashBoardConstants.TALEND_SOURCE);
					rejections.add(reject);
				});
			});
		}

		if(CollectionUtils.isNotEmpty(rejections))
		{
			dataRejectionRepository.saveAll(rejections);
		}
		if(CollectionUtils.isNotEmpty(listTargetRejectionDatas))
		{
			dataRejectionRepository.saveAll(listTargetRejectionDatas);
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
				entityCode = entry.getValue().getEntityCodeInValidation();
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

		/*		//persists Fallout data
		Boolean falloutMsg = persistsFalloutDataFromSystem(history, timestamp);

		//persists Talend data
		Boolean talendMsg = persistsLogsIntoSystem(history, timestamp);
		 */


		List<Callable<Boolean>> tasks = new ArrayList<>();
		tasks.add(() -> persistsFalloutDataFromSystem(history, timestamp));
		tasks.add(() -> persistsLogsIntoSystem(history, timestamp));

		ExecutorService service = Executors.newFixedThreadPool(3);
		List<Future<Boolean>> msgs = service.invokeAll(tasks);
		service.shutdown();

		for(Future<Boolean> msg : msgs)
		{
			if(!msg.get()) message = "Not successful";
		}
		return message;
	}

	@Override
	@Transactional(transactionManager = "defaultSqlTransactionManager",
	propagation=Propagation.REQUIRED,readOnly=true,
	rollbackFor= {Exception.class,CustomValidationException.class})
	public FetchErrorResponseDto fetchErrorData(FetchErrorRequestDto request) 
	{
		PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
		FetchErrorResponseDto response = new FetchErrorResponseDto();
		if(request.getPageNumber()!=null && request.getPageSize()!=null)
		{
			pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
		}
		Page<TalendErrorDetailsDto> result = dataRejectionRepository.findErrorDetails(request.getMigrationId(), 
				request.getEntityId(), request.getErrorCode(), 
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

	@Override
	public List<ValidationChartDto> fetchMigrationValidationData(String source) throws Exception, CustomValidationException {
		List<Object[]> validations = dataValidationRepository.findLatestValidationDetails();
		List<Object[]> rejections = dataRejectionRepository.findLatestRejectionDetails(source);

		List<ValidationChartDto> charts = new ArrayList<>();
		ValidationChartDto chart = new ValidationChartDto();
		charts.add(chart);
		Long entityId = null;
		Map<Long,EntityValidationDto> entitiesToValidationMap = new HashMap<>();
		Map<Long,List<ValdationErrorDto>> entitiesToErrorListMap = new HashMap<>();

		//Populating the rejection data
		for(Object[] objs : rejections)
		{

			ValdationErrorDto error = new ValdationErrorDto();
			error.setErrorId(objs[2]!=null ? (Long) objs[2] : null);
			error.setErrorCode(objs[3]!=null ? objs[3].toString() : null);
			error.setErrorCount(objs[4]!=null ? (Long) objs[4] : null);

			entityId = objs[1]!=null ? (Long) objs[1] : null;
			List<ValdationErrorDto> errors =  entitiesToErrorListMap.get(entityId);
			if(errors == null)
			{
				errors = new ArrayList<>();
			}
			errors.add(error);
			entitiesToErrorListMap.put(entityId,errors);
		}

		//Populating the validation data
		entityId = null;
		for(Object[] objs : validations )
		{
			if(chart.getMigrationId() == null)
			{
				chart.setMigrationId(objs[0]!=null ? (Long) objs[0] : null);
				chart.setMigrationDate(objs[1]!=null ? Instant.ofEpochMilli(((Long)objs[1])).atZone(ZoneId.systemDefault()).toLocalDate().toString() : null);
				chart.setClientCode(objs[2]!=null ? objs[2].toString() : null);
				chart.setMigrationDescription(objs[3]!=null ? objs[3].toString() : null);
			}

			entityId = objs[4]!=null ? (Long) objs[4] : null;

			//Adding entities and validation in the JSON
			if(entitiesToValidationMap.get(entityId) == null)
			{
				ValidationStatusDto status = new ValidationStatusDto();
				status.setCollected(objs[6]!=null ? (Long) objs[6] : null);
				status.setValidated(objs[7]!=null ? (Long) objs[7] : null);
				status.setTransformed(objs[8]!=null ? (Long) objs[8] : null);
				status.setLoaded(objs[9]!=null ? (Long) objs[9] : null);

				EntityValidationDto  validation = new EntityValidationDto();
				validation.setEntityId(entityId);
				validation.setEntityType(objs[5]!=null ? objs[5].toString() : null);
				validation.setMigrationStats(status);

				List<ValdationErrorDto> errors =  entitiesToErrorListMap.get(entityId);
				if(CollectionUtils.isNotEmpty(errors))
				{
					Collections.sort(errors, (error1,error2) -> error1.getErrorId().compareTo(error2.getErrorId()));
					validation.setErrors(errors);
				}
				List<EntityValidationDto> entities = chart.getEntities();
				if(entities ==null)
				{
					entities  = new ArrayList<>();
				}
				entities.add(validation);
				chart.setEntities(entities);
				entitiesToValidationMap.put(entityId, validation);
			}
		}

		return charts;
	}

	@Override
	public List<String> getEnvironmentDetails() {
		List<String> list = new ArrayList<>();
		list.add("Migration Date");
		List<EnvironmentDetailsMaster> environments = environmentDetailsRepository.findAllByOrderByEnvironmentDisplayOrderAsc();
		environments.stream().forEach(env -> list.add(env.getEnvironmentType()));
		return list;
	}



	public static void main(String args[])
	{
		String str = "Migprodeventtypedetails|34520060|FDU-00013|API validation error: The event source start date and time (24/11/2014 12:00:00) is before the product start date (09/06/2015 12:00:00).";
		Arrays.asList(StringUtils.split(str,"|")).stream().forEach(val -> System.out.println(val));
	}


}
