package utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiUtils {
    /*
    Static method
        .getCall(String endpoint); -> return response;
        .postCall(String endpoint, String body);  -> return response;
        .putCall(String endpoint, String body);  -> return response;
        .deleteCall(String endpoint);  -> return response;
     */

    public static Response getCall(String endpoint) {
        Response response =
                given().baseUri(Configuration.getProperty("ApiBaseURL"))
                .and().accept(ContentType.JSON)
                .when().get(endpoint);
        return response;
    }

    public static Response postCall(String endpoint, Object body) {
        Response response =
                given().baseUri(Configuration.getProperty("ApiBaseURL"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().body(body)
                .and().post(endpoint);
        return response;
    }

    public static Response putCall(String endpoint, Object body) {
        Response response =
                given().baseUri(Configuration.getProperty("ApiBaseURL"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().body(body)
                .and().put(endpoint);
        return response;
    }

    public static Response deleteCall(String endpoint) {
        Response response =
                given().baseUri(Configuration.getProperty("ApiBaseURL"))
                .when().delete(endpoint);
        return response;
    }

}
