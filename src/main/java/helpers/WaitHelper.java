package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {
    private static final Logger logger = LogManager.getLogger(WaitHelper.class);
    private final WebDriverWait wait;

    /**
     * Constructs a WaitHelper with a specified timeout.
     *
     * @param driver          the WebDriver instance
     * @param timeoutInSeconds the maximum time to wait for conditions (in seconds)
     */
    public WaitHelper(WebDriver driver, long timeoutInSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Waits for the visibility of a specified WebElement.
     *
     * @param element the WebElement to wait for
     * @return the visible WebElement
     */
    public WebElement waitForVisibilityOfElement(WebElement element) {
        WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
        logger.info("Element is visible: {}", visibleElement);
        return visibleElement;
    }

    /**
     * Waits for a specified WebElement to be clickable.
     *
     * @param element the WebElement to wait for
     */
    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        logger.info("Element is clickable: {}", element);
    }

    /**
     * Waits for a specified text to be present in a WebElement.
     *
     * @param element     the WebElement to check
     * @param elementText the text to wait for
     */
    public void waitForTextToBePresentInElement(WebElement element, String elementText) {
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
     * @param element the WebElement to wait for
     */
    public void waitForInvisibilityOfElement(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
        logger.info("Element is invisible: {}", element);
    }

    /**
     * Waits for a specified attribute of a WebElement to have a specific value.
     *
     * @param element   the WebElement to check
     * @param attribute the name of the attribute to check
     * @param value     the expected value of the attribute
     */
    public void waitForAttributeToBe(WebElement element, String attribute, String value) {
        wait.until(driver -> element.getAttribute(attribute).equals(value));
        logger.info("Element '{}' has attribute '{}' with value '{}'", element, attribute, value);
    }

    /**
     * Waits for a specified WebElement to be selected.
     *
     * @param element the WebElement to check
     */
    public void waitForElementToBeSelected(WebElement element) {
        wait.until(ExpectedConditions.elementToBeSelected(element));
        logger.info("Element is selected: {}", element);
    }

}