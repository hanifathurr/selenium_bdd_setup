package stepdefinitions;

import hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import utilities.pageobjectmanager.PageObjectManager;
import pageobjects.login.LoginPO;
import utilities.config.PropertyFileReader;
import utilities.drivermanager.GetDriverManager;

public class LoginSteps {
    private WebDriver driver;
    private PageObjectManager pageObjectManager;
    private static final PropertyFileReader config = new PropertyFileReader();
    private LoginPO loginPage;

    public LoginSteps() {
        // Get the PageObjectManager from Hooks
        this.driver = GetDriverManager.getDriver(config.getBrowser());
        this.pageObjectManager = new PageObjectManager(driver);
        this.loginPage = pageObjectManager.getLoginPage();
    }

    @Given("User is on the Login page")
    public void userIsOnTheLoginPage() {
        loginPage.verifyOpenLoginPage();
    }

    @When("User logs in using username {string} and password {string}")
    public void userLogsInUsingUsernameAndPassword(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }

    @Then("User redirected to the Inventory page")
    public void userRedirectedToTheInventorypage() {
        loginPage.verifyLoginSuccessful();
    }
}
