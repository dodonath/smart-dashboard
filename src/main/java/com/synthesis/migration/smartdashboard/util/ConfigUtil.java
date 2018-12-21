package com.synthesis.migration.smartdashboard.util;

import java.io.IOException;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.synthesis.migration.smartdashboard.dto.ConfigDto;
import com.synthesis.migration.smartdashboard.dto.ConfigDto.FalloutDto;


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


}
