package com.is.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.is.domain.WebResource;
/**
 * 
 * @author vishwa
 *
 */
public interface WebResourceRepo extends ElasticsearchRepository<WebResource, String> {

}
