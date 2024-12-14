package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {
    private static final Logger logger = LogManager.getLogger(WaitHelper.class);
    private final WebDriverWait wait;
    private final WebDriver driver;

    /**
     * Constructs a WaitHelper with a specified timeout.
     *
     * @param driver          the WebDriver instance
     * @param timeoutInSeconds the maximum time to wait for conditions (in seconds)
     */
    public WaitHelper(WebDriver driver, long timeoutInSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Resolves a locator (By or WebElement) to a visible WebElement.
     *
     * @param locator the locator to resolve
     * @return the visible WebElement
     */
    private WebElement resolveLocator(Object locator) {
        if (locator instanceof By) {
            logger.debug("Resolving locator as By: {}", locator);
            return driver.findElement((By) locator);
        } else if (locator instanceof WebElement) {
            logger.debug("Locator is already a WebElement: {}", locator);
            return (WebElement) locator;
        } else {
            throw new IllegalArgumentException("Locator must be of type By or WebElement.");
        }
    }

    /**
     * Waits for the visibility of a specified WebElement.
     *
     * @param locator the WebElement or By locator to wait for
     * @return the visible WebElement
     */
    public WebElement waitForVisibilityOfElement(Object locator) {
        WebElement element = resolveLocator(locator);
        WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
        logger.info("Element is visible: {}", element);
        return visibleElement;
    }

    /**
     * Waits for a specified WebElement to be clickable.
     *
     * @param locator the WebElement or By locator to wait for
     */
    public void waitForElementToBeClickable(Object locator) {
        WebElement element = resolveLocator(locator);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        logger.info("Element is clickable: {}", element);
    }

    /**
     * Waits for a specified text to be present in a WebElement.
     *
     * @param locator     the WebElement or By locator to check
     * @param elementText the text to wait for
     */
    public void waitForTextToBePresentInElement(Object locator, String elementText) {
        WebElement element = resolveLocator(locator);
        wait.until(ExpectedConditions.textToBePresentInElement(element, elementText));
        logger.info("Text '{}' is present in element: {}", elementText, element);
    }

    /**
     * Waits for the title of the page to contain a specified string.
     *
     * @param titleText the text to check for in the page title
     */
    public void waitForTitleToContain(String titleText) {
        wait.until(ExpectedConditions.titleContains(titleText));
        logger.info("Title contains text: '{}'", titleText);
    }

    /**
     * Waits for a specified WebElement to become invisible.
     *
     * @param locator the WebElement or By locator to wait for
     */
    public void waitForInvisibilityOfElement(Object locator) {
        WebElement element = resolveLocator(locator);
        wait.until(ExpectedConditions.invisibilityOf(element));
        logger.info("Element is invisible: {}", element);
    }

    /**
     * Waits for a specified attribute of a WebElement to have a specific value.
     *
     * @param locator   the WebElement or By locator to check
     * @param attribute the name of the attribute to check
     * @param value     the expected value of the attribute
     */
    public void waitForAttributeToBe(Object locator, String attribute, String value) {
        WebElement element = resolveLocator(locator);
        wait.until(driver -> element.getAttribute(attribute).equals(value));
        logger.info("Element '{}' has attribute '{}' with value '{}'", element, attribute, value);
    }

    /**
     * Waits for a specified WebElement to be selected.
     *
     * @param locator the WebElement or By locator to check
     */
    public void waitForElementToBeSelected(Object locator) {
        WebElement element = resolveLocator(locator);
        wait.until(ExpectedConditions.elementToBeSelected(element));
        logger.info("Element is selected: {}", element);
    }
}