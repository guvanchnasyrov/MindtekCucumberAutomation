package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pages.HrAppEmployeePage;
import pages.HrAppLoginPage;
import utilities.Configuration;
import utilities.Driver;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class HrAppSteps {

    WebDriver driver = Driver.getDriver();
    HrAppLoginPage loginPage = new HrAppLoginPage();
    HrAppEmployeePage employeePage = new HrAppEmployeePage();

    @Given("user navigates to login page")
    public void user_navigates_to_login_page() {
        driver.get(Configuration.getProperty("HrURL"));
        Assert.assertEquals("HrApp", driver.getTitle());
    }

    @When("user logs in to HrApp")
    public void user_logs_in_to_HrApp() {
        loginPage.username.sendKeys(Configuration.getProperty("HrAppUsername"));
        loginPage.password.sendKeys(Configuration.getProperty("HrAppPassword"));
        loginPage.loginButton.click();
    }

    @When("creates new employee")
    public void creates_new_employee() {
        System.out.println("New employee created");
    }

    @Then("user validates new employee is created in database")
    public void user_validates_new_employee_is_created_in_database() throws SQLException {
        employeePage.searchBox.sendKeys("101" + Keys.ENTER);
        String expectedFirstName = employeePage.firstname.getText();
        String expectedLastName = employeePage.lastname.getText();
        String expectedDepartmentName = employeePage.departmentName.getText();
//        System.out.println(expected);

        JDBCUtils.establishConnection();
        List<Map<String,Object>> actual = JDBCUtils.runQuery("select * from employees e inner join departments d " +
                "on d.department_id=e.department_id where employee_id = 101");
//        System.out.println(actual.get(0));

        Assert.assertEquals(expectedFirstName,actual.get(0).get("FIRST_NAME"));
        Assert.assertEquals(expectedLastName,actual.get(0).get("LAST_NAME"));
        Assert.assertEquals(expectedDepartmentName, actual.get(0).get("DEPARTMENT_NAME"));

        JDBCUtils.closeDatabase();
    }

}
