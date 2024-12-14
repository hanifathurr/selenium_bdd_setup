package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.config.PropertyFileReader;

import java.time.Duration;
import java.util.List;

public class DropDownHelper {
    private static final Logger logger = LogManager.getLogger(DropDownHelper.class);
    private static final PropertyFileReader config = new PropertyFileReader();
    private final WebDriver driver;
    private final WebDriverWait wait;

    public DropDownHelper(WebDriver driver) {
        this.driver = driver;
        int waitDuration = config.getDefaultWaitDuration();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
    }

    /**
     * Waits for the element (located by a By locator or WebElement) to be visible and returns it.
     *
     * @param locator the By locator or WebElement to be waited for
     * @return the visible WebElement
     */
    public WebElement getElement(Object locator) {
        logger.debug("Waiting for locator to be clickable: {}", locator);
        try {
            WebElement element = resolveLocator(locator);
            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            logger.debug("Element is now clickable: {}", clickableElement);
            return clickableElement;
        } catch (NoSuchElementException e) {
            logger.error("Element not found: {}. Exception: {}", locator, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while waiting for locator to be clickable: {}. Exception: {}", locator, e.getMessage());
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
     * Selects an option by its visible text.
     *
     * @param locator the dropdown WebElement
     * @param visibleText    the visible text of the option to select
     */
    public void selectByVisibleText(Object locator, String visibleText) {
        WebElement dropdownElement = getElement(locator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(visibleText);
        logger.info("Selected option by visible text: '{}'", visibleText);
    }

    /**
     * Selects an option by its value.
     *
     * @param locator the dropdown WebElement
     * @param value          the value of the option to select
     */
    public void selectByValue(Object locator, String value) {
        WebElement dropdownElement = getElement(locator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue(value);
        logger.info("Selected option by value: '{}'", value);
    }

    /**
     * Selects an option by its index.
     *
     * @param locator the dropdown WebElement
     * @param index          the index of the option to select
     */
    public void selectByIndex(Object locator, int index) {
        WebElement dropdownElement = getElement(locator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByIndex(index);
        logger.info("Selected option by index: {}", index);
    }

    /**
     * Gets the text of the currently selected option.
     *
     * @param locator the dropdown WebElement
     * @return the text of the selected option
     */
    public String getSelectedText(Object locator) {
        WebElement dropdownElement = getElement(locator);
        Select dropdown = new Select(dropdownElement);
        String selectedText = dropdown.getFirstSelectedOption().getText();
        logger.info("Currently selected option text: '{}'", selectedText);
        return selectedText;
    }

    /**
     * Gets all options in the dropdown.
     *
     * @param locator the dropdown WebElement
     * @return a list of all options
     */
    public List<WebElement> getAllOptions(Object locator) {
        WebElement dropdownElement = getElement(locator);
        Select dropdown = new Select(dropdownElement);
        List<WebElement> options = dropdown.getOptions();
        logger.info("Retrieved all options from dropdown, total options: {}", options.size());
        return options;
    }

    /**
     * Deselects all options in a multi-select dropdown.
     *
     * @param locator the dropdown WebElement
     */
    public void deselectOptions(Object locator) {
        WebElement dropdownElement = getElement(locator);
        Select dropdown = new Select(dropdownElement);

        if (dropdown.isMultiple()) {
            dropdown.deselectAll();
            logger.info("All options deselected in multi-select dropdown.");
        } else {
            logger.error("Cannot deselect options in a single-select dropdown.");
            throw new UnsupportedOperationException("Cannot deselect options in a single-select dropdown");
        }
    }

    /**
     * Checks if a specific option is disabled in the dropdown.
     *
     * @param locator the dropdown WebElement
     * @param optionText     the text of the option to check
     * @return true if the option is disabled, false otherwise
     */
    public boolean isOptionDisabled(Object locator, String optionText) {
        WebElement dropdownElement = getElement(locator);
        Select dropdown = new Select(dropdownElement);
        List<WebElement> options = dropdown.getOptions();

        for (WebElement option : options) {
            if (option.getText().equals(optionText)) {
                boolean isDisabled = !option.isEnabled();
                logger.info("Option '{}' is {}", optionText, isDisabled ? "disabled" : "enabled");
                return isDisabled;
            }
        }
        logger.error("Option with text '{}' not found in the dropdown", optionText);
        throw new NoSuchElementException("Option with text '" + optionText + "' not found in the dropdown");
    }

    /**
     * Resets the selection in a multi-select dropdown.
     *
     * @param locator the dropdown WebElement
     */
    public void resetDropdownSelection(Object locator) {
        WebElement dropdownElement = getElement(locator);
        Select dropdown = new Select(dropdownElement);
        if (dropdown.isMultiple()) {
            dropdown.deselectAll();
            logger.info("Dropdown selection reset.");
        } else {
            logger.error("Cannot reset selection in a single-select dropdown.");
            throw new UnsupportedOperationException("Cannot reset selection in a single-select dropdown");
        }
    }
}