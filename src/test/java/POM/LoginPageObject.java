package POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPageObject {
    private WebDriver driver;
    private By emailFieldLocator;
    private By passwordFieldLocator;
    private By submitButtonLocator;
    private WebDriverWait wait;

    public LoginPageObject(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        emailFieldLocator = By.cssSelector("input[formcontrolname='email']");
        passwordFieldLocator = By.cssSelector("input[formcontrolname='password']");
        submitButtonLocator = By.cssSelector("button[type='submit']");
    }

    public void typeEmail(String emailAddress) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailFieldLocator)).sendKeys(emailAddress);
    }

    public void typePassword(String password) {
        driver.findElement(passwordFieldLocator).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(submitButtonLocator).click();
    }
}
