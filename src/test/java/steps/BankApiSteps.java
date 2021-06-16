package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import pojos.bank.Account;
import pojos.bank.Customer;
import utilities.ApiUtilsB;
import utilities.Configuration;

import java.util.*;

import static io.restassured.RestAssured.given;

public class BankApiSteps {

    Response response;
    String customerId;
    String accountId;

    @Given("User sends get customers api call with access token")
    public void user_sends_get_customers_api_call_with_access_token() {
        response = ApiUtilsB.getCall("/api/customers");
    }

    @Then("User validates {int} status code")
    public void user_validates_status_code(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    @Given("User sends get customers api call without access token")
    public void user_sends_get_customers_api_call_without_access_token() {
        response = given().baseUri(Configuration.getProperty("ApiBankBaseURL"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().get("/api/customers");
    }

    @Given("User creates customers with post api call using data")
    public void user_creates_customers_with_post_api_call_using_data(DataTable dataTable) {
        List<Map<String, Object>> data = dataTable.asMaps(String.class, Object.class);
        String endpoint = "/api/customer";
        for (int i = 0; i < data.size(); i++) {
            Customer customer = new Customer();
            customer.setFullName(data.get(i).get("Name").toString());
            customer.setAddress(data.get(i).get("Address").toString());
            customer.setIsActive(Boolean.valueOf(data.get(i).get("isActive").toString()));
            // body -> customer
            // endpoint
            response = ApiUtilsB.postCall(endpoint, customer);
            Assert.assertEquals(201, response.getStatusCode());
        }
    }

    @When("User sends get customers api call with {string} order")
    public void user_sends_get_customers_api_call_with_order(String order) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("order", order);
        response = ApiUtilsB.getCall("/api/customers", queryParams);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("User validates that customers are in {string} order")
    public void user_validates_that_customers_are_in_order(String order) {
        //Storing response body into Customers array
        Customer[] customers = response.body().as(Customer[].class);
        // Validating customers array names are in ascending order
        // We can create a new list and we will save original values in that list
        // then we will sort customers and then compare if they are equal
        List<Customer> actualCustomers = Arrays.asList(customers);
        List<Customer> expectedCustomers = actualCustomers;
        if (order.equalsIgnoreCase("asc")) {
            Collections.sort(actualCustomers, new Customer()); //this will sort customers by ascending order
        } else if (order.equalsIgnoreCase("desc")) {
            Collections.sort(actualCustomers, Collections.reverseOrder(new Customer())); //this will sort customers by descending order
        }
        Assert.assertEquals(expectedCustomers, actualCustomers);
    }

    @When("User sends get customers api call with {string} limit")
    public void user_sends_get_customers_api_call_with_limit(String limit) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        response = ApiUtilsB.getCall("/api/customers", queryParams);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("User validates that get customers response has {string} customers")
    public void user_validates_that_get_customers_response_has_customers(String limit) {
        Customer[] customers = response.body().as(Customer[].class);
        Assert.assertEquals(limit, customers.length + "");
    }

    @Then("User validates that customer is created with data")
    public void user_validates_that_customer_is_created_with_data(DataTable dataTable) {
        List<Map<String, Object>> expectedData = dataTable.asMaps(String.class, Object.class);
        Customer customer = response.body().as(Customer.class); // Json -> POJO = Deserialization
        customerId = customer.getId().toString();
        response = ApiUtilsB.getCall("/api/customers/" + customerId);
        Assert.assertEquals(200, response.getStatusCode());
        customer = response.body().as(Customer.class);
        Assert.assertEquals(expectedData.get(0).get("Name").toString(), customer.getFullName());
        Assert.assertEquals(expectedData.get(0).get("Address").toString(), customer.getAddress());
        Assert.assertEquals(expectedData.get(0).get("isActive").toString(), customer.getIsActive().toString());
    }

    @When("User deletes created customer")
    public void user_deletes_created_customer() {
        response = ApiUtilsB.deleteCall("/api/customers/" + customerId);
        Assert.assertEquals(204, response.getStatusCode());

    }

    @Then("User validates that customer is deleted")
    public void user_validates_that_customer_is_deleted() {
        response = ApiUtilsB.getCall("/api/customers/" + customerId);
        Assert.assertEquals(404, response.getStatusCode());
        String actualErrorMessage = response.jsonPath().getString("message");
        String expectedErrorMessage = "Customer not found with ID " + customerId;
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @When("User creates account for a customer")
    public void user_creates_account_for_a_customer(DataTable dataTable) {
        String endpoint = "/api/account";
        customerId = response.jsonPath().getString("id");
        System.out.println("ID -- " +customerId);
        Map<String, Object> data = dataTable.asMap(String.class, Object.class);
        Account account = new Account();
        account.setAccountType(data.get("accountType").toString());
        account.setBalance(Double.valueOf(data.get("balance").toString()));
        Customer customer = new Customer();
        customer.setId(Integer.valueOf(customerId));
        account.setCustomer(customer);
        response = ApiUtilsB.postCall(endpoint, account);
        accountId = response.jsonPath().getString("id");
        Assert.assertEquals(201, response.getStatusCode());
    }

    @Then("User validates that customer linked to created account")
    public void user_validates_that_customer_linked_to_created_account(DataTable dataTable) {
        String endpoint = "/api/customers/" + customerId;
        response = ApiUtilsB.getCall(endpoint);
        Customer customer = response.body().as(Customer.class);
        Map<String, Object> expectedData = dataTable.asMap(String.class, Object.class);
        Assert.assertEquals(expectedData.get("accountType"), customer.getAccounts().get(0).getAccountType());
        Assert.assertEquals(Double.valueOf(expectedData.get("balance").toString()), customer.getAccounts().get(0).getBalance());
        Assert.assertEquals(Integer.valueOf(customerId), customer.getId());
    }

    @When("User deletes created account")
    public void user_deletes_created_account() {
        /*
        Delete call;
        1. Endpoint: /api/accounts/id -> accountId
         */
        response = ApiUtilsB.deleteCall("/api/accounts/" + accountId);
        Assert.assertEquals(204,response.getStatusCode());
    }

    @Then("User validates that account is deleted")
    public void user_validates_that_account_is_deleted() {
        /*
        GET account api call.
        1. Endpoint: /api/accounts/id
         */
        response = ApiUtilsB.getCall("/api/accounts/" + accountId);
        Assert.assertEquals(404, response.getStatusCode());
        String actualErrorMessage = response.jsonPath().getString("message");
        String expectedErrorMessage = "Account not found with ID " + accountId;
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage);

    }

    @When("User updates account balance with put api call")
    public void user_updates_account_balance_with_put_api_call(DataTable dataTable) {
        String endpoint = "/api/account";
        customerId = response.jsonPath().getString("id");
        Map<String, Object> data = dataTable.asMap(String.class, Object.class);
        Account account = new Account();
        account.setAccountType(data.get("accountType").toString());
        account.setBalance(Double.valueOf(data.get("balance").toString()));
        Customer customer = new Customer();
        customer.setId(Integer.valueOf(customerId));
        account.setCustomer(customer);
        response = ApiUtilsB.putCall(endpoint, account);
        Assert.assertEquals(201, response.getStatusCode());
    }

    @Then("User validates that account updated")
    public void user_validates_that_account_updated(DataTable dataTable) {
        String endpoint = "/api/customers/" + customerId;
        response = ApiUtilsB.getCall(endpoint);
        Customer customer = response.body().as(Customer.class);
        Map<String, Object> expectedData = dataTable.asMap(String.class, Object.class);
        Assert.assertEquals(expectedData.get("accountType"), customer.getAccounts().get(0).getAccountType());
        Assert.assertEquals(Double.valueOf(expectedData.get("balance").toString()), customer.getAccounts().get(0).getBalance());
    }


}
