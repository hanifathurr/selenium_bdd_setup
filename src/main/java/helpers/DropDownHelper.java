package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
     * Selects an option by its visible text.
     *
     * @param dropdownElement the dropdown WebElement
     * @param visibleText    the visible text of the option to select
     */
    public void selectByVisibleText(WebElement dropdownElement, String visibleText) {
        waitUntilDropdownIsReady(dropdownElement);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(visibleText);
        logger.info("Selected option by visible text: '{}'", visibleText);
    }

    /**
     * Selects an option by its value.
     *
     * @param dropdownElement the dropdown WebElement
     * @param value          the value of the option to select
     */
    public void selectByValue(WebElement dropdownElement, String value) {
        waitUntilDropdownIsReady(dropdownElement);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue(value);
        logger.info("Selected option by value: '{}'", value);
    }

    /**
     * Selects an option by its index.
     *
     * @param dropdownElement the dropdown WebElement
     * @param index          the index of the option to select
     */
    public void selectByIndex(WebElement dropdownElement, int index) {
        waitUntilDropdownIsReady(dropdownElement);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByIndex(index);
        logger.info("Selected option by index: {}", index);
    }

    /**
     * Gets the text of the currently selected option.
     *
     * @param dropdownElement the dropdown WebElement
     * @return the text of the selected option
     */
    public String getSelectedText(WebElement dropdownElement) {
        Select dropdown = new Select(dropdownElement);
        String selectedText = dropdown.getFirstSelectedOption().getText();
        logger.info("Currently selected option text: '{}'", selectedText);
        return selectedText;
    }

    /**
     * Gets all options in the dropdown.
     *
     * @param dropdownElement the dropdown WebElement
     * @return a list of all options
     */
    public List<WebElement> getAllOptions(WebElement dropdownElement) {
        Select dropdown = new Select(dropdownElement);
        List<WebElement> options = dropdown.getOptions();
        logger.info("Retrieved all options from dropdown, total options: {}", options.size());
        return options;
    }

    /**
     * Waits until the dropdown is clickable.
     *
     * @param dropdownElement the dropdown WebElement
     */
    public void waitUntilDropdownIsReady(WebElement dropdownElement) {
        wait.until(ExpectedConditions.elementToBeClickable(dropdownElement));
        logger.info("Dropdown is ready for interaction.");
    }

    /**
     * Deselects all options in a multi-select dropdown.
     *
     * @param dropdownElement the dropdown WebElement
     */
    public void deselectOptions(WebElement dropdownElement) {
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
     * @param dropdownElement the dropdown WebElement
     * @param optionText     the text of the option to check
     * @return true if the option is disabled, false otherwise
     */
    public boolean isOptionDisabled(WebElement dropdownElement, String optionText) {
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
     * @param dropdownElement the dropdown WebElement
     */
    public void resetDropdownSelection(WebElement dropdownElement) {
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