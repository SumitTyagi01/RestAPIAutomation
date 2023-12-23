package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification req;

    public RequestSpecification requestSpecification() throws IOException {

    if(req == null) {
        PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
        if(System.getProperty("env").equalsIgnoreCase("dev")) {
            req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .addHeader("Content-Type", "application/json")
                    .setContentType(ContentType.JSON).build();
        }
        else if(System.getProperty("env").equalsIgnoreCase("qa")) {
            req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .addHeader("Content-Type", "application/json")
                    .setContentType(ContentType.JSON).build();
        }
        return req;
      }
        return req;
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("C:\\Users\\Sumit\\Downloads\\demo (1)\\APIAutomationFramework\\src\\test\\java\\resources\\global_"+System.getProperty("env")+".properties");
        prop.load(fis);
        return prop.getProperty(key);

    }

    public String getJsonPath(Response res, String key){
        String response = res.asString();
        JsonPath js =  new JsonPath(response);
        return js.get(key).toString();
    }
}
