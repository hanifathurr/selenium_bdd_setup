package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.config.PropertyFileReader;

import java.time.Duration;

public class GeneralHelper {
    private static final Logger logger = LogManager.getLogger(GeneralHelper.class);  // Initialize the logger
    private static final PropertyFileReader config = new PropertyFileReader();
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public GeneralHelper(WebDriver driver) {
        this.driver = driver;
        int waitDuration = config.getDefaultWaitDuration();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
        this.actions = new Actions(driver);
        logger.info("GeneralHelper initialized with driver: {} and timeout: {} seconds", driver, waitDuration);  // Log the initialization
    }

    /**
     * Waits for the element to be visible and returns it.
     *
     * @param element the WebElement to be waited for
     * @return the visible WebElement
     */
    public WebElement getElement(WebElement element) {
        logger.debug("Waiting for visibility of element: {}", element);
        try {
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            logger.debug("Element is now visible: {}", visibleElement);
            return visibleElement;
        } catch (NoSuchElementException e) {
            logger.error("Element not found: {}. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the specific exception
        } catch (Exception e) {
            logger.error("Error while waiting for visibility of element: {}. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the exception to allow higher-level handling
        }
    }

    /**
     * Clicks on the provided element.
     *
     * @param element the WebElement to be clicked
     */
    public void click(WebElement element) {
        logger.info("Clicking on element: {}", element);
        try {
            getElement(element).click();
            logger.info("Clicked on element: {}", element);
        } catch (NoSuchElementException e) {
            logger.error("Failed to click on element: {}. Element not found. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the specific exception for test-level handling
        } catch (Exception e) {
            logger.error("Failed to click on element: {}. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }

    /**
     * Enters text into the provided element.
     *
     * @param element the WebElement to fill text into
     * @param text    the text to enter
     */
    public void fillText(WebElement element, String text) {
        logger.info("Filling text '{}' in element: {}", text, element);
        try {
            WebElement visibleElement = getElement(element);
            visibleElement.clear();
            visibleElement.sendKeys(text);
            logger.info("Filled text in element: {}", element);
        } catch (NoSuchElementException e) {
            logger.error("Failed to fill text in element: {}. Element not found. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the specific exception for test-level handling
        } catch (Exception e) {
            logger.error("Failed to fill text in element: {}. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }

    /**
     * Clears the text from the provided element.
     *
     * @param element the WebElement to clear text from
     */
    public void clearText(WebElement element) {
        logger.info("Clearing text in element: {}", element);
        try {
            getElement(element).clear();
            logger.info("Cleared text in element: {}", element);
        } catch (NoSuchElementException e) {
            logger.error("Failed to clear text in element: {}. Element not found. Exception: {}", element, e.getMessage());
            throw e;  // Re-throw ing the specific exception for test-level handling
        } catch (Exception e) {
            logger.error("Failed to clear text in element: {}. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }

    /**
     * Gets the text from the provided element.
     *
     * @param element the WebElement to retrieve text from
     * @return the text of the element
     */
    public String getText(WebElement element) {
        logger.info("Getting text from element: {}", element);
        try {
            String text = getElement(element).getText();
            logger.info("Retrieved text: '{}' from element: {}", text, element);
            return text;
        } catch (NoSuchElementException e) {
            logger.error("Failed to get text from element: {}. Element not found. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the specific exception for test-level handling
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }

    /**
     * Double clicks on the provided element.
     *
     * @param element the WebElement to double-click
     */
    public void doubleClick(WebElement element) {
        logger.info("Double-clicking on element: {}", element);
        try {
            actions.doubleClick(getElement(element)).perform();
            logger.info("Double-clicked on element: {}", element);
        } catch (NoSuchElementException e) {
            logger.error("Failed to double-click on element: {}. Element not found. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the specific exception for test-level handling
        } catch (Exception e) {
            logger.error("Failed to double-click on element: {}. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }

    /**
     * Scrolls to the provided element.
     *
     * @param element the WebElement to scroll to
     */
    public void scrollToElement(WebElement element) {
        logger.info("Scrolling to element: {}", element);
        try {
            actions.moveToElement(getElement(element)).perform();
            logger.info("Scrolled to element: {}", element);
        } catch (NoSuchElementException e) {
            logger.error("Failed to scroll to element: {}. Element not found. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the specific exception for test-level handling
        } catch (Exception e) {
            logger.error("Failed to scroll to element: {}. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }

    /**
     * Focuses on the provided element and clicks it.
     *
     * @param element the WebElement to focus on and click
     */
    public void focusOnElement(WebElement element) {
        logger.info("Focusing on element: {}", element);
        try {
            actions.moveToElement(getElement(element)).click().perform();
            logger.info("Focused on element and clicked: {}", element);
        } catch (NoSuchElementException e) {
            logger.error("Failed to focus on and click element: {}. Element not found. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the specific exception for test-level handling
        } catch (Exception e) {
            logger.error("Failed to focus on and click element: {}. Exception: {}", element, e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }

    /**
     * Navigates to the provided URL.
     *
     * @param url the URL to navigate to
     */
    public void navigateToUrl(String url) {
        logger.info("Navigating to URL: {}", url);
        try {
            driver.navigate().to(url);
            logger.info("Navigated to URL: {}", url);
        } catch (Exception e) {
            logger.error("Failed to navigate to URL: {}. Exception: {}", url, e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }

    /**
     * Navigates back in the browser.
     */
    public void backBrowser() {
        logger.info("Navigating back in the browser.");
        try {
            driver.navigate().back();
            logger.info("Navigated back in the browser.");
        } catch (Exception e) {
            logger.error("Failed to navigate back in the browser. Exception: {}", e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }

    /**
     * Navigates forward in the browser.
     */
    public void forwardBrowser() {
        logger.info("Navigating forward in the browser.");
        try {
            driver.navigate().forward();
            logger.info("Navigated forward in the browser.");
        } catch (Exception e) {
            logger.error("Failed to navigate forward in the browser. Exception: {}", e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }

    /**
     * Refreshes the current page in the browser.
     */
    public void refreshPage() {
        logger.info("Refreshing the page.");
        try {
            driver.navigate().refresh();
            logger.info("Page refreshed.");
        } catch (Exception e) {
            logger.error("Failed to refresh the page. Exception: {}", e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }

    /**
     * Retrieves the current URL of the browser.
     *
     * @return the current URL
     */
    public String getCurrentUrl() {
        try {
            String currentUrl = driver.getCurrentUrl();
            logger.info("Current URL: {}", currentUrl);
            return currentUrl;
        } catch (Exception e) {
            logger.error("Failed to retrieve current URL. Exception: {}", e.getMessage());
            throw e;  // Re-throwing the exception for test-level handling
        }
    }
}