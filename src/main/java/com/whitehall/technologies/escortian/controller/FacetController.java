package com.whitehall.technologies.escortian.controller;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitehall.technologies.escortian.query.builders.SearchQueryBuilder;
@RestController
@RequestMapping("/facets")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FacetController 
{
	@Autowired
	SearchQueryBuilder builder;
	 RestTemplate temp= new RestTemplate();
	 ObjectMapper mapper = new ObjectMapper();
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllFacets() throws IOException {
		return new ResponseEntity<Map>(builder.getAllAggregations(),HttpStatus.OK);
	}
	@GetMapping("/direct/getAll")
	public ResponseEntity<?> getAllFacetsFeomEs() throws IOException {
		
		return new ResponseEntity<JsonNode>(temp.postForObject("http://localhost:9200/persons/person/_search",mapper.readValue("{\r\n" + 
				"   \"aggs\": {\r\n" + 
				"     \"agg_keyword_facet\": {\r\n" + 
				"       \"nested\": {\r\n" + 
				"         \"path\": \"keyword_facets\"\r\n" + 
				"       },\r\n" + 
				"       \"aggs\": {\r\n" + 
				"         \"facet_name\": {\r\n" + 
				"           \"terms\": {\r\n" + 
				"             \"field\": \"keyword_facets.facet_name\"\r\n" + 
				"           },\r\n" + 
				"           \"aggs\": {\r\n" + 
				"             \"facet_value\": {\r\n" + 
				"               \"terms\": {\r\n" + 
				"                 \"field\": \"keyword_facets.facet_value\"\r\n" + 
				"               }\r\n" + 
				"             }\r\n" + 
				"           }\r\n" + 
				"         }\r\n" + 
				"       }\r\n" + 
				"     }\r\n" + 
				"   }\r\n" + 
				"}",JsonNode.class) ,JsonNode.class),HttpStatus.OK);
	}
	
}
