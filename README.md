# aws-lambda-cold-start-experiments

Contains code (CDK infrastructure and Java lambda function) used in experiments on AWS Lambda cold start latency when using Java.

Prerequisites: https://docs.aws.amazon.com/cdk/v2/guide/getting_started.html

This is a project for Java development with CDK.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

It is a [Maven](https://maven.apache.org/) based project, so you can open this project with any Maven compatible Java IDE to build and run tests.

## Commands

**Note:** CDK is required on your machine to deploy the solution

`mvn clean package` - build the solution

`cd infra; cdk deploy` - deploy the solution

# Structure

* `cdk` - Deploys lambda with CDK
* `lambdas` - Contains lambda functions
* `layer` - Creates layer for use by the lambdas