package cdk;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.dynamodb.*;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.RoleProps;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.*;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketProps;

import java.util.Arrays;

public class Stack extends software.amazon.awscdk.core.Stack {

  public Stack(final Construct scope, final String id) {
    this(scope, id, null);
  }

  public Stack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);

    // Create a layer from the layer module
    final LayerVersion layer = new LayerVersion(this, "layer", LayerVersionProps.builder()
        .code(Code.fromAsset("../layer/target/bundle"))
        .compatibleRuntimes(Arrays.asList(Runtime.JAVA_11))
        .build()
    );

    Role lambdaRole = new Role(this, "lambdaRole", RoleProps.builder()
        .assumedBy(new ServicePrincipal("lambda.amazonaws.com"))
        .managedPolicies(Arrays.asList(
            ManagedPolicy.fromAwsManagedPolicyName("CloudWatchFullAccess"),
            ManagedPolicy.fromAwsManagedPolicyName("AmazonDynamoDBFullAccess"),
            ManagedPolicy.fromAwsManagedPolicyName("AWSXrayFullAccess")
        ))
        .build());

    // The code that defines your stack goes here
    new Function(this, "ColdStartJavaFunction", FunctionProps.builder()
        .runtime(Runtime.JAVA_11)
        .code(Code.fromAsset("../lambdas/target/lambdas.jar"))
        .handler("lambdas.ExampleLambda")
        .layers(Arrays.asList(layer))
        .memorySize(512)
        .timeout(Duration.seconds(30))
        .logRetention(RetentionDays.ONE_WEEK)
        .functionName("ColdStartJavaFunction")
        .role(lambdaRole)
        .tracing(Tracing.ACTIVE)
        .build());

    new Table(this, "testTable", TableProps.builder()
        .billingMode(BillingMode.PAY_PER_REQUEST)
        .partitionKey(Attribute.builder()
            .name("id")
            .type(AttributeType.STRING)
            .build())
        .tableName("testTable")
        .build());

    Bucket bucket = new Bucket(this, "test-s3-bucket", BucketProps.builder()
        .bucketName("my-test-3-bucket-for-aws-lambda-cold-start-experiments")
        .build());
    bucket.grantWrite(lambdaRole);

  }

  public static void main(final String[] args) {
    final App app = new App();
    new Stack(app, "Stack", StackProps.builder().build());
    app.synth();
  }
}