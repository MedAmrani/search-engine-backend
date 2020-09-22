package com.akveo.bundlejava.config.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    private final ElasticsearchRestClientProperties elasticsearchRestClientProperties;

    public RestClientConfig(ElasticsearchRestClientProperties elasticsearchRestClientProperties) {
        this.elasticsearchRestClientProperties = elasticsearchRestClientProperties;
    }

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticsearchRestClientProperties.getUris().toArray(new String[0]))
                .withBasicAuth(elasticsearchRestClientProperties.getUsername(),elasticsearchRestClientProperties.getPassword())
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
