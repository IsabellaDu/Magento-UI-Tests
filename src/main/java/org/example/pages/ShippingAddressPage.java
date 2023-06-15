package org.example.pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

import static org.example.core.CommonPageMethods.emailPatternMatches;
import static org.example.utils.WaitUtils.waitUntilElementIsClickable;
import static org.example.utils.WaitUtils.waitUntilElementIsVisible;

public class ShippingAddressPage extends BasePage {
    private final String SHIPPING_ADDRESS_PAGE_URL = "https://magento2-demo.magebit.com/checkout/#shipping";
    @FindBy(xpath = "//li[@id='shipping']/div[@class='step-title']")
    private WebElement elShippingAddressTitle;

    @FindBy(xpath = "//div[@class='control _with-tooltip']//input[@id='customer-email']")
    private WebElement elEmailInput;

    @FindBy(xpath = "//div[@class='control _with-tooltip']//div[@id='customer-email-error']")
    private WebElement elEmailError;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='firstname']")
    private WebElement elFirstNameInput;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='firstname']/following-sibling::div[@class='field-error']/span")
    private WebElement elFirstNameError;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='lastname']")
    private WebElement elLastNameInput;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='lastname']/following-sibling::div[@class='field-error']/span")
    private WebElement elLastNameError;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='company']")
    private WebElement elCompanyInput;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='street[0]']")
    private WebElement elStreetAddressInput;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='street[0]']/following-sibling::div[@class='field-error']/span")
    private WebElement elStreetAddressError;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//select[@name='country_id']")
    private WebElement elCountryDropDown;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//select[@name='country_id']//option")
    private List<WebElement> elsCountryList;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//select[@name='country_id']/following-sibling::div[@class='field-error']/span")
    private WebElement elCountryError;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//select[@name='region_id']")
    private WebElement elProvinceDropDown;
    @FindBy(xpath = "//div[@id='shipping-new-address-form']//select[@name='region_id']//option")
    private List<WebElement> elsProvinceList;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='region']")
    private WebElement elProvinceInput;
    @FindBy(xpath = "//div[@id='shipping-new-address-form']//select[@name='region_id']/following-sibling::div[@class='field-error']/span")
    private WebElement elProvinceError;
    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='city']")
    private WebElement elCityInput;
    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='city']/following-sibling::div[@class='field-error']/span")
    private WebElement elCityError;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='postcode']")
    private WebElement elPostalCodeInput;
    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='postcode']/following-sibling::div[@class='field-error']/span")
    private WebElement elPostalCodeError;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='telephone']")
    private WebElement elPhoneInput;
    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='telephone']/following-sibling::div[@class='field-error']/span")
    private WebElement elPhoneCodeError;

    @FindBy(xpath = "//div[@id='checkout-shipping-method-load']//input")
    private List<WebElement> elShippingMethodCheckBoxList;

    @FindBy(xpath = "//div[@id='checkout-shipping-method-load']//span[@class='price']//span")
    private List<WebElement> elsShippingMethodPriceList;

    @Getter
    @FindBy(xpath = "//div[@class='primary']//button[@data-role='opc-continue']")
    private WebElement elNextCta;

    @Getter
    private double shippingMethodPrice;

    public ShippingAddressPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public void open() {
        super.driver.get(SHIPPING_ADDRESS_PAGE_URL);
        ensureOpened();
    }

    @Override
    public void ensureOpened() {
        waitUntilElementIsVisible(driver, elShippingAddressTitle);
    }

    public void isErrorFieldDisplayedWhenFieldIsEmpty(String value, WebElement errorField) {
        if (value.isEmpty()) {
            waitUntilElementIsVisible(driver, errorField);
            Assert.assertEquals(errorField.getText(), "This is a required field.", "expected error message doesn't match actual error message");
        }
    }

    public void checkErrorMessagesWorkCorrectly(String countryCode, String province) {
        isErrorFieldDisplayedForEmail(elEmailInput.getAttribute("value"), elEmailError);
        isErrorFieldDisplayedWhenFieldIsEmpty(elFirstNameInput.getAttribute("value"), elFirstNameError);
        isErrorFieldDisplayedWhenFieldIsEmpty(elLastNameInput.getAttribute("value"), elLastNameError);
        isErrorFieldDisplayedWhenFieldIsEmpty(elStreetAddressInput.getAttribute("value"), elStreetAddressError);
        isErrorFieldDisplayedWhenFieldIsEmpty(countryCode, elCountryError);
        isErrorFieldDisplayedForProvince(countryCode, province);
        isErrorFieldDisplayedWhenFieldIsEmpty(elCityInput.getAttribute("value"), elCityError);
        isErrorFieldDisplayedWhenFieldIsEmpty(elPostalCodeInput.getAttribute("value"), elPostalCodeError);
        isErrorFieldDisplayedWhenFieldIsEmpty(elPhoneInput.getAttribute("value"), elPhoneCodeError);
    }

    public void isErrorFieldDisplayedForProvince(String countryCode, String province) {
        if (countryCode.equalsIgnoreCase("ca") || countryCode.equalsIgnoreCase("us")) {
            isErrorFieldDisplayedWhenFieldIsEmpty(province, elProvinceError);
        }
    }

    public void isErrorFieldDisplayedForEmail(String value, WebElement errorField) {
        isErrorFieldDisplayedWhenFieldIsEmpty(value, errorField);
        if (!emailPatternMatches(value)) {
            waitUntilElementIsVisible(driver, errorField);
            Assert.assertEquals(errorField.getText(), "Please enter a valid email address (Ex: johndoe@domain.com).", "expected email error message doesn't match actual message");
        }
    }

    public void setEmail(String email) {
        waitUntilElementIsVisible(driver, elEmailInput);
        elEmailInput.clear();
        elEmailInput.sendKeys(email);
    }

    public void setFirstName(String firstName) {
        waitUntilElementIsVisible(driver, elFirstNameInput);
        elFirstNameInput.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        waitUntilElementIsVisible(driver, elLastNameInput);
        elLastNameInput.sendKeys(lastName);
    }

    public void setCompany(String company) {
        waitUntilElementIsVisible(driver, elCompanyInput);
        elCompanyInput.sendKeys(company);
    }

    public void setStreetAddress(String address) {
        waitUntilElementIsVisible(driver, elStreetAddressInput);
        elStreetAddressInput.sendKeys(address);
    }

    public void setCity(String city) {
        waitUntilElementIsVisible(driver, elCityInput);
        elCityInput.sendKeys(city);
    }

    public void setPostalCode(String postalCode) {
        waitUntilElementIsVisible(driver, elPostalCodeInput);
        elPostalCodeInput.sendKeys(postalCode);
    }

    public void setPhoneNumber(String phoneNumber) {
        waitUntilElementIsVisible(driver, elPhoneInput);
        elPhoneInput.sendKeys(phoneNumber);
    }

    public void setShippingMethod(String shippingMethod) {
        for (int itemIndex = 0; itemIndex < elShippingMethodCheckBoxList.size(); itemIndex++) {
            if (elShippingMethodCheckBoxList.get(itemIndex).getAttribute("value").toLowerCase().contains(shippingMethod.toLowerCase())) {
                elShippingMethodCheckBoxList.get(itemIndex).click();
                shippingMethodPrice = Double.parseDouble(elsShippingMethodPriceList.get(itemIndex).getText().replace("$", ""));
            }
        }
    }

    public void setCountryAndProvince(String countryCode, String province) {
        elCountryDropDown.click();
        waitUntilElementIsVisible(driver, elsCountryList.get(0));
        for (WebElement elCountry : elsCountryList) {
            if (elCountry.getAttribute("value").equalsIgnoreCase(countryCode)) {
                elCountry.click();
                if (countryCode.equalsIgnoreCase("CA") || countryCode.equalsIgnoreCase("US")) {
                    waitUntilElementIsClickable(driver, elProvinceDropDown);
                    elProvinceDropDown.click();
                    waitUntilElementIsVisible(driver, elsProvinceList.get(0));
                    for (WebElement elProvince : elsProvinceList) {
                        if (elProvince.getText().equalsIgnoreCase(province)) {
                            elProvince.click();
                        }
                    }
                } else {
                    waitUntilElementIsVisible(driver, elProvinceInput);
                    elProvinceInput.sendKeys(province);
                }
            }
        }
    }
}

