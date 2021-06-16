package examples;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiTest {

    public static void main(String[] args) {

        /*
        Do GET Call.
        get employee with employee id 100
        /api/employees/100
         */

        /*
        Get Call:
        URL + Headers
        URL = BaseURL+Endpoint
        Given BaseURL
        And Headers (Accept -> application/json)
        When Endpoint (make a get call with endpoint)
         */

        Response response =
                given().baseUri("https://devenv-apihr-arslan.herokuapp.com") // BaseURL
                .and().accept(ContentType.JSON) // Header (Accept)
                .when().get("/api/employees/123"); // call type and endpoint

        response.then().log().all();
        System.out.println("\n"+response.statusCode());
        System.out.println("\n"+response.body().asString());
        System.out.println("\n"+response.body().as(HashMap.class));

        Map<String, Object> responseData = response.body().as(HashMap.class);
        System.out.println(responseData.get("employeeId"));

        /*
        Post Call:
        Request: BaseURL + Endpoint + Headers + Json body
        Given BaseURL
        And Headers (Accept -> application/json)
        And Headers (content type -> application/json)
        When Json body
        And send post call
         */

        Response postResponse =
                given().baseUri("https://devenv-apihr-arslan.herokuapp.com")
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().body("{\n" +
                        "    \"department\": {\n" +
                        "        \"departmentId\": 60,\n" +
                        "        \"departmentName\": \"IT\",\n" +
                        "        \"location\": {\n" +
                        "            \"locationCity\": \"Austin\",\n" +
                        "            \"locationCountry\": \"United States\",\n" +
                        "            \"locationId\": 123,\n" +
                        "            \"locationState\": \"TX\"\n" +
                        "        }\n" +
                        "    },\n" +
                        "    \"employeeId\": 301,\n" +
                        "    \"firstName\": \"Smith\",\n" +
                        "    \"job\": {\n" +
                        "        \"jobId\": \"IT+PROG\",\n" +
                        "        \"salary\": 90000,\n" +
                        "        \"title\": \"SDET\"\n" +
                        "    },\n" +
                        "    \"lastName\": \"John\"\n" +
                        "}")
                .and().post("/api/employees");

        System.out.println("\n" + postResponse.statusCode());

        /*
        Request
        Delete call:
        Given BaseUrl
        When delete call

        Response
        Status code
         */

        Response deleteResponse = given()
                .baseUri("https://devenv-apihr-arslan.herokuapp.com")
                .when().delete("/api/employees/215");
        System.out.println("\n" + deleteResponse.statusCode());



    }

}
