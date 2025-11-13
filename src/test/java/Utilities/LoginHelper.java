package Utilities;

import TotalIndieLoginTests.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;

import java.io.IOException;
import java.time.Duration;

public class LoginHelper {

    public static void loginToTotalIndie(WebDriver driver) throws IOException {
        ConfigReader config = new ConfigReader();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://dev.totalindie.com/account/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[formcontrolname='email']")))
                .sendKeys(config.getEmail());

        driver.findElement(By.cssSelector("input[formcontrolname='password']"))
                .sendKeys(config.getPassword());

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlToBe("https://dev.totalindie.com/discover"));
        System.out.println("✅ Logged in successfully!");
    }
}
