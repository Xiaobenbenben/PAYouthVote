package com.citi.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class DynamoDBConfig {
    @Bean
    public AmazonDynamoDB getDynamoDBClient() {
//        System.setProperty("aws.accessKeyId", "ASIAWQWKMEAXQ3QDI5QC");
//        System.setProperty("aws.secretKey", "FKNLgljS/yVPkJo9uxYuMx5EzKXIW0Ei96UI1/0j");
//        System.setProperty("aws.sessionToken", "IQoJb3JpZ2luX2VjEBsaCXVzLWVhc3QtMSJIMEYCIQCTin+7uHRDcF5dHfRyVoMwO5Qjrne8WWvJkkn1Pkey/wIhAJdaUrstI8r8QU3V3ptwCepnZ50iVvJ0j7wgW/LxxB9sKrQECBQQABoMNDQ4MTc0NzU5OTgzIgwQl9Z5osoSrAFnRYsqkQTG1cWliQ4ZZEWEWWxKB7sVx8uxso6VIW+rhqNFNsJgqqUBh0xICoJvd6Psfmz3pxUNPHFPXyCkccmJPW0e6Y0ATDrWxEnAp8C25Kvrn5gYsFAF5buefQoZedI7znrXbm/pSGcOdqr8Wm4fn8tFZRChl6a/EKCr+DNxEEUpcTOTtWoWMdeJi14A/K/wi//8gm6XOOKXTZbNBmZ/xEg1ne38CAJx18xOi0igxSgJ4tfYR6FD6d7Fn0Fg7IirhxnJqKHFRyiYbVNTDpn6eKwNkHvNyMQeDXUy1b7uv+7HtHoCOn2/+FOA60ZVntGdUM5d7thDGQr+zFFmZzjVbrYPN5pcz7ZJM9CSSBwlBL/FzDrFKHyVO2fja2FKO3SZqQ19C1odHTec4Nqr5TfOQQsVy1IPiZcCWlp+wq2/YjS8MJ1IYbIt0D26yUnyD9RfbSXuHpLTyp9eZw0K6alYPCO/8wTJktNZxTTxFJytah5cYri0aeLeML0b3+JNsacU2Bv/iWqRCj0HihmjdgXbtZ2Xr59pSRZ61qjYZzEbHvwpYwcUwHU8f7GsH58QHjM7gcUa719TPKJVEEkem9qMaOLabcDQKs5k8iwRxlO7WqXA3eXcF/nalkS501WnH+nehMO/jk6UlAAYtB8Fb4PuQ78uKfQoAure/YlVUTEEQeltdWBxetYfea62NJ6WnjjwoTYSATtPMI3ntagGOqUChURBr5y6uX8vv70Yk8Yduk1iSgBrdvwOIHM+5mdUh6KemAUR6rsMVyYaZL4X9lG7tbsQF2VFZu8Jpu3d0GV9eIMapmhqZkHglSPLYAFA1lKrUsn0nW09zZzj36VmuviJrz9r8Ghq53JZoPZeU9t80XKmoXWFnXeimGk0Dr0y6E0jlq2ysS4J7ideFrmgU4yauBMp0al9T1xpqEJIdRIRmQ9PNcVrfkYI2WExSNN0xl9+IOM96l0JMGZ3eh9hKtjDZsawFH/o9LFvZric1l1eZv+U3DUDOc0Q2zJpyUeMuwRm4Thdo2vQvRl0BimP5yIk5j136JMwyGJE2Ang6V5jjBKZ/tRn/vJehfiWyjRc/PsdYwduzxTpuyPVChNqpgBYCaAnFcY=");
        return AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-east-1")
                .build();
    }

    @Bean
    public DynamoDBMapper getDynamoDBMapper(AmazonDynamoDB client) {
        return new DynamoDBMapper(client);
    }
}