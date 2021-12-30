package lambdas;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;

import java.util.UUID;

public class DynamoWriter {

  private static final String TABLE_NAME = "testTable";
  private static final String PARTITION_KEY = "id";
  private final AmazonDynamoDB dynamoDB;

  @Inject
  public DynamoWriter(AmazonDynamoDB dynamoDB) {
    this.dynamoDB = dynamoDB;
  }

  public void writeData() {
    PutItemRequest request = new PutItemRequest();
    request.setTableName(TABLE_NAME);
    request.setItem(ImmutableMap.of(
        PARTITION_KEY, new AttributeValue().withS(UUID.randomUUID().toString())
    ));

    dynamoDB.putItem(request);
  }
}
