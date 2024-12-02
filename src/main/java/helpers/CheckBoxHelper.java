package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
     * Selects a checkbox if it's not already selected.
     *
     * @param checkboxElement the checkbox element to select
     */
    public void selectCheckBox(WebElement checkboxElement) {
        waitUntilClickable(checkboxElement);
        try {
            if (!checkboxElement.isSelected()) {
                checkboxElement.click();
                logger.info("Checkbox selected: {}", checkboxElement);
            } else {
                logger.info("Checkbox already selected: {}", checkboxElement);
            }
        } catch (StaleElementReferenceException e) {
            logger.error("Checkbox element is stale: {}", checkboxElement, e);
        }
    }

    /**
     * Deselects a checkbox if it's already selected.
     *
     * @param checkboxElement the checkbox element to deselect
     */
    public void deselectCheckBox(WebElement checkboxElement) {
        waitUntilClickable(checkboxElement);
        try {
            if (checkboxElement.isSelected()) {
                checkboxElement.click();
                logger.info("Checkbox deselected: {}", checkboxElement);
            } else {
                logger.info("Checkbox already deselected: {}", checkboxElement);
            }
        } catch (StaleElementReferenceException e) {
            logger.error("Checkbox element is stale: {}", checkboxElement, e);
        }
    }

    /**
     * Checks if a checkbox is selected.
     *
     * @param checkboxElement the checkbox element to check
     * @return true if the checkbox is selected, false otherwise
     */
    public boolean isSelected(WebElement checkboxElement) {
        waitUntilVisible(checkboxElement);
        try {
            boolean isSelected = checkboxElement.isSelected();
            logger.info("Checkbox selected state: {} - {}", checkboxElement, isSelected);
            return isSelected;
        } catch (StaleElementReferenceException e) {
            logger.error("Checkbox element is stale: {}", checkboxElement, e);
            return false;
        }
    }

    /**
     * Toggles the state of a checkbox (selects if deselected, deselects if selected).
     *
     * @param checkboxElement the checkbox element to toggle
     */
    public void toggleCheckBox(WebElement checkboxElement) {
        waitUntilClickable(checkboxElement);
        try {
            checkboxElement.click();
            logger.info("Checkbox toggled: {}", checkboxElement);
        } catch (StaleElementReferenceException e) {
            logger.error("Checkbox element is stale: {}", checkboxElement, e);
        }
    }

    /**
     * Selects all checkboxes in a list.
     *
     * @param checkboxElements the list of checkbox elements to select
     */
    public void selectAllCheckBoxes(List<WebElement> checkboxElements) {
        for (WebElement checkbox : checkboxElements) {
            selectCheckBox(checkbox);
        }
    }

    /**
     * Deselects all checkboxes in a list.
     *
     * @param checkboxElements the list of checkbox elements to deselect
     */
    public void deselectAllCheckBoxes(List<WebElement> checkboxElements) {
        for (WebElement checkbox : checkboxElements) {
            deselectCheckBox(checkbox);
        }
    }

    /**
     * Toggles all checkboxes in a list.
     *
     * @param checkboxElements the list of checkbox elements to toggle
     */
    public void toggleAllCheckBoxes(List<WebElement> checkboxElements) {
        for (WebElement checkbox : checkboxElements) {
            toggleCheckBox(checkbox);
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