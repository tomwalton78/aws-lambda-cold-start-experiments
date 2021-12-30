package lambdas;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.inject.Inject;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class S3Writer {

  private static final String BUCKET_NAME = "my-test-3-bucket-for-aws-lambda-cold-start-experiments";

  private final AmazonS3 amazonS3;

  @Inject
  public S3Writer(AmazonS3 amazonS3) {
    this.amazonS3 = amazonS3;
  }

  public void writeObject() {

    PutObjectRequest request = new PutObjectRequest(
        BUCKET_NAME,
        UUID.randomUUID().toString(),
        new ByteArrayInputStream("test content".getBytes(StandardCharsets.UTF_8)),
        new ObjectMetadata()
    );
    amazonS3.putObject(request);
  }
}
