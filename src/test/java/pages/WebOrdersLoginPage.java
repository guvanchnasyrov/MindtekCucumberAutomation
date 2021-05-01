package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class WebOrdersLoginPage {

    public WebOrdersLoginPage(){
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy (xpath = "//*[contains(@id,'username')]")
    public WebElement username;

    @FindBy (xpath = "//*[contains(@id,'password')]")
    public WebElement password;

    @FindBy (xpath = "//*[@type='submit']")
    public WebElement loginButton;

    @FindBy (xpath = "//*[contains(@id,'status')]")
    public WebElement errorMessage;



}
