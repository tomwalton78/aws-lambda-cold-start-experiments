package lambdas;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.s3.AmazonS3;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = LambdaModule.class)
public interface MyComponent {

  AmazonDynamoDB buildAmazonDynamoDB();

  AmazonS3 buildAmazonS3();
}
