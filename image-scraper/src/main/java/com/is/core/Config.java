package com.is.core;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;


/**
 *	TODO
 *	1. Load host, port, protocol, username, password from externalized configuration file.
 *  2. Optimize ES repositories, to be scanned packaged as accordingly required.
 * 
 * @author vishwa
 *
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.is.repo")
@ComponentScan(basePackages = { "com.is" })
public class Config {
	
//  @Value("${elasticsearch.host}")
//  private String host;
//
//  @Value("${elasticsearch.port}")
//  private int port;
//
//  @Value("${elasticsearch.protocol}")
//  private String protocol;
//
//  @Value("${elasticsearch.username}")
//  private String userName;
//
//  @Value("${elasticsearch.password}")
//  private String password;


	@Bean
	public RestHighLevelClient client() {
		// TODO
//	    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//	    //credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

		ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200").withDefaultHeaders(getDefaultHeaders()).build();
		return RestClients.create(clientConfiguration).rest();
	}

	
	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
		return new ElasticsearchRestTemplate(client());
	}
	
	// To handle version compatibility between ES7, ES8
	private HttpHeaders getDefaultHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/vnd.elasticsearch+json;compatible-with=7");
		headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.elasticsearch+json;compatible-with=7");
		return headers;
	}
	
}
