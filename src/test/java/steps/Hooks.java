package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utilities.Driver;

import java.util.concurrent.TimeUnit;

public class Hooks {

    @Before
    public void setUp(Scenario scenario){
        if(scenario.getSourceTagNames().contains("@ui")) {
            WebDriver driver = Driver.getDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    @After
    public void tearDown(Scenario scenario) throws InterruptedException {
        if(scenario.getSourceTagNames().contains("@ui")) {
            WebDriver driver = Driver.getDriver();
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            }
            Thread.sleep(5000);
            driver.quit();
        }
    }

}
