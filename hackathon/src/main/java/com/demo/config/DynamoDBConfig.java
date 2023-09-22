package com.demo.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {
    @Bean
    public AmazonDynamoDB getDynamoDBClient() {
        return AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-east-1")
                .build();
    }

    @Bean
    public DynamoDBMapper getDynamoDBMapper(AmazonDynamoDB client) {
        return new DynamoDBMapper(client);
    }
}