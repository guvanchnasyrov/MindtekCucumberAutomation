package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pages.EtsyHomePage;
import utilities.Configuration;
import utilities.Driver;

import java.util.List;

public class EtsyAppSteps {

    WebDriver driver = Driver.getDriver();
    EtsyHomePage homePage = new EtsyHomePage();

    @Given("User navigates to Etsy application")
    public void user_navigates_to_Etsy_application() {
        driver.get(Configuration.getProperty("EtsyURL"));
    }

    @When("User searches for {string}")
    public void user_searches_for(String item) {
        homePage.searchBox.sendKeys(item, Keys.ENTER);
    }

    @Then("User validates search results contain")
    public void user_validates_search_results_contain(DataTable dataTable) {
        List<String> keywords = dataTable.asList();
//        System.out.println(keywords.get(0));
//        System.out.println(keywords.get(2));
        for (int i = 0; i < homePage.productList.size(); i++) {
            String itemDescription = homePage.productList.get(i).getText();
            System.out.println(itemDescription);
            boolean isFound = false;
            for (int k = 0; k < keywords.size(); k++) {
                if (itemDescription.toLowerCase().contains(keywords.get(k))) {
                    isFound = true;
                }
            }
            Assert.assertTrue("Failed: -> " + itemDescription, isFound);
        }

    }

    @When("User selects price range more than {int}")
    public void user_selects_price_range_more_than(Integer priceRange) {
        homePage.filtersButton.click();
        homePage.over1000.click();
        homePage.applyButton.click();
    }

    @Then("User validates price range is more than {int}")
    public void user_validates_price_range_is_more_than(Integer priceRange) {
        for (int i = 0; i < homePage.priceList.size(); i++) {
            double actualPrice = Double.parseDouble(homePage.priceList.get(i).getText().replaceAll("\\,", ""));
            //System.out.println(actualPrice);
            Assert.assertTrue("Failed: -> " + actualPrice, actualPrice >= priceRange);
        }
    }

    @When("User clicks on {string} module")
    public void user_clicks_on_module(String module) {
        driver.findElement(By.xpath("//a[@class='wt-text-link-no-underline']//span[contains(text(),'" + module + "')]")).click();
    }

    @Then("User validates title {string}")
    public void user_validates_title(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @When("User selects price range between {int} and {int}")
    public void user_selects_price_range_between_and(Integer lowPrice, Integer highPrice) {
        homePage.filtersButton.click();
        if (lowPrice == 0 && highPrice == 50) {
            homePage.under50.click();
        } else if (lowPrice == 50 && highPrice == 500) {
            homePage.from50to500.click();
        } else if (lowPrice == 500 && highPrice == 1000) {
            homePage.from500to1000.click();
        } else if (lowPrice > 0 && highPrice > 0) {
            homePage.minPrice.sendKeys(lowPrice + "");
            homePage.maxPrice.sendKeys(highPrice + "");
        }
        homePage.applyButton.click();
    }

    @Then("User validates price range between {int} and {int}")
    public void user_validates_price_range_between_and(Integer lowPrice, Integer highPrice) {
        for (int i = 0; i < homePage.priceList.size(); i++) {
            double actualPrice = Double.parseDouble(homePage.priceList.get(i).getText().replaceAll("\\,", "").replaceAll("\\$", ""));
            boolean actualResult = actualPrice >= lowPrice && actualPrice < highPrice;
            Assert.assertTrue("Failed: -> " + actualPrice, actualResult);
        }
    }

}
