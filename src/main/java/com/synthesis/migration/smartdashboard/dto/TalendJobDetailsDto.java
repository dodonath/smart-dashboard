package com.synthesis.migration.smartdashboard.dto;

public class TalendJobDetailsDto {
	
	private Long collectedCount;
	private Long validatedCount;
	private Long transformedCount;
	private String startedAt;
	private String endedAt;
	
	
	public Long getCollectedCount() {
		return collectedCount;
	}
	public void setCollectedCount(Long collectedCount) {
		this.collectedCount = collectedCount;
	}
	public Long getValidatedCount() {
		return validatedCount;
	}
	public void setValidatedCount(Long validatedCount) {
		this.validatedCount = validatedCount;
	}
	public Long getTransformedCount() {
		return transformedCount;
	}
	public void setTransformedCount(Long transformedCount) {
		this.transformedCount = transformedCount;
	}
	public String getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(String startedAt) {
		this.startedAt = startedAt;
	}
	public String getEndedAt() {
		return endedAt;
	}
	public void setEndedAt(String endedAt) {
		this.endedAt = endedAt;
	}
	
	
	public String toString()
	{
		return "TalendJobDetailsDto = { \n"
				+ "  collectedCount = " + collectedCount + " , \n"
				+ "  transformedCount = " + transformedCount + " , \n"
				+ "  startedAt = " + startedAt + " , \n"
				+ "  endedAt = " + endedAt + "  \n"
				+ "}";
		
	}
	

}
