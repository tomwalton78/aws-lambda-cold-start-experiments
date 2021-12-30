package lambdas;

import java.util.Map;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleLambda implements RequestHandler<Map<String, String>, String> {

    private static final Logger LOGGER = LogManager.getLogger(ExampleLambda.class);

    private final ObjectMapper objectMapper;
    private final DynamoWriter dynamoWriter;
    private final S3Writer s3Writer;

    public ExampleLambda() {
        Injector injector = Guice.createInjector(new LambdaModule());
        this.objectMapper = injector.getInstance(ObjectMapper.class);
        this.dynamoWriter = injector.getInstance(DynamoWriter.class);
        this.s3Writer = injector.getInstance(S3Writer.class);
    }

    @Override
    public String handleRequest(final Map<String, String> event, final Context context) {
        LOGGER.info("Received event: " + event);

        doGuavaWork();

        doJacksonWork(event, context);

        doWriteToDynamoWork();

        doWriteToS3Work();

        return null;
    }

    private void doGuavaWork() {
        LOGGER.info("Starting Guava work");
        // using an external dependency from a layer
        final String msg = Joiner.on(" ").join("Hello", "from", "Java!");
        LOGGER.info(msg);
        LOGGER.info("Finished Guava work");
    }

    private void doJacksonWork(final Map<String, String> event, final Context context) {
        LOGGER.info("Starting Jackson work");
        try {
            this.objectMapper.writeValueAsString(event);
            this.objectMapper.writeValueAsString(context.getClientContext());
        } catch (JsonProcessingException ex) {
           throw new RuntimeException(ex);
        }

        LOGGER.info("Finishing Jackson work");
    }

    private void doWriteToDynamoWork() {
        LOGGER.info("Starting writing to Dynamo work");
        this.dynamoWriter.writeData();
        LOGGER.info("Finished writing to Dynamo work");
    }


    private void doWriteToS3Work() {
        LOGGER.info("Starting writing to S3 work");
        this.s3Writer.writeObject();
        LOGGER.info("Finished writing to S3 work");
    }
}