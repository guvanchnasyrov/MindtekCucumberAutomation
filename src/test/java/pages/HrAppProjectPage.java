package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class HrAppProjectPage {

    public HrAppProjectPage(){
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//table[@id='europe-employees']//tr/td[1]")
    public List<WebElement> listOfFirstName;

    @FindBy(xpath="//table[@id='europe-employees']//tr/td[2]")
    public List<WebElement> listOfLastName;

    @FindBy(xpath="//table[@id=\"number-of-employees-by-department-name\"]//tr/td[1]")
    public List<WebElement> listOfDepartmentName;

    @FindBy(xpath="//table[@id=\"number-of-employees-by-department-name\"]//tr/td[2]")
    public List<WebElement> listOfNumberOfEmployees;

}
