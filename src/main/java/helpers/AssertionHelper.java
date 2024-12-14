package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.config.PropertyFileReader;

import java.time.Duration;

public class AssertionHelper {
    private static final Logger logger = LogManager.getLogger(AssertionHelper.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final PropertyFileReader config = new PropertyFileReader();

    /**
     * Constructor to initialize the AssertionHelper with WebDriver.
     *
     * @param driver the WebDriver instance
     */
    public AssertionHelper(WebDriver driver) {
        this.driver = driver;
        int waitDuration = config.getDefaultWaitDuration();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));

    }

    /**
     * Asserts that two strings are equal with an optional message.
     *
     * @param actual   the actual string value
     * @param expected the expected string value
     * @param message  optional custom message for logging
     */
    public static void assertEquals(String actual, String expected, String... message) {
        String logMessage = message.length > 0 ? message[0] : "String values do not match.";
        try {
            Assert.assertEquals(actual, expected, "Assertion Failed: " + logMessage);
            logger.info("Assertion Passed: {} - Expected: '{}', Actual: '{}'", logMessage, expected, actual);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: {} - Expected: '{}', Actual: '{}'", logMessage, expected, actual, e);
            throw e;
        }
    }

    /**
     * Asserts that two integers are equal with an optional message.
     *
     * @param actual   the actual integer value
     * @param expected the expected integer value
     * @param message  optional custom message for logging
     */
    public static void assertEquals(int actual, int expected, String... message) {
        String logMessage = message.length > 0 ? message[0] : "Integer values do not match.";
        try {
            Assert.assertEquals(actual, expected, "Assertion Failed: " + logMessage);
            logger.info("Assertion Passed: {} - Expected: '{}', Actual: '{}'", logMessage, expected, actual);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: {} - Expected: '{}', Actual: '{}'", logMessage, expected, actual, e);
            throw e;
        }
    }

    /**
     * Asserts that a condition is true with an optional message.
     *
     * @param condition the condition to evaluate
     * @param message   optional custom message for logging
     */
    public static void assertTrue(boolean condition, String... message) {
        String logMessage = message.length > 0 ? message[0] : "Condition is not true.";
        try {
            Assert.assertTrue(condition, "Assertion Failed: " + logMessage);
            logger.info("Assertion Passed: {} - Condition evaluated to true.", logMessage);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: {} - Condition evaluated to false.", logMessage, e);
            throw e;
        }
    }

    /**
     * Asserts that a WebElement is displayed on the page.
     *
     * @param locator the By locator or WebElement to check
     */
    public void assertElementDisplayed(Object locator) {
        WebElement element = getElement(locator);
        assertElementCondition(element.isDisplayed(), "Element is displayed: " + element);
    }

    /**
     * Asserts that a WebElement's text matches the expected value.
     *
     * @param locator the By locator or WebElement to check
     * @param text    the expected text value
     */
    public void assertElementText(Object locator, String text) {
        WebElement element = getElement(locator);
        String actualText = getElementText(element);
        assertEquals(actualText, text, "Element text assertion");
    }

    /**
     * Asserts that a WebElement contains specific text.
     *
     * @param locator the By locator or WebElement to check
     * @param text    the expected text value
     */
    public void assertElementContainsText(Object locator, String text) {
        WebElement element = getElement(locator);
        String actualText = getElementText(element);
        try {
            Assert.assertTrue(actualText.contains(text),
                    String.format("Assertion Failed: Text does not contain the expected value. Expected: '%s', Found: '%s'", text, actualText));
            logger.info("Assertion Passed: Element text contains expected value: '{}'", text);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: Element text does not contain expected value. Expected: '{}', Found: '{}'.", text, actualText, e);
            throw e;
        }
    }

    /**
     * Asserts that a WebElement is enabled.
     *
     * @param locator the By locator or WebElement to check
     */
    public void assertElementEnabled(Object locator) {
        WebElement element = getElement(locator);
        assertElementCondition(element.isEnabled(), "Element is enabled: " + element);
    }

    /**
     * Asserts that a WebElement is selected (e.g., checkbox or radio button).
     *
     * @param locator the By locator or WebElement to check
     */
    public void assertElementSelected(Object locator) {
        WebElement element = getElement(locator);
        assertElementCondition(element.isSelected(), "Element is selected: " + element);
    }

    /**
     * Asserts the page title matches the expected title.
     *
     * @param expectedTitle the expected page title
     */
    public void assertPageTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle, "Page title assertion");
    }

    /**
     * Asserts that the current URL matches the expected URL.
     *
     * @param expectedUrl the expected URL
     */
    public void assertCurrentUrl(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl, expectedUrl, "Current URL assertion");
    }

    /**
     * Asserts that the current URL contains the expected substring.
     *
     * @param substring the expected substring in the URL
     */
    public void assertUrlContains(String substring) {
        String actualUrl = driver.getCurrentUrl();
        try {
            Assert.assertTrue(actualUrl.contains(substring),
                    String.format("Assertion Failed: URL does not contain expected substring. Expected: '%s', Found: '%s'", substring, actualUrl));
            logger.info("Assertion Passed: Current URL contains expected substring: '{}'", substring);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: URL does not contain expected substring. Expected: '{}', Found: '{}'.", substring, actualUrl, e);
            throw e;
        }
    }

    /**
     * Asserts that a WebElement meets a specific condition.
     *
     * @param condition the condition to evaluate
     * @param message   the message to display if the assertion fails
     */
    public void assertElementCondition(boolean condition, String message) {
        try {
            Assert.assertTrue(condition, "Assertion Failed: " + message);
            logger.info("Assertion Passed: {}", message);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: {}", message, e);
            throw e;
        }
    }

    /**
     * Waits for the element (located by a By locator or WebElement) to be visible and returns it.
     *
     * @param locator the By locator or WebElement to be waited for
     * @return the visible WebElement
     */
    public WebElement getElement(Object locator) {
        logger.debug("Waiting for visibility of locator: {}", locator);
        try {
            WebElement element = resolveLocator(locator);
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            logger.debug("Element is now visible: {}", visibleElement);
            return visibleElement;
        } catch (NoSuchElementException e) {
            logger.error("Element not found: {}. Exception: {}", locator, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while waiting for visibility of locator: {}. Exception: {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Resolves the provided locator into a WebElement.
     *
     * @param locator the By locator or WebElement
     * @return the resolved WebElement
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
     * Retrieves the text from a WebElement safely.
     *
     * @param locator the WebElement to retrieve text from
     * @return the text of the WebElement
     */
    private String getElementText(Object locator) {
        try {
            WebElement element = getElement(locator);
            String text = element.getText().trim();
            logger.info("Successfully retrieved text from element: '{}'", text);
            return text;
        } catch (Exception e) {
            logger.error("Unable to retrieve text from the element.", e);
            throw new RuntimeException("Unable to retrieve text from the element. " + e.getMessage(), e);
        }
    }
}
