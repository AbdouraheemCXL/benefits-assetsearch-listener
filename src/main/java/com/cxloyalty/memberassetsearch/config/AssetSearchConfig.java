/**
 * 
 */
package com.cxloyalty.memberassetsearch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

/**
 * 
 * @author afall
 *
 */

@EnableReactiveElasticsearchRepositories(basePackages = "com.cxloyalty.memberassetsearch.repository")
@ComponentScan("com.cxloyalty.membershipsearch.mapper")
@Configuration
public class AssetSearchConfig extends AbstractReactiveElasticsearchConfiguration {

	@Value("${spring.elasticsearch.host}")
	private String elasticsearchHost;

	@Override

	@Bean
	public ReactiveElasticsearchClient reactiveElasticsearchClient() {
		return client();
	}

	@Bean
	public ReactiveElasticsearchClient client() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo(elasticsearchHost).build();
		return ReactiveRestClients.create(clientConfiguration);
	}

}