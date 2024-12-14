package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.config.PropertyFileReader;

import java.time.Duration;
import java.util.List;

public class CheckBoxHelper {
    private static final Logger logger = LogManager.getLogger(CheckBoxHelper.class);
    private static final PropertyFileReader config = new PropertyFileReader();
    private final WebDriver driver;
    private final WebDriverWait wait;

    public CheckBoxHelper(WebDriver driver) {
        this.driver = driver;
        int waitDuration = config.getDefaultWaitDuration();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
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
     * Selects a checkbox if it's not already selected.
     *
     * @param locator the checkbox element to select, either By locator or WebElement
     */
    public void selectCheckBox(Object locator) {
        WebElement checkboxElement = resolveLocator(locator);
        waitUntilClickable(checkboxElement);
        try {
            if (!checkboxElement.isSelected()) {
                checkboxElement.click();
                logger.info("Checkbox selected: {}", locator);
            } else {
                logger.info("Checkbox already selected: {}", locator);
            }
        } catch (StaleElementReferenceException e) {
            logger.error("Checkbox element is stale: {}", locator, e);
        }
    }

    /**
     * Deselects a checkbox if it's already selected.
     *
     * @param locator the checkbox element to deselect, either By locator or WebElement
     */
    public void deselectCheckBox(Object locator) {
        WebElement checkboxElement = resolveLocator(locator);
        waitUntilClickable(checkboxElement);
        try {
            if (checkboxElement.isSelected()) {
                checkboxElement.click();
                logger.info("Checkbox deselected: {}", locator);
            } else {
                logger.info("Checkbox already deselected: {}", locator);
            }
        } catch (StaleElementReferenceException e) {
            logger.error("Checkbox element is stale: {}", locator, e);
        }
    }

    /**
     * Selects all checkboxes in a list.
     *
     * @param checkboxes the list of checkbox elements to select
     */
    public void selectAllCheckBoxes(List<WebElement> checkboxes) {
        for (WebElement checkbox : checkboxes) {
            selectCheckBox(checkbox);
        }
    }

    /**
     * Deselects all checkboxes in a list.
     *
     * @param checkboxes the list of checkbox elements to deselect
     */
    public void deselectAllCheckBoxes(List<WebElement> checkboxes) {
        for (WebElement checkbox : checkboxes) {
            deselectCheckBox(checkbox);
        }
    }

    /**
     * Waits until the checkbox is clickable.
     *
     * @param checkboxElement the checkbox element to wait for
     */
    private void waitUntilClickable(WebElement checkboxElement) {
        wait.until(ExpectedConditions.elementToBeClickable(checkboxElement));
        logger.info("Checkbox is clickable: {}", checkboxElement);
    }

    /**
     * Waits until the checkbox is visible.
     *
     * @param checkboxElement the checkbox element to wait for
     */
    private void waitUntilVisible(WebElement checkboxElement) {
        wait.until(ExpectedConditions.visibilityOf(checkboxElement));
        logger.info("Checkbox is visible: {}", checkboxElement);
    }
}
