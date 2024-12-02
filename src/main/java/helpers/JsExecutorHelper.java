package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
     * @param element the WebElement to scroll to
     */
    public void jsScrollToElement(WebElement element) {
        executeScript("arguments[0].scrollIntoView(true);", element);
        logger.info("Scrolled to element: {}", element);
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
     * @param element the WebElement to highlight
     */
    public void highlightElement(WebElement element) {
        executeScript("arguments[0].style.backgroundColor = 'yellow';", element);
        logger.info("Highlighted element: {}", element);
    }

    /**
     * Performs a hard click on the specified element using JavaScript.
     *
     * @param element the WebElement to click
     */
    public void hardClick(WebElement element) {
        executeScript("arguments[0].click();", element);
        logger.info("Performed hard click on element: {}", element);
    }

    /**
     * Removes the highlight from the specified element by resetting its background color.
     *
     * @param element the WebElement to unhighlight
     */
    public void removeHighlight(WebElement element) {
        executeScript("arguments[0].style.backgroundColor = '';", element);
        logger.info("Removed highlight from element: {}", element);
    }

    /**
     * Sets the value of a specified input element using JavaScript.
     *
     * @param element the input WebElement
     * @param value   the value to set
     */
    public void setInputValue(WebElement element, String value) {
        executeScript("arguments[0].value = arguments[1];", element, value);
        logger.info("Set input value for element: {} to '{}'", element, value);
    }

    /**
     * Retrieves the value of a specified input element using JavaScript.
     *
     * @param element the input WebElement
     * @return the value of the input element
     */
    public String getInputValue(WebElement element) {
        String value = (String) executeScript("return arguments[0].value;", element);
        logger.info("Retrieved input value for element: {} - Value: '{}'", element, value);
        return value;
    }
}