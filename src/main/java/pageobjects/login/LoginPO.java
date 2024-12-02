package pageobjects.login;

import helpers.AssertionHelper;
import helpers.GeneralHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.config.PropertyFileReader;

public class LoginPO {
    private final AssertionHelper hardAssert;
    WebDriver driver;
    GeneralHelper selenium;
    private static final PropertyFileReader config = new PropertyFileReader();
    // Elements
    @FindBy (id = "user-name")
    WebElement usernameInput;
    @FindBy (id = "password")
    WebElement passwordInput;
    @FindBy (id = "login-button")
    WebElement loginButton;
    public LoginPO(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.selenium = new GeneralHelper(driver);
        this.hardAssert = new AssertionHelper(driver);
    }
    public void enterUsername(String username){
        selenium.fillText(usernameInput, username);
    }

    public void enterPassword(String password) {
        selenium.fillText(passwordInput, password);
    }
    public void clickLoginButton() {
        selenium.click(loginButton);
    }
    public void verifyOpenLoginPage() {
        hardAssert.assertPageTitle(config.getProperty("loginPageTitle"));
        hardAssert.assertCurrentUrl(config.getProperty("homePageUrl"));
        hardAssert.assertElementDisplayed(usernameInput);
        hardAssert.assertElementDisplayed(passwordInput);
        hardAssert.assertElementDisplayed(loginButton);
    }
    public void verifyLoginSuccessful(){
        hardAssert.assertPageTitle(config.getProperty("inventoryPageTitle"));
        hardAssert.assertCurrentUrl(config.getProperty("inventoryPageUrl"));
    }
}
