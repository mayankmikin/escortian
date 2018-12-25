package com.whitehall.technologies.escortian.model;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class KeywordMultiFacets 
{
	@Field(type = FieldType.Keyword)
	private String facet_name;
	@Field(type = FieldType.Keyword)
	private List<String> facet_value;
	
	public String getFacet_name() {
		return facet_name;
	}
	public void setFacet_name(String facet_name) {
		this.facet_name = facet_name;
	}
	public List<String> getFacet_value() {
		return facet_value;
	}
	public void setFacet_value(List<String> facet_value) {
		this.facet_value = facet_value;
	}
	
	
}
