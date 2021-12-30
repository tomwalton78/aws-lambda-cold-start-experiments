package lambdas;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
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
  @Named("Region")
  String providesRegion() {
    return System.getenv("AWS_REGION");
  }

}
