package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;


@Configuration
public class DynamoDBConfig {

    @Bean
    public DynamoDbClient getDynamoDBClient() {
        String accessKeyId = System.getenv("AWS_ACCESS_KEY_ID");
        String secretAccessKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String sessionToken = System.getenv("AWS_SESSION_TOKEN");
        AwsSessionCredentials awsBasicCredentials = AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken);
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);
        System.setProperty("aws.endpointDiscoveryEnabled", "false");
        Region region = Region.US_EAST_1;
       return DynamoDbClient.builder()
                .credentialsProvider(staticCredentialsProvider)
                .endpointOverride(URI.create("https://dynamodb.us-east-1.amazonaws.com"))
                .region(region)
                .build();
    }

}