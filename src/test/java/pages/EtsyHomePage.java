package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utilities.Driver;

import java.util.List;

public class EtsyHomePage {

    public EtsyHomePage(){
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath ="//*[contains(@data-id, 'search-query')]")
    public WebElement searchBox;

    //h2[@id='search-results-top']/following-sibling::div//a[contains(@class, 'listing-link')]/div[2]/div/h3
    @FindBy(xpath = "//div[@id='content']//div[@class='wt-bg-white wt-display-block wt-pb-xs-2 wt-mt-xs-0']//h3")
    public List<WebElement> productList;

    @FindBy(xpath ="//span[contains(text(),'All Filters')]")
    public WebElement filtersButton;

    @FindBy(xpath = "//label[@for='price-input-0']")
    public WebElement anyPrice;

    @FindBy(xpath = "//label[@for='price-input-4']")
    public WebElement over1000;

    @FindBy(xpath = "//label[@for='price-input-1']")
    public WebElement under50;

    @FindBy(xpath = "//label[@for='price-input-2']")
    public WebElement from50to500;

    @FindBy(xpath = "//label[@for='price-input-3']")
    public WebElement from500to1000;

    @FindBy(xpath = "//input[@name='min']")
    public WebElement minPrice;

    @FindBy(xpath = "//input[@name='max']")
    public WebElement maxPrice;

    @FindBy(xpath = "//button[@aria-label='Apply']")
    public WebElement applyButton;

    @FindBy(xpath = "//p[@class='wt-text-title-01']/span[@class='currency-value']")
    public List<WebElement> priceList;

}
