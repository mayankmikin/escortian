package com.whitehall.technologies.escortian.query.builders;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitehall.technologies.escortian.model.AggregationResponse;
import com.whitehall.technologies.escortian.model.KeywordFacets;
import com.whitehall.technologies.escortian.model.Person;
import com.whitehall.technologies.escortian.utils.AggregationsResultsExtractor;

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
	public List<Person> getPersonByProfileName(String profilename) throws JsonProcessingException
	{
		QueryBuilder query=matchQuery("entity.name", profilename).minimumShouldMatch("75%");
		NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();
		log.info("query is {}",query);
		 List<Person> persons = elasticsearchTemplate.queryForList(build, Person.class);
		 log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(persons));
		 return persons;
	}
	
	public Map<String, AggregationResponse> getAllAggregations() throws IOException
	{
		//final SearchQuery searchQuery = buildSearchQuery(terms("keyword_facets").field("keyword_facets.facet_name"));
		final SearchQuery searchQuery = 
		buildSearchQuery(terms("keyword_facets_name").field("keyword_facets.facet_name").showTermDocCountError(true),
				terms("keyword_facets_values").field("keyword_facets.facet_value").showTermDocCountError(true));
		 final Aggregations aggregations = elasticsearchTemplate.query(searchQuery,new AggregationsResultsExtractor());
		 final StringTerms topTags = (StringTerms)aggregations.getAsMap().get("keyword_facets_name");
	        topTags.getBuckets().forEach(bucket -> {
	            log.info("{}, {}", bucket.getKeyAsString(), bucket.getDocCount());
	        });
	        final StringTerms topTagss = (StringTerms)aggregations.getAsMap().get("keyword_facets_values");
	        topTagss.getBuckets().forEach(bucket -> {
	            log.info("{}, {}", bucket.getKeyAsString(), bucket.getDocCount());
	        });
	        HashMap<String, AggregationResponse>mapWithCount= new HashMap<>();
	        int count=0;
	        for(Bucket b:topTags.getBuckets())
	        {
	        	if(null!=topTagss.getBuckets().get(count))
	        	{
	        		Bucket bb=topTagss.getBuckets().get(count);
	        		mapWithCount.putIfAbsent(b.getKeyAsString(), new AggregationResponse(bb.getKeyAsString(),bb.getDocCount()));
	        	}
	        	else
	        	{
	        		mapWithCount.putIfAbsent(b.getKeyAsString(), new AggregationResponse());
	        	}
	        	count++;
	        }

		 return mapWithCount;
		 
	}
	private SearchQuery buildSearchQuery(AbstractAggregationBuilder abstractAggregationBuilder){
        return new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withIndices("persons").withTypes("person")
                .addAggregation(abstractAggregationBuilder)
                .build();
    }
	private SearchQuery buildSearchQuery(AbstractAggregationBuilder abstractAggregationBuilder1,AbstractAggregationBuilder abstractAggregationBuilder2){
        return new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withIndices("persons").withTypes("person")
                .addAggregation(abstractAggregationBuilder1)
                .addAggregation(abstractAggregationBuilder2)
                .build();
    }
	
}
