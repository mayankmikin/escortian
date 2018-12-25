package com.whitehall.technologies.escortian.model;

import static org.springframework.data.elasticsearch.annotations.FieldType.Nested;

import java.io.Serializable;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
@Document(indexName = "persons", type = "person",shards = 1, replicas = 0, refreshInterval = "-1")
public class Person implements Serializable
{
	private static final long serialVersionUID = 8200390022234026L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	private Entity entity;

	@Field(type = Nested, includeInParent = true)
    private List<KeywordFacets> keyword_facets;
	@Field(type = Nested, includeInParent = true)
    private List<LongFacets> long_facets;
	@Field(type = Nested, includeInParent = true)
    private List<KeywordMultiFacets> keyword_multi_facets;
	

	public List<KeywordFacets> getKeyword_facets() {
		return keyword_facets;
	}
	public void setKeyword_facets(List<KeywordFacets> keyword_facets) {
		this.keyword_facets = keyword_facets;
	}
	public List<LongFacets> getLong_facets() {
		return long_facets;
	}
	public void setLong_facets(List<LongFacets> long_facets) {
		this.long_facets = long_facets;
	}
	
	public List<KeywordMultiFacets> getKeyword_multi_facets() {
		return keyword_multi_facets;
	}
	public void setKeyword_multi_facets(List<KeywordMultiFacets> keyword_multi_facets) {
		this.keyword_multi_facets = keyword_multi_facets;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	
	
}
