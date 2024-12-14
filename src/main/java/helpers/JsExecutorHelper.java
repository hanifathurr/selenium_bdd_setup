package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsExecutorHelper {
    private static final Logger logger = LogManager.getLogger(JsExecutorHelper.class);
    private final WebDriver driver;

    public JsExecutorHelper(WebDriver driver) {
        this.driver = driver;
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
     * Executes a JavaScript script in the context of the currently selected frame or window.
     *
     * @param script the JavaScript code to execute
     * @param args   optional arguments to pass to the script
     * @return the result of the JavaScript execution
     */
    public Object executeScript(String script, Object... args) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Object result = executor.executeScript(script, args);
        logger.info("Executed script: '{}', with arguments: {}", script, args);
        return result;
    }

    /**
     * Scrolls the page to bring the specified element into view.
     *
     * @param locator the WebElement or By locator to scroll to
     */
    public void jsScrollToElement(Object locator) {
        WebElement element = resolveLocator(locator);
        executeScript("arguments[0].scrollIntoView(true);", element);
        logger.info("Scrolled to element: {}", locator);
    }

    /**
     * Scrolls the page to the top.
     */
    public void scrollToTop() {
        executeScript("window.scrollTo(0, 0);");
        logger.info("Scrolled to the top of the page.");
    }

    /**
     * Scrolls the page to the bottom.
     */
    public void scrollToBottom() {
        executeScript("window.scrollTo(0, document.body.scrollHeight);");
        logger.info("Scrolled to the bottom of the page.");
    }

    /**
     * Highlights the specified element by changing its background color.
     *
     * @param locator the WebElement or By locator to highlight
     */
    public void highlightElement(Object locator) {
        WebElement element = resolveLocator(locator);
        executeScript("arguments[0].style.backgroundColor = 'yellow';", element);
        logger.info("Highlighted element: {}", locator);
    }

    /**
     * Performs a hard click on the specified element using JavaScript.
     *
     * @param locator the WebElement or By locator to click
     */
    public void hardClick(Object locator) {
        WebElement element = resolveLocator(locator);
        executeScript("arguments[0].click();", element);
        logger.info("Performed hard click on element: {}", locator);
    }

    /**
     * Removes the highlight from the specified element by resetting its background color.
     *
     * @param locator the WebElement or By locator to unhighlight
     */
    public void removeHighlight(Object locator) {
        WebElement element = resolveLocator(locator);
        executeScript("arguments[0].style.backgroundColor = '';", element);
        logger.info("Removed highlight from element: {}", locator);
    }

    /**
     * Sets the value of a specified input element using JavaScript.
     *
     * @param locator the WebElement or By locator of the input element
     * @param value   the value to set
     */
    public void setInputValue(Object locator, String value) {
        WebElement element = resolveLocator(locator);
        executeScript("arguments[0].value = arguments[1];", element, value);
        logger.info("Set input value for element: {} to '{}'", locator, value);
    }

    /**
     * Retrieves the value of a specified input element using JavaScript.
     *
     * @param locator the WebElement or By locator of the input element
     * @return the value of the input element
     */
    public String getInputValue(Object locator) {
        WebElement element = resolveLocator(locator);
        String value = (String) executeScript("return arguments[0].value;", element);
        logger.info("Retrieved input value for element: {} - Value: '{}'", locator, value);
        return value;
    }
}
