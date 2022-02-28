package examples;

import io.restassured.response.Response;
import org.openqa.selenium.support.ui.Select;
import pojos.Employee;
import utilities.ApiUtils;

public class ApiTest2 {

    public static void main(String[] args) {
        Response response1 = ApiUtils.getCall("/api/employees/100");
        System.out.println(response1.body().asString());

        response1.then().log().all();

        Employee responseBody = new Employee();

        // Deserialization -> Converting JSON to POJO
        responseBody = response1.body().as(Employee.class);

        System.out.println(responseBody.getFirstName() + " " + responseBody.getLastName());
        System.out.println(responseBody.getJob().getSalary());
        System.out.println(responseBody.getDepartment().getLocation().getLocationCity());
        System.out.println(responseBody.getDepartment().getDepartmentName());




        /*
            Assignment:
            Create Department with name "Student Relations"
            Get Department
            Update Department name with to "SR"
            Delete Department
            ** Api calls
        */

//        Response response = ApiUtils.postCall("/api/departments","{\n" +
//                "    \"departmentId\": 300,\n" +
//                "    \"departmentName\": \"Student Relations\",\n" +
//                "    \"location\": {\n" +
//                "        \"locationCountry\": \"US\",\n" +
//                "        \"locationState\": \"Washington\",\n" +
//                "        \"locationCity\": \"Seattle\",\n" +
//                "        \"locationId\": 1700\n" +
//                "    }\n" +
//                "}");
//        System.out.println("POST Status Code: " + response.statusCode());
//
//        Response getResponse = ApiUtils.getCall("/api/departments/300");
//        System.out.println("GET Status Code: " + getResponse.statusCode());
//
//        Response updateResponse = ApiUtils.putCall("/api/departments","{\n" +
//                "    \"departmentId\": 300,\n" +
//                "    \"departmentName\": \"SR\",\n" +
//                "    \"location\": {\n" +
//                "        \"locationCity\": \"Austin\",\n" +
//                "        \"locationCountry\": \"US\",\n" +
//                "        \"locationId\": 82645086,\n" +
//                "        \"locationState\": \"TX\"\n" +
//                "    }\n" +
//                "}");
//        System.out.println("PUT Status Code: " + updateResponse.statusCode());
//
//        Response deleteResponse = ApiUtils.deleteCall("/api/departments/300");
//        System.out.println("DELETE Status Code: " + deleteResponse.statusCode());


    }

}
