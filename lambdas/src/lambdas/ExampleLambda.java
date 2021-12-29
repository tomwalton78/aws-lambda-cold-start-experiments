package lambdas;

import java.util.Map;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleLambda implements RequestHandler<Map<String, String>, String> {

    private static final Logger LOGGER = LogManager.getLogger(ExampleLambda.class);

    @Override
    public String handleRequest(final Map<String, String> event, final Context context) {
        LOGGER.info("Received event: " + event);
        // using an external dependency from a layer
        final String msg = Joiner.on(" ").join("Hello", "from", "Java!");
        LOGGER.info(msg);
        return null;
    }
}