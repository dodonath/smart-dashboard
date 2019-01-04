package com.synthesis.migration.smartdashboard.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.synthesis.migration.smartdashboard.dto.ConfigDto;
import com.synthesis.migration.smartdashboard.dto.ConfigDto.FalloutDto;
import com.synthesis.migration.smartdashboard.entity.defaultdb.EntityMaster;
import com.synthesis.migration.smartdashboard.entity.defaultdb.ErrorMaster;


public class ConfigUtil {

	static List<ConfigDto> configList;

	static 
	{
		try 
		{
			// load  config
			URI uri = Paths.get("./config/recon_report_config").toUri();
			Stream<Path> paths = Files.walk(Paths.get(uri));
			configList = new ArrayList<>();
			paths.filter(Files::isRegularFile).forEach(path -> configList.add(getReportConfig(path)));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static List<ConfigDto> getReportConfig() {
		return configList;
	}

	public static List<FalloutDto> getConfigDetails() {
		List<ConfigDto.FalloutDto> fallouts = configList.stream()
				.map(reconReportConfig -> reconReportConfig.getFallouts())
				.flatMap(f -> f.stream())
				.collect(Collectors.toList());
		return fallouts;

	}

	public static Map<String,String> getEnvironmentMapMapping() 
	{

		Map<String,String> mapping = new HashMap<>();
		String[] arr = configList.get(0).getEnvironmentMap().getMapDetails().split("\\|");
		for(String data : arr)
		{
			String[] fullMap = data.split("_");
			mapping.put(fullMap[0], fullMap[1]);
		}
		return mapping;

	}



	private static ConfigDto getReportConfig(Path p) {
		try {
			byte[] content = Files.readAllBytes(p);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(content, ConfigDto.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <M,T> Map<M,T> populateListToMapWithFieldNameKey(List<T> list,String fieldName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Map<M,T> map = new HashMap<>();
		String getter = "get"+StringUtils.capitalize(fieldName);

		if(list==null || list.isEmpty()) return map;
		{
			for(T value : list)
			{
				M key = (M) value.getClass().getMethod(getter, null).invoke(value, null);
				if(key!=null)
				{
					map.put(key, value);
				}
			}
		}
		return map;
	}

	public static <M,T> Map<M,List<T>> populateListToMapListWithFieldNameKey(List<T> list,String fieldName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Map<M,List<T>> map = new HashMap<>();
		String getter = "get"+StringUtils.capitalize(fieldName);

		if(list==null || list.isEmpty()) return map;
		{
			List<T> newList = null;
			for(T value : list)
			{
				M key = (M) value.getClass().getMethod(getter, null).invoke(value, null);
				newList = map.get(key);
				if(null == newList)
				{
					newList = new ArrayList<>();
				}
				newList.add(value);
				map.put(key, newList);
			}
		}
		return map;
	}


	public static long getTodaysMidnightValueInEpoch()
	{
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now();
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		return todayMidnight.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
	}

	public static <T> List<T> parseCsvToBean(String fileName,Class<T> classNm )
	{
		List<T> list = new ArrayList<>(); 
		try 
		(
				Reader reader = Files.newBufferedReader(Paths.get(fileName));
				CSVReader csvReader = new CSVReader(reader);
		) 
		{

			HeaderColumnNameMappingStrategy<T> headerColumnName = new HeaderColumnNameMappingStrategy<>();
			headerColumnName.setType(classNm);

			CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
					.withMappingStrategy(headerColumnName)
					.withIgnoreLeadingWhiteSpace(Boolean.TRUE.booleanValue())
					.withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
					.withSeparator(',')
					.build();
			list = csvToBean.parse();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return list;

	}
	
	public static <T> void writeBeanToCsv(PrintWriter writer,List<T> list,Class<T> classNm)
	{
		try 
		{

			/*HeaderColumnNameMappingStrategy<T> headerColumnName = new HeaderColumnNameMappingStrategy<>();
			headerColumnName.setType(classNm);
			headerColumnName.generateHeader();*/
			
			StatefulBeanToCsv<T> btcsv = new StatefulBeanToCsvBuilder<T>(writer)
                   // .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                   // .withMappingStrategy(headerColumnName)
                   // .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
			btcsv.write(list);
		}
		 
		catch (CsvDataTypeMismatchException e) 
		{
			e.printStackTrace();
		} 
		catch (CsvRequiredFieldEmptyException e) 
		{
			e.printStackTrace();
		}

	}
	
	
	public static void listf(String directoryName, List<File> files,List<String> filePath) {
		File directory = Paths.get(directoryName).toFile();

		// Get all files from a directory.
		File[] fList = directory.listFiles();
		if(fList != null)
			for (File file : fList)
			{      
				if (file.isFile()) 
				{
					files.add(file);
					filePath.add(file.getAbsolutePath());
					System.out.println(file.getAbsolutePath());
				} 
				else if (file.isDirectory()) 
				{
					listf(file.getAbsolutePath(), files,filePath);
				}
			}
	}
	
	
	
	public static void parseAndFetchTargetData(String baseDirectory,Map<String, EntityMaster> masterEntitiesMap,Map<String, ErrorMaster> masterErrorsMap, Map<String, List<String>> concurrentMap)
	{
			List<String> filesPath = new ArrayList<>();
			List<File> files = new ArrayList<>();
			listf(baseDirectory,files,filesPath);
			
			ExecutorService service = Executors.newFixedThreadPool(5);
			CompletableFuture.allOf(
					filesPath
					.stream()
					.map(filepath -> CompletableFuture.runAsync(() -> parseAndStore(concurrentMap, masterEntitiesMap,masterErrorsMap,filepath), service))
					.toArray(CompletableFuture[]::new))
			.join();  
			service.shutdown();
			//return concurrentMap;
	}

	
	private static void parseAndStore(Map<String, List<String>> map, Map<String, EntityMaster> masterEntitiesMap,Map<String, ErrorMaster> masterErrorsMap, String filepath) {
		
		File file = new File(filepath); 
		try(Scanner sc = new Scanner(file)) 
		{
			
			String lineStr = "";
			String accountId = "";
			String errorCode = "";
			String errorDetails = "";
			String entityCode = "";
			String [] arrOuter = null;
			String [] arrInner = null;
			while (sc.hasNextLine()) 
			{

				accountId = "";
				errorCode = "";
				errorDetails = "";
				entityCode = "";
				lineStr = sc.nextLine();
				arrOuter = StringUtils.split(lineStr, ":");
				List<String> strList = null;

				if(arrOuter!=null && arrOuter[0]!=null && masterEntitiesMap.containsKey(arrOuter[0]))
				{
					entityCode = arrOuter[0];
					strList = map.get(entityCode);
					if(CollectionUtils.isEmpty(strList))
					{
						strList  = new ArrayList<>();
					}
					arrInner = StringUtils.split(arrOuter[1], ",");
					
					if(arrInner!=null && arrInner[0]!=null)
					{
						accountId = StringUtils.trimAllWhitespace(arrInner[0]);
						accountId = StringUtils.replace(accountId, "\"", "") ;
					}

					//read the next line
					if(sc.hasNextLine())
					{
						
						lineStr = sc.nextLine();
						arrOuter = StringUtils.split(lineStr, ": ");
						if(arrOuter!=null && arrOuter[1]!=null)
						{
							arrInner = StringUtils.split(arrOuter[1],":") ;
							errorCode = StringUtils.trimWhitespace(arrInner[0]);
							errorCode = StringUtils.replace(errorCode, "\"", "") ;

							if(masterErrorsMap.containsKey(errorCode))
							{
								errorDetails = StringUtils.trimWhitespace(arrInner[1]);
								errorDetails = StringUtils.replace(errorDetails, "\"", "") ;
							}
						}
					}

					if(!StringUtils.isEmpty(errorDetails) && !StringUtils.isEmpty(errorCode) && !StringUtils.isEmpty(accountId))
					{
						String str = new String(entityCode+"|"+accountId+"|"+errorCode+"|"+errorDetails);
						strList.add(str);
						map.put(entityCode, strList);
						System.out.println(str);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	
	


}
