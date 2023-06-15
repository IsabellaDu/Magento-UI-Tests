package org.example.pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.example.utils.WaitUtils.waitUntilElementIsVisible;

public class MyAccountPage extends BasePage {
    private final String MY_ACCOUNT_URL = "https://magento2-demo.magebit.com/customer/account/";
    @Getter
    @FindBy(xpath = "//div[@class='page-title-wrapper']//h1[@class='page-title']")
    private WebElement elTitle;
    @Getter
    @FindBy(xpath = "//div[@class='box box-information']//div[@class='box-content']//p")
    private WebElement elContactInfo;

    public MyAccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public void open() {
        super.driver.get(MY_ACCOUNT_URL);
        ensureOpened();
    }

    @Override
    public void ensureOpened() {
        waitUntilElementIsVisible(driver, elTitle);
    }
}
