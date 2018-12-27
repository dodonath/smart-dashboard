package com.synthesis.migration.smartdashboard.dto;

import java.util.List;

public class ProgessWrapperDto {
	
	private List<String> progressChartsHeader;
	
	private List<FalloutProgressChartDto> progressChartsValues;

	public List<String> getProgressChartsHeader() {
		return progressChartsHeader;
	}

	public void setProgressChartsHeader(List<String> progressChartsHeader) {
		this.progressChartsHeader = progressChartsHeader;
	}

	public List<FalloutProgressChartDto> getProgressChartsValues() {
		return progressChartsValues;
	}

	public void setProgressChartsValues(List<FalloutProgressChartDto> progressChartsValues) {
		this.progressChartsValues = progressChartsValues;
	}
	
	
	

}
