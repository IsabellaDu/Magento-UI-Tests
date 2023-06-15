package org.example.pages;

import lombok.Getter;
import org.example.utils.ConfigProvider;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.example.utils.WaitUtils.waitUntilElementIsClickable;
import static org.example.utils.WaitUtils.waitUntilElementIsVisible;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[@class='logo']")
    private WebElement elLogoCta;
    @Getter
    @FindBy(xpath = "//div[@class='panel wrapper']//a[@href='https://magento2-demo.magebit.com/customer/account/create/']")
    private WebElement elCreateAccountCta;
    @Getter
    @FindBy(xpath = "//div[@class='panel header']//li[@class='authorization-link']//a")
    private WebElement elSignInCta;
    @Getter
    @FindBy(xpath = "//input[@id='search']")
    private WebElement elSearchInput;

    @Getter
    @FindBy(xpath = "//a[@class='action showcart']")
    private WebElement elShowCartCta;


    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public void open() {
        super.driver.get(ConfigProvider.getBASE_URL());
        ensureOpened();
    }

    @Override
    public void ensureOpened() {
        waitUntilElementIsVisible(super.driver, elLogoCta);
    }

    public void clickToButton(WebDriver driver, WebElement elCta) {
        waitUntilElementIsClickable(driver, elCta);
        elCta.click();
    }


    public void setSearchInput(String item) {
        waitUntilElementIsVisible(driver, elSearchInput);
        elSearchInput.clear();
        elSearchInput.sendKeys(item, Keys.ENTER);
    }
}
