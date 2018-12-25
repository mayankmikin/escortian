package com.whitehall.technologies.escortian.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class KeywordFacets 
{
	@Field(type = FieldType.Keyword)
	private String facet_name;
	@Field(type = FieldType.Keyword)
	private String facet_value;
	
	public String getFacet_name() {
		return facet_name;
	}
	public void setFacet_name(String facet_name) {
		this.facet_name = facet_name;
	}
	public String getFacet_value() {
		return facet_value;
	}
	public void setFacet_value(String facet_value) {
		this.facet_value = facet_value;
	}

	
}
