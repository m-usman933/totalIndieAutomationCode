package SignupTests;

import TotalIndieLoginTests.ConfigReader;
import Utilities.EmailVerifier;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Random;
import java.util.logging.Level;

public class ValidSignup {
    WebDriver driver;
    WebDriverWait wait;
    ConfigReader config;

    String baseEmail = "osmansarfraz468";
    String domainName = "@gmail.com";

    String randomString = generateRandomString(6);
    String uniqueEmail = baseEmail + "+" + randomString + domainName;

    String countryCode= "345";
    String randomNumber = generateRandomInteger(7);
    String phoneNumber= countryCode + randomNumber;

    @BeforeMethod
    public void setup() throws IOException {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        config = new ConfigReader();
        System.setProperty("webdriver.chrome.silentOutput", "true");
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        driver= new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://dev.totalindie.com/account/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private static String generateRandomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
    private static String generateRandomInteger(int length)
    {
        Random random = new Random();
        String randomNumber = String.valueOf(1000000 + random.nextInt(9000000));
        return randomNumber;
    }
    @Test
    public void signup() throws Exception {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Sign Up"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formcontrolname='firstName']"))).sendKeys("Muhammad Usman");
        driver.findElement(By.cssSelector("input[formcontrolname='lastName']")).sendKeys("Sarfraz");
        driver.findElement(By.cssSelector("input[formcontrolname='email']")).sendKeys(uniqueEmail);
        driver.findElement(By.cssSelector("input[formcontrolname='secondaryEmail']")).sendKeys(uniqueEmail);
        driver.findElement(By.className("country-selector-code")).click();
        driver.findElement(By.className("country-search")).sendKeys("Pakistan");
        driver.findElement(By.cssSelector("div.flag.PK")).click();

        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys(phoneNumber);
        driver.findElement(By.cssSelector("input[formcontrolname='password']")).sendKeys(config.getPassword());
        driver.findElement(By.xpath("//label[normalize-space()='Promoter']")).click();

        By multiSelectInput = By.cssSelector("input[aria-autocomplete='list']");
        driver.findElement(multiSelectInput).click();
        driver.findElement(By.xpath("//span[normalize-space()='Alternative']")).click();
        driver.findElement(multiSelectInput).click();// re-open if it closes
        driver.findElement(By.xpath("//span[normalize-space()='Ambient']")).click();
        driver.findElement(multiSelectInput).click();  // re-open again
        driver.findElement(By.xpath("//span[normalize-space()='Blues']")).click();

        WebElement signUpBtn = driver.findElement(By.xpath("//button[normalize-space()='Sign Up']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", signUpBtn);
        Thread.sleep(1000);
        signUpBtn.click();

        boolean emailReceived = EmailVerifier.checkEmailReceived("Total Indie Sign Up Verification");
        Assert.assertTrue(emailReceived, "Verification email was not received.");
    }
    @AfterMethod
    public void teerDown()
    {
        if (driver != null)
            driver.close();
    }
}
