package examples;

import io.restassured.response.Response;
import pojos.Department;
import pojos.Employee;
import pojos.Job;
import utilities.ApiUtils;

public class ApiTest3 {
    public static void main(String[] args) {
        /*
        Create an Employee
        POST call
        1. Endpoint
        2. Headers --- we don't need it
        3. Json
         */
        String endpoint = "/api/employees";
        Employee requestBody = new Employee();
        requestBody.setFirstName("John");
        requestBody.setLastName("Smith");

        Department department = new Department();
        department.setDepartmentName("IT");
        requestBody.setDepartment(department);

        Job job = new Job();
        job.setTitle("SDET");
        job.setSalary(104500.0);
        requestBody.setJob(job);

        // POJO(requestBody) -> JSON ===> Serialization
        Response response = ApiUtils.postCall(endpoint, requestBody);
        System.out.println(response.statusCode());

    }
}
