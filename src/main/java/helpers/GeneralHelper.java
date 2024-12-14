package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.config.PropertyFileReader;

import java.time.Duration;

public class GeneralHelper {
    private static final Logger logger = LogManager.getLogger(GeneralHelper.class);
    private static final PropertyFileReader config = new PropertyFileReader();
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public GeneralHelper(WebDriver driver) {
        this.driver = driver;
        int waitDuration = config.getDefaultWaitDuration();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
        this.actions = new Actions(driver);
        logger.info("GeneralHelper initialized with driver instance hash: {} and timeout: {} seconds", System.identityHashCode(driver), waitDuration);
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
     * Clicks on the provided element (By locator or WebElement).
     *
     * @param locator the By locator or WebElement to be clicked
     */
    public void click(Object locator) {
        logger.info("Clicking on locator: {}", locator);
        try {
            scrollToElement(locator);
            getElement(locator).click();
            logger.info("Clicked on locator: {}", locator);
        } catch (NoSuchElementException e) {
            logger.error("Failed to click on locator: {}. Element not found. Exception: {}", locator, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Failed to click on locator: {}. Exception: {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Enters text into the provided element (By locator or WebElement).
     *
     * @param locator the By locator or WebElement to fill text into
     * @param text    the text to enter
     */
    public void fillText(Object locator, String text) {
        logger.info("Filling text '{}' in locator: {}", text, locator);
        try {
            scrollToElement(locator);
            WebElement visibleElement = getElement(locator);
            visibleElement.clear();
            visibleElement.sendKeys(text);
            logger.info("Filled text in locator: {}", locator);
        } catch (NoSuchElementException e) {
            logger.error("Failed to fill text in locator: {}. Element not found. Exception: {}", locator, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Failed to fill text in locator: {}. Exception: {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Clears the text from the provided element (By locator or WebElement).
     *
     * @param locator the By locator or WebElement to clear text from
     */
    public void clearText(Object locator) {
        logger.info("Clearing text in locator: {}", locator);
        try {
            scrollToElement(locator);
            getElement(locator).clear();
            logger.info("Cleared text in locator: {}", locator);
        } catch (NoSuchElementException e) {
            logger.error("Failed to clear text in locator: {}. Element not found. Exception: {}", locator, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Failed to clear text in locator: {}. Exception: {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Gets the text from the provided element (By locator or WebElement).
     *
     * @param locator the By locator or WebElement to retrieve text from
     * @return the text of the element
     */
    public String getText(Object locator) {
        logger.info("Getting text from locator: {}", locator);
        try {
            scrollToElement(locator);
            String text = getElement(locator).getText();
            logger.info("Retrieved text: '{}' from locator: {}", text, locator);
            return text;
        } catch (NoSuchElementException e) {
            logger.error("Failed to get text from locator: {}. Element not found. Exception: {}", locator, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Failed to get text from locator: {}. Exception: {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Double-clicks on the provided element (By locator or WebElement).
     *
     * @param locator the By locator or WebElement to double-click
     */
    public void doubleClick(Object locator) {
        logger.info("Double-clicking on locator: {}", locator);
        try {
            scrollToElement(locator);
            actions.doubleClick(getElement(locator)).perform();
            logger.info("Double-clicked on locator: {}", locator);
        } catch (NoSuchElementException e) {
            logger.error("Failed to double-click on locator: {}. Element not found. Exception: {}", locator, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Failed to double-click on locator: {}. Exception: {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Scrolls to the provided element (By locator or WebElement).
     *
     * @param locator the By locator or WebElement to scroll to
     */
    public void scrollToElement(Object locator) {
        logger.info("Scrolling to locator: {}", locator);
        try {
            actions.moveToElement(resolveLocator(locator)).perform();
            logger.info("Scrolled to locator: {}", locator);
        } catch (NoSuchElementException e) {
            logger.error("Failed to scroll to locator: {}. Element not found. Exception: {}", locator, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Failed to scroll to locator: {}. Exception: {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Focuses on the provided element (By locator or WebElement) and clicks it.
     *
     * @param locator the By locator or WebElement to focus on and click
     */
    public void focusOnElement(Object locator) {
        logger.info("Focusing on locator: {}", locator);
        try {
            scrollToElement(locator);
            actions.moveToElement(getElement(locator)).click().perform();
            logger.info("Focused on locator and clicked: {}", locator);
        } catch (NoSuchElementException e) {
            logger.error("Failed to focus on and click locator: {}. Element not found. Exception: {}", locator, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Failed to focus on and click locator: {}. Exception: {}", locator, e.getMessage());
            throw e;
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
            throw e;
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
            throw e;
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
            throw e;
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
            throw e;
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
            logger.error("Failed to retrieve current URL. Exception");
            throw e;
        }
    }
}
