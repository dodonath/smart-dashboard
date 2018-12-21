package com.synthesis.migration.smartdashboard.dto;

import java.util.List;

public class FetchErrorResponseDto {
	
	private Integer pageNumber = 0 ;
	private Integer pageSize = 0;
	private Integer totalPages = 0;
	private Long totalElements =0l;
	
	private List<TalendErrorDetailsDto> errors;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<TalendErrorDetailsDto> getErrors() {
		return errors;
	}

	public void setErrors(List<TalendErrorDetailsDto> errors) {
		this.errors = errors;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}
	
	

}
