package resourses;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class Utils {
    public static RequestSpecification req; // not to be treated as null this var should be made static
    // see also explanation below
    public RequestSpecification requestSpecification() throws IOException {
        if (req == null) { // when we use scenario outline logs will be overridden
            // to avoid this we use for loop
            // if req is not null that means that test has already been executed and another req object should be returned (line 32)

            //The if (req == null) condition checks whether the req object (likely a class-level variable) is null.
            // This check helps ensure that a new RequestSpecification is only created if it hasn't been initialized before.

            //A PrintStream named log is created to specify where the request and response logs will be written.
            PrintStream log = new PrintStream("logging.txt");// here we should give a file
            req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log)) //here is how we can see all request login
                    //her we also say where to log to
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))

                    .setContentType(ContentType.JSON).build();
            return req;
        }
        //If req is not null (meaning it has already been created in a previous call), the existing req object is returned.
        return req;
    }

    public static String getGlobalValue (String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/java/resourses/global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public String getJsonPath(Response response, String key) {
        String responseAsString = response.asString();
        JsonPath js = new JsonPath(responseAsString);
        return js.get(key).toString();
    }
}
