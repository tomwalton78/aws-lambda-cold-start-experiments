package lambdas;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class LambdaModule extends AbstractModule {

  @Provides
  AmazonDynamoDB providesDynamoDbClient(@Named("Region") String region) {
    return AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.fromName(region))
        .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
        .build();
  }

  @Provides
  AmazonS3 providesAmazonS3Client(@Named("Region") String region) {
    return AmazonS3ClientBuilder
        .standard()
        .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
        .withRegion(Regions.fromName(region))
        .build();
  }

  @Provides
  @Named("Region")
  String providesRegion() {
    return System.getenv("AWS_REGION");
  }

}
