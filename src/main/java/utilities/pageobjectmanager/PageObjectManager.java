package utilities.pageobjectmanager;

import org.openqa.selenium.WebDriver;
import pageobjects.login.LoginPO;

public class PageObjectManager {
    private final WebDriver driver;
    private LoginPO loginPO;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPO getLoginPage() {
        if (loginPO == null) {
            loginPO = new LoginPO(driver);
        }
        return loginPO;
    }
}
