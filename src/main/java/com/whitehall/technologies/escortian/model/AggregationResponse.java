package com.whitehall.technologies.escortian.model;

public class AggregationResponse {
	
	private String facetValue;
	private long count;
	public String getFacetValue() {
		return facetValue;
	}
	public void setFacetValue(String facetValue) {
		this.facetValue = facetValue;
	}
	public long getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public AggregationResponse() {
		super();
	}
	public AggregationResponse(String facetValue, long count) {
		super();
		this.facetValue = facetValue;
		this.count = count;
	}

	
}
