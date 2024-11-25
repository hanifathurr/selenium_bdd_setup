package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GeneralHelper {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public GeneralHelper(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }
    public WebElement getElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public void click(WebElement element) {
        getElement(element).click();
    }
    public void fillText(WebElement element, String text) {
        getElement(element).clear();
        getElement(element).sendKeys(text);
    }
    public void clearText(WebElement element){
        getElement(element).clear();
    }
    public String getText(WebElement element){
        return getElement(element).getText();
    }
    public void doubleClick(WebElement element){
        actions.doubleClick(getElement(element)).perform();
    }
    public void scrollToElement(WebElement element){
        actions.scrollToElement(getElement(element)).perform();
    }
    public void focusOnElement(WebElement element) {
        actions.moveToElement(getElement(element)).click().perform();
    }
    public void navigateToUrl(String url) {
        driver.navigate().to(url);
    }
    public void backBrowser() {
        driver.navigate().back();
    }
    public void forwardBrowser() {
        driver.navigate().forward();
    }
    public void refreshPage() {
        driver.navigate().refresh();
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
