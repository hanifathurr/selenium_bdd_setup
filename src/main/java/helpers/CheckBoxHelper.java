package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckBoxHelper {
    private final WebDriver driver;
    public CheckBoxHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void selectCheckBox(WebElement checkboxElement) {
        if (!checkboxElement.isSelected()) {
            checkboxElement.click();
        }
    }
    public void unselectCheckBox(WebElement checkboxElement) {
        if (checkboxElement.isSelected()) {
            checkboxElement.click();
        }
    }
    public boolean isSelected(WebElement checkboxElement) {
        return checkboxElement.isSelected();
    }
}
