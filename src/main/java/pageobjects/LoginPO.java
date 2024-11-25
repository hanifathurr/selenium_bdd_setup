package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPO {
    // Elements
    @FindBy (id = "")
    WebElement usernameInput;
    @FindBy (id = "")
    WebElement passwordInput;
    @FindBy (id = "")
    WebElement loginButton;
    public LoginPO(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    public void enterUsername(String username){
//        selenium.sendKeysToElement(usernameInput, username);
    }

    public void setPasswordInput(String password) {
//        selenium.sendKeysToElement(passwordInput, password);
    }
}
