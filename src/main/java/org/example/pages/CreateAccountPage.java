package org.example.pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static org.example.core.CommonPageMethods.emailPatternMatches;
import static org.example.core.CommonPageMethods.passwordPatternMatches;
import static org.example.utils.WaitUtils.waitUntilElementIsVisible;

public class CreateAccountPage extends BasePage {
    @Getter
    private final String CREATE_ACCOUNT_URL = "https://magento2-demo.magebit.com/customer/account/create/";

    @FindBy(xpath = "//input[@id='firstname']")
    private WebElement elFirstNameInput;

    @FindBy(xpath = "//div[@id='firstname-error']")
    private WebElement elFirstnameError;
    @FindBy(xpath = "//input[@id='lastname']")
    private WebElement elLastNameInput;
    @FindBy(xpath = "//div[@id='lastname-error']")
    private WebElement elLastnameError;
    @FindBy(xpath = "//input[@id='is_subscribed']")
    private WebElement elIsSubscribedCheckBox;

    @FindBy(xpath = "//input[@id='email_address']")
    private WebElement elEmailInput;

    @FindBy(xpath = "//div[@id='email_address-error']")
    private WebElement elEmailError;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement elPasswordInput;
    @FindBy(xpath = "//div[@id='password-error']")
    private WebElement elPasswordError;
    @FindBy(xpath = "//input[@id='password-confirmation']")
    private WebElement elPasswordConfirmationInput;
    @FindBy(xpath = "//div[@id='password-confirmation-error']")
    private WebElement elPasswordConfirmationError;
    @Getter
    @FindBy(xpath = "//button[@class='action submit primary']")
    private WebElement elCreateAccountCta;

    public CreateAccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public void open() {
        super.driver.get(CREATE_ACCOUNT_URL);
        ensureOpened();
    }

    @Override
    public void ensureOpened() {
        waitUntilElementIsVisible(driver, elFirstNameInput);
    }

    public void setFirstName(String firstName) {
        waitUntilElementIsVisible(driver, elFirstNameInput);
        elFirstNameInput.sendKeys(firstName);
    }

    public void checkFirstNameError(String firstName) {
        if (firstName.length() < 1) {
            waitUntilElementIsVisible(driver, elFirstnameError);
            Assert.assertEquals(elFirstnameError.getText(), "This is a required field.", "expected firstname error message doesn't match actual message");
        }
    }

    public void setLastName(String lastName) {
        waitUntilElementIsVisible(driver, elLastNameInput);
        elLastNameInput.sendKeys(lastName);
    }

    public void checkLastNameError(String lastName) {
        if (lastName.length() < 1) {
            waitUntilElementIsVisible(driver, elLastnameError);
            Assert.assertEquals(elLastnameError.getText(), "This is a required field.", "expected lastname error message doesn't match actual message");
        }
    }

    public void setEmail(String email) {
        waitUntilElementIsVisible(driver, elEmailInput);
        elEmailInput.sendKeys(email);
    }

    public void checkEmailError(String email) {
        if (email.isEmpty()) {
            waitUntilElementIsVisible(driver, elEmailError);
            Assert.assertEquals(elEmailError.getText(), "This is a required field.", "expected email error message doesn't match actual message");
        } else if (!emailPatternMatches(email)) {
            waitUntilElementIsVisible(driver, elEmailError);
            Assert.assertEquals(elEmailError.getText(), "Please enter a valid email address (Ex: johndoe@domain.com).", "expected email error message doesn't match actual message");
        }
    }

    public void setPassword(String password) {
        waitUntilElementIsVisible(driver, elPasswordInput);
        elPasswordInput.sendKeys(password);
    }

    public void checkPasswordError(String password) {
        if (password.isEmpty()) {
            waitUntilElementIsVisible(driver, elPasswordError);
            Assert.assertEquals(elPasswordError.getText(), "This is a required field.", "expected password error message doesn't match actual message");
        } else if (password.length() < 8) {
            waitUntilElementIsVisible(driver, elPasswordError);
            Assert.assertEquals(elPasswordError.getText(), "Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.", "expected password error message doesn't match actual message");
        } else if (!passwordPatternMatches(password)) {
            waitUntilElementIsVisible(driver, elPasswordError);
            Assert.assertEquals(elPasswordError.getText(), "Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters.", "expected password error message doesn't match actual message");
        }
    }

    public void confirmPassword(String confirmPassword) {
        waitUntilElementIsVisible(driver, elPasswordConfirmationInput);
        elPasswordConfirmationInput.sendKeys(confirmPassword);
    }

    public void checkConfirmPasswordError(String password, String confirmPassword) {
        if (confirmPassword.isEmpty()) {
            waitUntilElementIsVisible(driver, elPasswordConfirmationError);
            Assert.assertEquals(elPasswordConfirmationError.getText(), "This is a required field.", "expected passwordConfirmation error message doesn't match actual message");
        } else if (!confirmPassword.equals(password)) {
            waitUntilElementIsVisible(driver, elPasswordConfirmationError);
            Assert.assertEquals(elPasswordConfirmationError.getText(), "Please enter the same value again.", "expected passwordConfirmation error message doesn't match actual message");
        }
    }

    public void checkDoErrorFiledWorkCorrectly(String firstName, String lastName, String email, String password, String confirmPassword) {
        checkFirstNameError(firstName);
        checkLastNameError(lastName);
        checkEmailError(email);
        checkPasswordError(password);
        checkConfirmPasswordError(password, confirmPassword);
    }
}
