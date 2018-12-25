package com.whitehall.technologies.escortian.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.whitehall.technologies.escortian.model.KeywordFacets;
import com.whitehall.technologies.escortian.model.Person;
import com.whitehall.technologies.escortian.query.builders.SearchQueryBuilder;
@RestController
@RequestMapping("/filter")
public class FilterController 
{
	@Autowired
	SearchQueryBuilder builder;
	
	private static final Logger log = LoggerFactory.getLogger(FilterController.class);
	
	 @PostMapping("/data")
		public ResponseEntity<?> getdatarequest(@RequestBody KeywordFacets datarequest) throws JsonProcessingException
		{
			 log.debug("datarequest is {}",datarequest);
			 
			 return new ResponseEntity<List<Person>>(builder.getAllGirlsWithBlackColorAndDoAnal(datarequest),HttpStatus.OK);
		}

}
