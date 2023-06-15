package uiTests;

import io.qameta.allure.Description;
import org.example.core.data.DataProviderMethods;
import org.example.listeners.RetryListener;
import org.example.pages.CreateAccountPage;
import org.example.pages.HomePage;
import org.example.pages.LogInPage;
import org.example.pages.MyAccountPage;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.example.utils.WaitUtils.*;

public class LoginAndSIgnInVerificationTest extends BaseTest {
    @Test
    @Description("Test Description: on the Home Page click on the Create Account button.")
    public void clickOnCreateAccountCta() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.getElCreateAccountCta().click();
        CreateAccountPage createAccountPage = new CreateAccountPage(driver);
        Assert.assertEquals(driver.getCurrentUrl(), createAccountPage.getCREATE_ACCOUNT_URL(), "expected url doesn't match actual url");
    }

    private String currentEmail;

    @Test(dependsOnMethods = {"clickOnCreateAccountCta"}, dataProvider = "dataForCanCreateAccountTest",
            dataProviderClass = DataProviderMethods.class, retryAnalyzer = RetryListener.class)
    @Description("Test Description: on the Create Account Page check can create account with all test data cases.")
    public void canCreateAccount(String firstName, String lastName, String email, String password, String confirmPassword) {
        clickOnCreateAccountCta();
        CreateAccountPage createAccountPage = new CreateAccountPage(driver);
        createAccountPage.setFirstName(firstName);
        createAccountPage.setLastName(lastName);
        createAccountPage.setEmail(email);
        createAccountPage.setPassword(password);
        createAccountPage.confirmPassword(confirmPassword);
        waitUntilElementIsClickable(driver, createAccountPage.getElCreateAccountCta());
        createAccountPage.getElCreateAccountCta().click();
        createAccountPage.checkDoErrorFiledWorkCorrectly(firstName, lastName, email, password, confirmPassword);
        currentEmail = email;
    }

    @Test(dependsOnMethods = {"canCreateAccount"}, retryAnalyzer = RetryListener.class)
    @Description("Test Description: on the MyAccount Page check is account created.")
    public void checkIsAccountCreated() {
        Actions action = new Actions(driver);
        action.pause(100);
        waitUntilUrlIsLoaded(driver, "/customer/account/");
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.ensureOpened();
        waitUntilElementIsVisible(driver, myAccountPage.getElContactInfo());
        Assert.assertTrue(myAccountPage.getElContactInfo().getText().contains(currentEmail), "expected contact information doesn't match actual contact information");
    }

    @Test
    @Description("Test Description: on the Home Page click on the LogIn button.")
    public void clickOnSignInCta() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        waitUntilElementIsClickable(driver, homePage.getElSignInCta());
        homePage.getElSignInCta().click();
        LogInPage logInPage = new LogInPage(driver);
        Assert.assertEquals(driver.getCurrentUrl(), logInPage.getLOGIN_PAGE_URL(), "expected url doesn't match actual url");
    }

    @Test(dependsOnMethods = {"clickOnSignInCta"}, dataProvider = "dataForCanLoginTest",
            dataProviderClass = DataProviderMethods.class, retryAnalyzer = RetryListener.class)
    @Description("Test Description: on the LogIn Page check can login with all test data cases.")
    public void canLogIn(String email, String password) {
        clickOnSignInCta();
        LogInPage logInPage = new LogInPage(driver);
        logInPage.setEmail(email);
        logInPage.setPassword(password);
        logInPage.getElSignInCta().click();
        logInPage.checkErrorMessagesWorkCorrectly();
        logInPage.checkSignIn(email, password);
    }
}
