package com.whitehall.technologies.escortian.query.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitehall.technologies.escortian.controller.FilterController;
import com.whitehall.technologies.escortian.model.KeywordFacets;
import com.whitehall.technologies.escortian.model.Person;

@Component
public class SearchQueryBuilder 
{
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	private static final Logger log = LoggerFactory.getLogger(SearchQueryBuilder.class);
	ObjectMapper mapper= new ObjectMapper();
	public List<Person> getAll(String text) {

        QueryBuilder query = QueryBuilders.boolQuery()
                .should(
                        QueryBuilders.queryStringQuery(text)
                                .lenient(true)
                                .field("name")
                                .field("teamName")
                ).should(QueryBuilders.queryStringQuery("*" + text + "*")
                        .lenient(true)
                        .field("name")
                        .field("teamName"));

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();

        List<Person> persons = elasticsearchTemplate.queryForList(build, Person.class);

        return persons;
    }
	
	public List<Person> getAllGirlsWithBlackColorAndDoAnal(KeywordFacets keyworFacet) throws JsonProcessingException
	{
		
		QueryBuilder query = QueryBuilders.boolQuery()
				.filter(
						QueryBuilders.boolQuery()
							.should(
									QueryBuilders.nestedQuery("keyword_facets", QueryBuilders.boolQuery()
											.filter(QueryBuilders.termQuery("keyword_facets.facet_name", keyworFacet.getFacet_name()))
											.filter(QueryBuilders.termQuery("keyword_facets.facet_value", keyworFacet.getFacet_value()))
											, ScoreMode.Max)
									)
						);
		NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();
		log.info("query is {}",query);
		//log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(query));
		 List<Person> persons = elasticsearchTemplate.queryForList(build, Person.class);
		 log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(persons));
		 return persons;
	}
	
}
