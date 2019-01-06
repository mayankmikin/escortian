package com.whitehall.technologies.escortian.repository;




import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.whitehall.technologies.escortian.model.Person;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource(collectionResourceRel = "person", path = "person")
public interface PersonRepository extends ElasticsearchCrudRepository<Person, String> {

	 
	    Page<Person> findByEntityName(String name, Pageable pageable);

	   /* @Query("{\"bool\": {\"must\": {\"match_all\": {}}, \"filter\": {\"term\": {\"tags\": \"?0\" }}}}")
	    Page<Person> findByFilteredTagQuery(String tag, Pageable pageable);

	    @Query("{\"bool\": {\"must\": {\"match\": {\"authors.name\": \"?0\"}}, \"filter\": {\"term\": {\"tags\": \"?1\" }}}}")
	    Page<Person> findByAuthorsNameAndFilteredTagQuery(String name, String tag, Pageable pageable);*/
}
