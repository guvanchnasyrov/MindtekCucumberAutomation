package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.WebOrdersLoginPage;
import pages.WebOrdersHomePage;
import utilities.BrowserUtils;
import utilities.Configuration;
import utilities.Driver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WebOrdersSteps {

    WebDriver driver = Driver.getDriver();
    WebOrdersLoginPage loginPage = new WebOrdersLoginPage();
    WebOrdersHomePage homePage = new WebOrdersHomePage();
    List<Map<String, Object>> data = new ArrayList<>();
    String product = "";

    @Given("User navigates to application")
    public void user_navigates_to_application() {
        driver.get(Configuration.getProperty("URL"));
    }

    @When("User logs in with username {string} and password {string}")
    public void user_logs_in_with_username_and_password(String username, String password) {
        loginPage.username.sendKeys(username);
        loginPage.password.sendKeys(password);
        loginPage.loginButton.click();
    }

    @Then("User validates that application is on homepage")
    public void user_validates_that_application_is_on_homepage() {
        String expectedTitle = "Web Orders";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Then("User validates error login message {string}")
    public void user_validates_error_login_message(String expectedErrorMessage) {
        String actualErrorMessage = loginPage.errorMessage.getText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @When("User clicks on Order module")
    public void user_clicks_on_Order_module() {
        homePage.orderButton.click();
    }

    @When("User provides product {string} and quantity {int}")
    public void user_provides_product_and_quantity(String product, Integer quantity) {
        BrowserUtils.selectDropdownByValue(homePage.product, product);
        homePage.quantity.sendKeys(Keys.BACK_SPACE);
        homePage.quantity.sendKeys(quantity.toString());
        homePage.quantity.sendKeys(Keys.ENTER);
    }

    @Then("User validates total is calculated properly for quantity {int}")
    public void user_validates_total_is_calculated_properly_for_quantity(Integer quantity) {
        // <input id="sdfsdfsdf" name="sdfsdf" value="80"> sometimes value can be hidden
        double unitPrice = Double.parseDouble(homePage.unitPrice.getAttribute("value"));
        double discount = Double.parseDouble(homePage.discount.getAttribute("value"));
        double expectedTotal = quantity * unitPrice * (100 - discount) / 100;
        int actualTotal = Integer.parseInt(homePage.totalPrice.getAttribute("value"));
        Assert.assertEquals(actualTotal, (int) expectedTotal);
    }

    @When("User creates and order with data")
    public void user_creates_and_order_with_data(DataTable dataTable) {
        // Convert dataTable is List of Maps -> List<Map<String,Object>>
        data = dataTable.asMaps(String.class, Object.class);
        for (int i = 0; i < data.size(); i++) {
            BrowserUtils.selectDropdownByValue(homePage.product, data.get(i).get("Product").toString());
            homePage.quantity.sendKeys(Keys.BACK_SPACE);
            homePage.quantity.sendKeys(data.get(i).get("Quantity").toString());
            homePage.customerName.sendKeys(data.get(i).get("Customer name").toString());
            homePage.street.sendKeys(data.get(i).get("Street").toString());
            homePage.city.sendKeys(data.get(i).get("City").toString());
            homePage.state.sendKeys(data.get(i).get("State").toString());
            homePage.zipcode.sendKeys(data.get(i).get("Zip").toString());
            homePage.visa.click();
            homePage.cardNumber.sendKeys(data.get(i).get("Card Nr").toString());
            homePage.expireDate.sendKeys(data.get(i).get("Exp Date").toString());
            homePage.processButton.click();
        }
    }

    @Then("User validates success message {string}")
    public void user_validates_success_message(String expectedMessage) {
        String actualMessage = homePage.successMessage.getText();
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Then("User validates that created orders are in the list of all orders")
    public void user_validates_that_created_orders_are_in_the_list_of_all_orders() throws ParseException {
        homePage.viewAllOrdersButton.click();
        int numberOfCreatedOrders = data.size();
        for (int i = 0; i < numberOfCreatedOrders; i++) {
            List<WebElement> row = driver.findElements(By.xpath("//table[@class='SampleTable']//tr[" + (numberOfCreatedOrders + 1 - i) + "]/td"));
            Assert.assertEquals(row.get(1).getText(), data.get(i).get("Customer name"));
            Assert.assertEquals(row.get(2).getText(), data.get(i).get("Product"));
            Assert.assertEquals(row.get(3).getText(), data.get(i).get("Quantity"));

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Karachi"));
            Date todayDate =  new Date();  // dateFormat.parse("04/29/2021");
            Assert.assertEquals(row.get(4).getText(),dateFormat.format(todayDate));

            Assert.assertEquals(row.get(5).getText(), data.get(i).get("Street"));
            Assert.assertEquals(row.get(6).getText(), data.get(i).get("City"));
            Assert.assertEquals(row.get(7).getText(), data.get(i).get("State"));
            Assert.assertEquals(row.get(8).getText(), data.get(i).get("Zip"));
            Assert.assertEquals(row.get(9).getText(), data.get(i).get("Card"));
            Assert.assertEquals(row.get(10).getText(), data.get(i).get("Card Nr"));
            Assert.assertEquals(row.get(11).getText(), data.get(i).get("Exp Date"));
        }

    }

    @When("User clicks on View all orders module")
    public void user_clicks_on_View_all_orders_module() {
        homePage.viewAllOrdersButton.click();
    }

    @When("User deletes selected products {string}")
    public void user_deletes_selected_products(String product) {
        this.product = product;
        List<WebElement> products = homePage.orderedProducts;
        List<WebElement> checkbox = homePage.checkbox;
        for(int i =0; i< products.size();i++){
            if (products.get(i).getText().equals(product)){
                checkbox.get(i).click();
            }
        }
        homePage.deleteSelectedButton.click();
    }

    @Then("User validates deleted orders")
    public void user_validates_deleted_orders() {
        List<WebElement> products = homePage.orderedProducts;
        for(int i =0; i< products.size();i++){
            Assert.assertNotEquals(product, products.get(i).getText());
        }
    }

    @When("User edits selected order data")
    public void user_edits_selected_order_data(DataTable dataTable) {
        data = dataTable.asMaps(String.class, Object.class);
        List<WebElement> names = homePage.customerNames;
        List<WebElement> edit = homePage.editButtons;
        for (int i = 0; i< names.size();i++){
            if (names.get(i).getText().equals(data.get(0).get("Name"))){
                edit.get(i).click();
                break;
            }
        }
    }

    @Then("User validates edited selected order data")
    public void user_validates_edited_selected_order_data() {
        Assert.assertEquals(homePage.selectedProduct.getAttribute("value"), data.get(0).get("Product"));
        Assert.assertEquals(homePage.quantity.getAttribute("value"), data.get(0).get("Quantity"));
        Assert.assertEquals(homePage.customerName.getAttribute("value"), data.get(0).get("Name"));
        Assert.assertEquals(homePage.street.getAttribute("value"), data.get(0).get("Street"));
        Assert.assertEquals(homePage.city.getAttribute("value"), data.get(0).get("City"));
        Assert.assertEquals(homePage.state.getAttribute("value"), data.get(0).get("State"));
        Assert.assertEquals(homePage.zipcode.getAttribute("value"), data.get(0).get("Zip"));
        // We can't validate card type because it's not selected in edit order
        /*if (data.get(0).get("Card").equals("Visa")) {
            Assert.assertTrue(homePage.visa.isSelected());
            Assert.assertFalse(homePage.americanExpress.isSelected());
            Assert.assertFalse(homePage.masterCard.isSelected());
        } else if (data.get(0).get("Card").equals("MasterCard")) {
            Assert.assertTrue(homePage.masterCard.isSelected());
            Assert.assertFalse(homePage.americanExpress.isSelected());
            Assert.assertFalse(homePage.visa.isSelected());
        } else if (data.get(0).get("Card").equals("American Express")) {
            Assert.assertTrue(homePage.americanExpress.isSelected());
            Assert.assertFalse(homePage.masterCard.isSelected());
            Assert.assertFalse(homePage.visa.isSelected());
        }*/
        Assert.assertEquals(homePage.cardNumber.getAttribute("value"), data.get(0).get("Card Number"));
        Assert.assertEquals(homePage.expireDate.getAttribute("value"), data.get(0).get("Exp"));

    }



}
