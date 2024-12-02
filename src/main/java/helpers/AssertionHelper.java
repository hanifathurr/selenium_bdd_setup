package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class AssertionHelper {
    private static final Logger logger = LogManager.getLogger(AssertionHelper.class);
    private final WebDriver driver;

    /**
     * Constructor to initialize the AssertionHelper with WebDriver.
     *
     * @param driver the WebDriver instance
     */
    public AssertionHelper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Asserts that two strings are equal.
     *
     * @param actual   the actual string value
     * @param expected the expected string value
     */
    public static void assertEquals(String actual, String expected) {
        try {
            Assert.assertEquals(actual, expected, "Assertion Failed: String values do not match.");
            logger.info("Assertion Passed: Expected string '{}' matches actual '{}'", expected, actual);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: String values do not match. Expected: '{}', Actual: '{}'", expected, actual, e);
            throw new RuntimeException("Assertion Failed: " + e.getMessage(), e);
        }
    }

    /**
     * Asserts that two integers are equal.
     *
     * @param actual   the actual integer value
     * @param expected the expected integer value
     */
    public static void assertEquals(int actual, int expected) {
        try {
            Assert.assertEquals(actual, expected, "Assertion Failed: Integer values do not match.");
            logger.info("Assertion Passed: Expected integer '{}' matches actual '{}'", expected, actual);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: Integer values do not match. Expected: '{}', Actual: '{}'", expected, actual, e);
            throw new RuntimeException("Assertion Failed: " + e.getMessage(), e);
        }
    }

    /**
     * Asserts that a condition is true.
     *
     * @param condition the condition to evaluate
     */
    public static void assertTrue(boolean condition) {
        try {
            Assert.assertTrue(condition, "Assertion Failed: Condition is not true.");
            logger.info("Assertion Passed: Condition is true.");
        } catch (AssertionError e) {
            logger.error("Assertion Failed: Condition is not true.", e);
            throw new RuntimeException("Assertion Failed: " + e.getMessage(), e);
        }
    }

    /**
     * Asserts that a WebElement is displayed on the page.
     *
     * @param element the WebElement to check
     */
    public void assertElementDisplayed(WebElement element) {
        assertElementCondition(element.isDisplayed(), "Element is not displayed.");
    }

    /**
     * Asserts that a WebElement's text matches the expected value.
     *
     * @param element the WebElement to check
     * @param text    the expected text value
     */
    public void assertElementText(WebElement element, String text) {
        String actualText = getElementText(element);
        try {
            Assert.assertEquals(actualText, text,
                    String.format("Assertion Failed: Text mismatch. Expected: '%s', Found: '%s'", text, actualText));
            logger.info("Assertion Passed: Element text matches expected value: '{}'", text);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: Element text mismatch. Expected: '{}', Found: '{}'", text, actualText, e);
            throw new RuntimeException("Assertion Failed: " + e.getMessage(), e);
        }
    }

    /**
     * Asserts that a WebElement contains specific text.
     *
     * @param element the WebElement to check
     * @param text    the expected text value
     */
    public void assertElementContainsText(WebElement element, String text) {
        String actualText = getElementText(element);
        try {
            Assert.assertTrue(actualText.contains(text),
                    String.format("Assertion Failed: Text does not contain the expected value. Expected: '%s', Found: '%s'", text, actualText));
            logger.info("Assertion Passed: Element text contains expected value: '{}'", text);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: Element text does not contain expected value. Expected: '{}', Found: '{}'", text, actualText, e);
            throw new RuntimeException("Assertion Failed: " + e.getMessage(), e);
        }
    }

    /**
     * Asserts that a WebElement is enabled.
     *
     * @param element the WebElement to check
     */
    public void assertElementEnabled(WebElement element) {
        assertElementCondition(element.isEnabled(), "Element is not enabled.");
    }

    /**
     * Asserts that a WebElement is selected (e.g., checkbox or radio button).
     *
     * @param element the WebElement to check
     */
    public void assertElementSelected(WebElement element) {
        assertElementCondition(element.isSelected(), "Element is not selected.");
    }

    /**
     * Asserts the page title matches the expected title.
     *
     * @param expectedTitle the expected page title
     */
    public void assertPageTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        try {
            Assert.assertEquals(actualTitle, expectedTitle,
                    String.format("Assertion Failed: Page title mismatch. Expected: '%s', Found: '%s'", expectedTitle, actualTitle));
            logger.info("Assertion Passed: Page title matches expected title: '{}'", expectedTitle);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: Page title mismatch. Expected: '{}', Found: '{}'", expectedTitle, actualTitle, e);
            throw new RuntimeException("Assertion Failed: " + e.getMessage(), e);
        }
    }

    /**
     * Asserts that the current URL matches the expected URL.
     *
     * @param expectedUrl the expected URL
     */
    public void assertCurrentUrl(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        try {
            Assert.assertEquals(actualUrl, expectedUrl,
                    String.format("Assertion Failed: URL mismatch. Expected: '%s', Found: '%s'", expectedUrl, actualUrl));
            logger.info("Assertion Passed: Current URL matches expected URL: '{}'", expectedUrl);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: URL mismatch. Expected: '{}', Found: '{}'", expectedUrl, actualUrl, e);
            throw new RuntimeException("Assertion Failed: " + e.getMessage(), e);
        }
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
            logger.error("Assertion Failed: URL does not contain expected substring. Expected: '{}', Found: '{}'", substring, actualUrl, e);
            throw new RuntimeException("Assertion Failed: " + e.getMessage(), e);
        }
    }

    /**
     * Asserts that a WebElement meets a specific condition.
     *
     * @param condition the condition to evaluate
     * @param message   the message to display if the assertion fails
     */
    private void assertElementCondition(boolean condition, String message) {
        try {
            Assert.assertTrue(condition, "Assertion Failed: " + message);
            logger.info("Assertion Passed: {}", message);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: {}", message, e);
            throw new RuntimeException("Assertion Failed: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves the text from a WebElement safely.
     *
     * @param element the WebElement to retrieve text from
     * @return the text of the WebElement
     */
    private String getElementText(WebElement element) {
        try {
            return element.getText().trim();
        } catch (Exception e) {
            logger.error("Unable to retrieve text from the element.", e);
            throw new RuntimeException("Unable to retrieve text from the element. " + e.getMessage(), e);
        }
    }
}