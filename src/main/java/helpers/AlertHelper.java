package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.config.PropertyFileReader;

import java.time.Duration;

public class AlertHelper {
    private static final Logger logger = LogManager.getLogger(AlertHelper.class);  // Initialize the logger

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final PropertyFileReader config = new PropertyFileReader();

    /**
     * Constructor to initialize WebDriver and WebDriverWait.
     *
     * @param driver the WebDriver instance
     */
    public AlertHelper(WebDriver driver) {
        this.driver = driver;
        int waitDuration = config.getDefaultWaitDuration();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
        logger.info("AlertHelper initialized with driver: {}", driver);
    }

    /**
     * Waits for an alert to be present and returns it.
     *
     * @return the Alert instance
     */
    public Alert waitForAlert() {
        try {
            logger.debug("Waiting for alert to be present...");
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            logger.debug("Alert is present: {}", alert);
            return alert;
        } catch (TimeoutException e) {
            logger.error("No alert was present within the specified timeout", e);
            throw new RuntimeException("No alert was present within the specified timeout", e);
        }
    }

    /**
     * Checks if an alert is present.
     *
     * @return true if an alert is present, false otherwise
     */
    public boolean isAlertPresent() {
        try {
            logger.debug("Checking if alert is present...");
            wait.until(ExpectedConditions.alertIsPresent());
            logger.debug("Alert is present.");
            return true;
        } catch (TimeoutException e) {
            logger.debug("No alert was present.");
            return false;
        }
    }

    /**
     * Gets the text displayed on the alert.
     *
     * @return the alert text
     */
    public String getAlertText() {
        String alertText = waitForAlert().getText();
        logger.info("Retrieved alert text: '{}'", alertText);
        return alertText;
    }

    /**
     * Accepts (confirms) the alert.
     */
    public void acceptAlert() {
        logger.info("Accepting alert.");
        waitForAlert().accept();
        logger.info("Alert accepted.");
    }

    /**
     * Dismisses (cancels) the alert.
     */
    public void dismissAlert() {
        logger.info("Dismissing alert.");
        waitForAlert().dismiss();
        logger.info("Alert dismissed.");
    }

    /**
     * Sends text to a prompt alert and accepts it.
     *
     * @param keysToSend the text to input into the alert
     */
    public void sendTextToAlert(String keysToSend) {
        logger.info("Sending text '{}' to alert.", keysToSend);
        Alert alert = waitForAlert();
        alert.sendKeys(keysToSend);
        alert.accept();
        logger.info("Text sent and alert accepted.");
    }

    /**
     * Sends text to a prompt alert and optionally accepts or dismisses it.
     *
     * @param keysToSend the text to input into the alert
     * @param accept     true to accept the alert, false to dismiss
     */
    public void sendTextAndHandleAlert(String keysToSend, boolean accept) {
        logger.info("Sending text '{}' to alert and will {}", keysToSend, accept ? "accept" : "dismiss");
        Alert alert = waitForAlert();
        alert.sendKeys(keysToSend);
        if (accept) {
            alert.accept();
            logger.info("Alert accepted.");
        } else {
            alert.dismiss();
            logger.info("Alert dismissed.");
        }
    }

    /**
     * Switches to the currently active alert.
     *
     * @return the Alert instance
     */
    public Alert switchToAlert() {
        logger.debug("Switching to active alert.");
        Alert alert = driver.switchTo().alert();
        logger.debug("Switched to alert: {}", alert);
        return alert;
    }

    /**
     * Accepts the alert if present.
     *
     * @return true if the alert was accepted, false if no alert was present
     */
    public boolean acceptIfAlertPresent() {
        if (isAlertPresent()) {
            acceptAlert();
            return true;
        }
        return false;
    }
}