package DemoQAAccountSetup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.logging.Level;

import static org.testng.Assert.assertEquals;

public class totalIndieLoginTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        // suppress Selenium logs (do this once at startup)
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://dev.totalindie.com/account/login");

        // setup explicit wait (10 sec) for reuse
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testTotalIndieTitle() {
        String title = driver.getTitle();
        assertEquals(title, "Total-Indie", "Page title mismatch!");
    }

    @Test
    public void TestValidLogin() {
        // wait for email field and enter email
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formcontrolname='email']")))
                .sendKeys("osmansarfraz468@gmail.com");

        // wait for password field and enter password
        driver.findElement(By.cssSelector("input[formcontrolname='password']"))
                .sendKeys("12345678");

        // wait for login button and click
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // wait for URL to be discover page
        wait.until(ExpectedConditions.urlToBe("https://dev.totalindie.com/discover"));

        // assertion
        Assert.assertEquals(driver.getCurrentUrl(), "https://dev.totalindie.com/discover", "Login failed!");
    }

    @AfterMethod
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
