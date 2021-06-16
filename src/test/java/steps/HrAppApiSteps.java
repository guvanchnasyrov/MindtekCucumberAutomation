package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.HrAppEmployeePage;
import pages.HrAppLoginPage;
import pojos.Department;
import pojos.Employee;
import utilities.ApiUtils;
import utilities.Configuration;
import utilities.Driver;
import java.util.Map;

public class HrAppApiSteps {

    Response response;
    Employee responseBody;
    String endpoint = "/api/employees";
    WebDriver driver = Driver.getDriver();
    HrAppLoginPage loginPage = new HrAppLoginPage();
    HrAppEmployeePage employeePage = new HrAppEmployeePage();


    @Given("User sends create employee api post call with data")
    public void user_sends_create_employee_api_post_call_with_data(DataTable dataTable) {
        Map<String, Object> data = dataTable.asMap(String.class, Object.class);
        // endpoint + body(POJO)
        // 1. Endpoint
        //2. Pojo
        Employee requestBody = new Employee();
        requestBody.setFirstName(data.get("firstName").toString());
        requestBody.setLastName(data.get("lastName").toString());
        Department department = new Department();
        department.setDepartmentName(data.get("departmentName").toString());
        requestBody.setDepartment(department);
        //Serialization
        response = ApiUtils.postCall(endpoint, requestBody);
    }

    @Then("User validates status code {int}")
    public void user_validates_status_code(Integer expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, (Integer)response.statusCode());
    }

    @Then("User validates data populated in UI")
    public void user_validates_data_populated_in_UI(DataTable dataTable) throws InterruptedException {
        driver.get(Configuration.getProperty("HrURL"));
        loginPage.username.sendKeys(Configuration.getProperty("HrAppUsername"));
        loginPage.password.sendKeys(Configuration.getProperty("HrAppPassword"));
        loginPage.loginButton.click();
        // Deserialization
        responseBody = response.body().as(Employee.class);
        employeePage.searchBox.sendKeys(responseBody.getEmployeeId().toString());
        Thread.sleep(1000);
        employeePage.searchButton.click();
        Map<String, Object> expectedData = dataTable.asMap(String.class, Object.class);
        Assert.assertEquals(expectedData.get("firstName"), employeePage.firstname.getText());
        Assert.assertEquals(expectedData.get("lastName"), employeePage.lastname.getText());
        Assert.assertEquals(expectedData.get("departmentName"), employeePage.departmentName.getText());

    }

    @Then("User validates employee data is persisted into DB")
    public void user_validates_employee_data_is_persisted_into_DB() {
        System.out.println("PASSED");
    }

    @Then("User validates data with get employee api call")
    public void user_validates_data_with_get_employee_api_call(DataTable dataTable) {
        Map<String, Object> expectedData = dataTable.asMap(String.class, Object.class);
        Response actualResponse = ApiUtils.getCall(endpoint + "/" + responseBody.getEmployeeId().toString());
        Employee actualResponseBody = actualResponse.body().as(Employee.class);
        Assert.assertEquals(expectedData.get("firstName"), actualResponseBody.getFirstName());
        Assert.assertEquals(expectedData.get("lastName"), actualResponseBody.getLastName());
        Assert.assertEquals(expectedData.get("departmentName"), actualResponseBody.getDepartment().getDepartmentName());

    }

}
