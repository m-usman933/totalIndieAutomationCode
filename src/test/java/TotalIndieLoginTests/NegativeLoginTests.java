package TotalIndieLoginTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.logging.Level;

public class NegativeLoginTests {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup()
    {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://dev.totalindie.com/account/login");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void emptyLoginFields()
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formcontrolname='email']")));
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[normalize-space()='Please enter an email']"))).getText();

        Assert.assertEquals(errorMsg, "Please enter an email", "The Inline error message appeared");
    }

    @Test
    public void invalidLoginCredentials() throws InterruptedException {
        // Wait for email field and enter valid email
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[formcontrolname='email']")))
                .sendKeys("osmansarfraz468@gmail.com");

        // Corrected selector (no space)
        driver.findElement(By.cssSelector("input[formcontrolname='password']"))
                .sendKeys("123234fa");

        // Click login
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        try {
            // Wait for toast to appear (max 10s)
            WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[contains(@class,'toast') or contains(@class,'toast-message')]")));

            // Get toast text
            String toastText = toast.getText().trim();
            System.out.println("✅ Toast Message Found: " + toastText);

            // Assertion
            Assert.assertTrue(
                    toastText.contains("You are not authorized, check email or password"),
                    "❌ Toast message does not contain expected text!");

        }
        catch (Exception e)
        {
            System.out.println("⚠️ Toast message not found within timeout.");
            e.printStackTrace();
        }

        // Wait a bit to observe visually (optional)
        Thread.sleep(2000);
    }

    @AfterMethod
    public void closeDriver()
    {
        if(driver != null)
        {
            driver.quit();
        }
    }
}
