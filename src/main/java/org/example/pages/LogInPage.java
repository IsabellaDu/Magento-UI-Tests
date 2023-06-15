package org.example.pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static org.example.core.CommonPageMethods.emailPatternMatches;
import static org.example.utils.WaitUtils.*;

public class LogInPage extends BasePage {
    @Getter
    private final String LOGIN_PAGE_URL = "https://magento2-demo.magebit.com/customer/account/login/referer/aHR0cHM6Ly9tYWdlbnRvMi1kZW1vLm1hZ2ViaXQuY29tLw%2C%2C/";
    private final String VALID_EMAIL = "roni_cost@example.com";
    private final String VALID_PASSWORD = "roni_cost3@example.com";
    @FindBy(xpath = "//div[@class='page-title-wrapper']//h1[@class='page-title']//span")
    private WebElement elTitle;
    @FindBy(xpath = "//input[@id='email']")
    private WebElement elEmailInput;
    @FindBy(xpath = "//div[@id='email-error']")
    private WebElement elEmailError;

    @FindBy(xpath = "//div[@class='columns']//input[@id='pass']")
    private WebElement elPasswordInput;
    @FindBy(xpath = "//div[@id='pass-error']")
    private WebElement elPasswordError;
    @Getter
    @FindBy(xpath = "//div[@class='columns']//button[@id='send2']")
    private WebElement elSignInCta;

    @FindBy(xpath = "//div[@data-ui-id='message-error']//div")
    private WebElement elSignInError;

    //@FindBy(xpath = "//li[@class='customer-welcome active']//button[@class='action switch']")
    @FindBy(xpath = "//div[@class='panel wrapper']//ul[@class='header links']//li[@class='customer-welcome']/span")
    private WebElement elWelcomeDropDown;

    @FindBy(xpath = "//li[@class='customer-welcome active']//div[@class='customer-menu']//a[@href='https://magento2-demo.magebit.com/customer/account/']")
    private WebElement elMyAccountCta;

    public LogInPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public void open() {
        super.driver.get(LOGIN_PAGE_URL);
        ensureOpened();
    }

    @Override
    public void ensureOpened() {
        waitUntilElementIsVisible(driver, elTitle);
    }

    public void setEmail(String email) {
        waitUntilElementIsVisible(driver, elEmailInput);
        elEmailInput.clear();
        elEmailInput.sendKeys(email);
    }

    public void setPassword(String password) {
        waitUntilElementIsVisible(driver, elPasswordInput);
        elPasswordInput.clear();
        elPasswordInput.sendKeys(password);
    }

    public void isErrorFieldDisplayedWhenFieldIsEmpty(String value, WebElement errorField) {
        if (value.isEmpty()) {
            waitUntilElementIsVisible(driver, errorField);
            Assert.assertEquals(errorField.getText(), "This is a required field.", "expected error message doesn't match actual error message");
        }
    }

    public void isErrorFieldDisplayedForEmail(String value, WebElement errorField) {
        if (value.isEmpty()) {
            waitUntilElementIsVisible(driver, errorField);
            Assert.assertEquals(errorField.getText(), "This is a required field.", "expected error message doesn't match actual error message");
        } else if (!emailPatternMatches(value)) {
            waitUntilElementIsVisible(driver, errorField);
            Assert.assertEquals(errorField.getText(), "Please enter a valid email address (Ex: johndoe@domain.com).", "expected email error message doesn't match actual message");
        }
    }

    public void checkErrorMessagesWorkCorrectly() {
        isErrorFieldDisplayedForEmail(elEmailInput.getAttribute("value"), elEmailError);
        isErrorFieldDisplayedWhenFieldIsEmpty(elPasswordInput.getAttribute("value"), elPasswordError);
    }

    public void checkSignIn(String email, String password) {
        if (email.equals(VALID_EMAIL) && password.equals(VALID_PASSWORD)) {
            waitUntilUrlIsLoaded(driver, "/magento2-demo.magebit.com/");
            waitUntilElementIsClickable(driver, elWelcomeDropDown);
            elWelcomeDropDown.click();
            waitUntilElementIsClickable(driver, elMyAccountCta);
            elMyAccountCta.click();
            MyAccountPage myAccountPage = new MyAccountPage(driver);
            myAccountPage.ensureOpened();
            Assert.assertTrue(myAccountPage.getElContactInfo().getText().contains(email), "expected info doesn't match actual info");
        }
    }
}
