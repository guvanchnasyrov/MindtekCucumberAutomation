package utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiUtilsB {
    /*
    .getAccessToken(); -> this method will return token as String value
     */

    public static String getAccessToken() {
        Response response = given().baseUri(Configuration.getProperty("ApiBankBaseURL"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body("{\n" +
                        "    \"password\": \"MindtekStudent\",\n" +
                        "    \"username\": \"Mindtek\"\n" +
                        "}")
                .when().post("/authenticate");
        response.then().log().all();
        String token = response.jsonPath().getString("jwt");
        return token;
    }

    /*
    .setHeader(); -> this method will set headers to api call
    and will return (RequestSpecifications or Map of headers)
    */

    public static Map<String, Object> setHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        return headers;
    }

    public static Response getCall(String endpoint) {
        Response response =
                given().baseUri(Configuration.getProperty("ApiBankBaseURL"))
                        .and().headers(setHeaders())
                        .when().get(endpoint);
        response.then().log().all();
        return response;
    }

    public static Response getCall(String endpoint, Map<String, Object> queryParams) {
        Response response =
                given().baseUri(Configuration.getProperty("ApiBankBaseURL"))
                        .and().headers(setHeaders())
                        .and().queryParams(queryParams)
                        .when().get(endpoint);
        response.then().log().all();
        return response;
    }

    public static Response postCall(String endpoint, Object body) {
        Response response =
                given().baseUri(Configuration.getProperty("ApiBankBaseURL"))
                        .and().headers(setHeaders())
                        .when().body(body)
                        .and().post(endpoint);
        response.then().log().all();
        return response;
    }

    public static Response putCall(String endpoint, Object body) {
        Response response =
                given().baseUri(Configuration.getProperty("ApiBankBaseURL"))
                        .and().headers(setHeaders())
                        .when().body(body)
                        .and().put(endpoint);
        response.then().log().all();
        return response;
    }

    public static Response deleteCall(String endpoint) {
        Response response =
                given().baseUri(Configuration.getProperty("ApiBankBaseURL"))
                        .and().headers(setHeaders())
                        .when().delete(endpoint);
        response.then().log().all();
        return response;
    }
}
