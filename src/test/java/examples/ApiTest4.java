package examples;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiTest4 {

    public static void main(String[] args) {
        /*
    Authentication post api call Request
    1. Endpoint
    2. Headers
    3. Body
     */

        Response response = given().baseUri("https://mindtek-restapi.herokuapp.com/")
                .accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body("{\n" +
                        "    \"password\": \"MindtekStudent\",\n" +
                        "    \"username\": \"Mindtek\"\n" +
                        "}")
                .when().post("authenticate");

        String token = response.jsonPath().getString("jwt");
        System.out.println(token+"\n");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        headers.put("Authorization","Bearer " + token);

        Response response1 = given().baseUri("https://mindtek-restapi.herokuapp.com/")
                .and().headers(headers)
                .when().get("api/customers");

        System.out.println(response1.body().asString()+"\n");




    }

}
