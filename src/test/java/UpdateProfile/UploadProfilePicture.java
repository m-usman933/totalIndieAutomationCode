package UpdateProfile;

import Utilities.LoginHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class UploadProfilePicture {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup()
    {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Test
    public void testProfilePicUpload() throws IOException, InterruptedException {
        LoginHelper.loginToTotalIndie(driver);
        driver.findElement(By.id("page-header-user-dropdown")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@href, '/profile/me/releases')]"))).click();
        driver.navigate().to("https://dev.totalindie.com/profile/me/releases?name=Muhammad-Usman");
        Thread.sleep(20000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//span[text()='Add/Upload']]")));

        System.out.println("Going to upload image");
        WebElement uploadInput = driver.findElement(By.id("actual-btn"));
        uploadInput.sendKeys("C:\\Users\\Dell\\Pictures\\image.png");
        System.out.println("Image uploaded");
    
    }
    @AfterMethod
    public void wrapUp()
    {
        if(driver != null)
            driver.quit();
    }
}
