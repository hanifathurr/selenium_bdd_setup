package helpers;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DropDownHelper {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public DropDownHelper(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectByVisibleText(WebElement dropdownElement, String visibleText) {
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(visibleText);
    }

    public void selectByValue(WebElement dropdownElement, String value) {
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue(value);
    }

    public void selectByIndex(WebElement dropdownElement, int index) {
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByIndex(index);
    }

    public String getSelectedText(WebElement dropdownElement) {
        Select dropdown = new Select(dropdownElement);
        return dropdown.getFirstSelectedOption().getText();
    }

    public List<WebElement> getAllOptions(WebElement dropdownElement) {
        Select dropdown = new Select(dropdownElement);
        return dropdown.getOptions();
    }

    public void waitUntilDropdownIsReady(WebElement dropdownElement) {
        wait.until(ExpectedConditions.elementToBeClickable(dropdownElement));
    }

    public void deselectOptions(WebElement dropdownElement) {
        Select dropdown = new Select(dropdownElement);

        if (dropdown.isMultiple()) {
            dropdown.deselectAll();
        } else {
            throw new UnsupportedOperationException("Cannot deselect options in a single-select dropdown");
        }
    }

    public boolean isOptionDisabled(WebElement dropdownElement, String optionText) {
        Select dropdown = new Select(dropdownElement);

        List<WebElement> options = dropdown.getOptions();
        for (WebElement option : options) {
            if (option.getText().equals(optionText)) {
                return !option.isEnabled();
            }
        }
        throw new NoSuchElementException("Option with text '" + optionText + "' not found in the dropdown");
    }

    public void resetDropdownSelection(WebElement dropdownElement) {
        Select dropdown = new Select(dropdownElement);
        dropdown.deselectAll();
    }
}
