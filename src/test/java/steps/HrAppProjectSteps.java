package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.HrAppProjectPage;
import utilities.Configuration;
import utilities.Driver;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HrAppProjectSteps {

    WebDriver driver = Driver.getDriver();
    List<Map<String, Object>> dataFromDatabase = new ArrayList<>();
    List<WebElement> listOfFirstData = new ArrayList<>();
    List<WebElement> listOfSecondData = new ArrayList<>();
    HrAppProjectPage projectPage = new HrAppProjectPage();

    @Given("user navigates to HRPage")
    public void user_navigates_to_HRPage() {
        driver.get(Configuration.getProperty("HRAppProject"));
    }

    @When("user gets data from database who works in Europe")
    public void user_gets_data_from_database_who_works_in_Europe() throws SQLException {
        JDBCUtils.establishConnection();
        String query = "select e.first_name data1, e.last_name data2 from employees e " +
                "inner join departments d on e.department_id=d.department_id " +
                "inner join locations l on d.location_id=l.location_id " +
                "inner join countries c on l.country_id=c.country_id " +
                "inner join regions r on c.region_id=r.region_id and r.region_name='Europe'";
        dataFromDatabase = JDBCUtils.runQuery(query);
        JDBCUtils.closeDatabase();
    }

    @When("user gets first name and last name")
    public void user_gets_first_name_and_last_name() {
        listOfFirstData = projectPage.listOfFirstName;
        listOfSecondData = projectPage.listOfLastName;
    }

    @When("user gets number of employees from database")
    public void user_gets_number_of_employees_from_database() throws SQLException {
        JDBCUtils.establishConnection();
        String query = "select count(*) data2, d.department_name data1 " +
                "from employees e " +
                "inner join departments d on e.department_id=d.department_id " +
                "group by d.department_name";
        dataFromDatabase = JDBCUtils.runQuery(query);
        JDBCUtils.closeDatabase();
    }

    @When("user gets number of employees from UI")
    public void user_gets_number_of_employees_from_UI() {
        listOfFirstData = projectPage.listOfDepartmentName;
        listOfSecondData = projectPage.listOfNumberOfEmployees;
    }

    @Then("validates data from database ara matching in UI")
    public void validates_data_from_database_ara_matching_in_UI() {
        String expectedData = "";
        for (int i = 0; i < listOfFirstData.size(); i++) {
            expectedData = expectedData + listOfFirstData.get(i).getText() + listOfSecondData.get(i).getText() + " ";
        }

        for (int i = 0; i < dataFromDatabase.size(); i++) {
            String actualData = dataFromDatabase.get(i).get("DATA1").toString() + dataFromDatabase.get(i).get("DATA2").toString();
            System.out.println(i + ". " + actualData);
            Assert.assertTrue(actualData + " didn't match", expectedData.contains(actualData));
        }

//        2. Solution
//        boolean isTrue = false;
//        for (int i = 0; i< dataFromDatabase.size(); i++ ) {
//            for (int j = 0; j < listOfFirstData.size(); j++) {
//                isTrue = (listOfFirstData.get(j).getText() + " " + listOfSecondData.get(j).getText()).
//                        equals(dataFromDatabase.get(i).get("DATA1").toString() + " " + dataFromDatabase.get(i).get("DATA2").toString());
//                if(isTrue) break;
//            }
//            Assert.assertTrue(dataFromDatabase.get(i).get("DATA1").toString() + " " + dataFromDatabase.get(i).get("DATA2").toString() +
//                            " -> didn't match from Database with UI", isTrue);
//        }
    }
}
