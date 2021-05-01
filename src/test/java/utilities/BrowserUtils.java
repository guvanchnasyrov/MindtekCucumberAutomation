package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class BrowserUtils {

    /**
     * This method will accept WebElement of dropdown
     * and String value of dropdown. And then it will select
     * provided value in dropdown
     * Ex:
     * .selectByValue(element,"1");
     */

    public static void selectDropdownByValue(WebElement element, String value) {
        Select day = new Select(element);
        day.selectByValue(value);
    }

    /**
     * This method will accept WebElement of dropdown
     * and String value of dropdown. And then it will select
     * provided visible text in dropdown
     * Ex:
     * .selectByValue(element,"1");
     */

    public static void selectDropdownByVisibleText(WebElement element, String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    /**
     * This method will wait for element to be clickable.
     * Ex:
     * .waitElementToBeClickable(element, 10); -> return element;
     */

    public static WebElement waitElementToBeClickable(WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), seconds);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * This method will wait for element to be visible.
     * Ex:
     * .waitElementToBeVisible(element, 10); -> return element;
     */

    public static WebElement waitElementToBeVisible(WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), seconds);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * This method will help to switch to another window.
     */

    public static void switchWindow() {
        WebDriver driver = Driver.getDriver();
        String currentWindowID = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!currentWindowID.equalsIgnoreCase(window)) {
                driver.switchTo().window(window);
            }
        }
    }

    /**
     * This method will scroll down and up
     * Ex:
     * .scroll(250);  -> scrolling down
     * .scroll(-250); -> scrolling up
     */

    public static void scroll(int pixels) {
        WebDriver driver = Driver.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) (driver);
        js.executeScript("window.scrollBy(0," + pixels + ")");
    }

    /**
     * This method will take screenshot
     * Ex:
     * .takeScreenshot("SauceDemo app test");
     */

    public static void takeScreenshot(String testName) throws IOException {
        WebDriver driver = Driver.getDriver();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "src/test/resources/screenshots/" + testName + ".png";
        File file = new File(path);
        FileUtils.copyFile(screenshot,file);
    }

}
