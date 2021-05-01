package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class WebOrdersHomePage {

    public WebOrdersHomePage() {
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[.='Order']")
    public WebElement orderButton;

    @FindBy(xpath = "//*[contains(@id,'Product')]")
    public WebElement product;

    @FindBy(xpath = "//*[contains(@id,'Quantity')]")
    public WebElement quantity;

    @FindBy(xpath = "//*[contains(@id,'UnitPrice')]")
    public WebElement unitPrice;

    @FindBy(xpath = "//*[contains(@id,'Total')]")
    public WebElement totalPrice;

    @FindBy(xpath = "//*[contains(@id,'Discount')]")
    public WebElement discount;

    @FindBy(xpath = "//*[contains(@name,'Name')]")
    public WebElement customerName;

    @FindBy(xpath = "//*[contains(@name,'TextBox2')]")
    public WebElement street;

    @FindBy(xpath = "//*[contains(@name,'TextBox3')]")
    public WebElement city;

    @FindBy(xpath = "//*[contains(@name,'TextBox4')]")
    public WebElement state;

    @FindBy(xpath = "//*[contains(@name,'TextBox5')]")
    public WebElement zipcode;

    @FindBy(xpath = "//*[contains(@value,'Visa')]")
    public WebElement visa;

    @FindBy(xpath = "//*[contains(@value,'MasterCard')]")
    public WebElement masterCard;

    @FindBy(xpath = "//*[contains(@value,\"American Express\")]")
    public WebElement americanExpress;

    @FindBy(xpath = "//*[contains(@name,'TextBox6')]")
    public WebElement cardNumber;

    @FindBy(xpath = "//*[contains(@name,'TextBox1')]")
    public WebElement expireDate;

    @FindBy(xpath = "//*[contains(@id,'InsertButton')]")
    public WebElement processButton;

    @FindBy(xpath = "//*[contains(@value,'Reset')]")
    public WebElement resetButton;

    @FindBy(tagName = "strong")
    public WebElement successMessage;

    @FindBy(xpath = "//*[contains(text(),'View all orders')]")
    public WebElement viewAllOrdersButton;

    @FindBy(xpath = "//*[contains(@id,'Delete')]")
    public WebElement deleteSelectedButton;

    @FindBy(xpath = "//*[@type='checkbox']")
    public List<WebElement> checkbox;

    @FindBy(xpath = "//*[@class='SampleTable']//tr/td[3]")
    public List<WebElement> orderedProducts;

    @FindBy(xpath = "//*[@alt='Edit']")
    public List<WebElement> editButtons;

    @FindBy(xpath = "//*[@class='SampleTable']//tr/td[2]")
    public List<WebElement> customerNames;

    @FindBy(xpath = "//*[@selected='selected']")
    public WebElement selectedProduct;
}
