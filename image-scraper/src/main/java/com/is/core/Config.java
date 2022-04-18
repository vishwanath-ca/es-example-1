package com.is.core;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;


/**
 * 
 * @author vishwa
 *
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.is.repo")
@ComponentScan(basePackages = { "com.is" })
public class Config {

	@Bean
	public RestHighLevelClient client() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200").withDefaultHeaders(getDefaultHeaders()).build();
		return RestClients.create(clientConfiguration).rest();
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
		return new ElasticsearchRestTemplate(client());
	}
	
	private HttpHeaders getDefaultHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/vnd.elasticsearch+json;compatible-with=7");
		headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.elasticsearch+json;compatible-with=7");
		return headers;
	}
	
}
