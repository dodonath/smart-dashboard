package com.synthesis.migration.smartdashboard.dto;

import java.util.List;



public class ConfigDto {

	private List<FalloutDto> fallouts;
	private EnvironmentMapping environmentMap;
	
	

	public EnvironmentMapping getEnvironmentMap() {
		return environmentMap;
	}

	public void setEnvironmentMap(EnvironmentMapping environmentMap) {
		this.environmentMap = environmentMap;
	}

	public List<FalloutDto> getFallouts() 
	{
		return fallouts;
	}

	public void setFallouts(List<FalloutDto> fallouts) 
	{
		this.fallouts = fallouts;
	}

	public FalloutDto findFalloutByName(String name) 
	{
		return fallouts.stream().filter(fallout -> fallout.getName().equals(name)).findFirst().get();
	}

	@Override
	public String toString() 
	{
		return "ConfigDto{" +
				"fallouts=" + fallouts +
				'}';
	}
	
	
	
	public static class EnvironmentMapping
	{
		private String mapDetails;

		public String getMapDetails() {
			return mapDetails;
		}

		public void setMapDetails(String mapDetails) {
			this.mapDetails = mapDetails;
		}
		
		
		
	}

	public static class FalloutDto 
	{
		private String name;
		private String sourceQuery;
		private String targetQuery;
		private String middleDBQuery;



		public String getMiddleDBQuery() {
			return middleDBQuery;
		}

		public void setMiddleDBQuery(String middleDBQuery) {
			this.middleDBQuery = middleDBQuery;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSourceQuery() {
			return sourceQuery;
		}

		public void setSourceQuery(String sourceQuery) {
			this.sourceQuery = sourceQuery;
		}

		public String getTargetQuery() {
			return targetQuery;
		}

		public void setTargetQuery(String targetQuery) {
			this.targetQuery = targetQuery;
		}

		@Override
		public String toString() {
			return "FalloutDto{" +
					"name='" + name + '\'' +
					", sourceQuery='" + sourceQuery + '\'' +
					", targetQuery='" + targetQuery + '\'' +
					'}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			FalloutDto fallout = (FalloutDto) o;

			return name.equals(fallout.name);
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}
	}



}
