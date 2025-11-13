package POM;

import TotalIndieLoginTests.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {
    WebDriver driver;
    ConfigReader config;
    @BeforeMethod
    public void setup() throws IOException {
        config = new ConfigReader();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://dev.totalindie.com/account/login");
    }

    @Test
    public void testLogin() throws InterruptedException {
        LoginPageObject loginObject = new LoginPageObject(driver);
        loginObject.typeEmail(config.getEmail());
        loginObject.typePassword(config.getPassword());
        loginObject.clickLoginButton();
        Thread.sleep(10000);

        String expectedUrl = "https://dev.totalindie.com/discover";
        Assert.assertEquals(driver.getCurrentUrl(),expectedUrl,"URLs did not Match");
    }

    @AfterMethod
    public void tearDown()
    {
        if(driver != null)
            driver.close();
    }
}
