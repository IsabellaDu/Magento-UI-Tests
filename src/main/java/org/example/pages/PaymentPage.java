package org.example.pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static org.example.utils.WaitUtils.waitUntilElementIsVisible;
import static org.example.utils.WaitUtils.waitUntilUrlIsLoaded;

public class PaymentPage extends BasePage {
    private final String PAYMENT_PAGE_URL = "https://magento2-demo.magebit.com/checkout/#payment";
    @FindBy(xpath = "//div[@class='payment-group']//div[@class='step-title']")
    private WebElement elTitle;

    @FindBy(xpath = "//tr[@class='totals sub']//span[@class='price']")
    private WebElement elCartSubtotalPrice;

    @FindBy(xpath = "//tr[@class='totals shipping excl']//span[@class='price']")
    private WebElement elShippingPrice;

    @FindBy(xpath = "//tr[@class='grand totals']//span[@class='price']")
    private WebElement elOrderTotalPrice;
    @Getter
    @FindBy(xpath = "//div[@class='primary']//button[@class='action primary checkout']")
    private WebElement elPlaceOrderCta;

    @FindBy(xpath = "//div[@class='page-title-wrapper']//span[@data-ui-id='page-title-wrapper']")
    private WebElement elSuccessTitle;

    public PaymentPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public void open() {
        super.driver.get(PAYMENT_PAGE_URL);
        ensureOpened();
    }

    @Override
    public void ensureOpened() {
        waitUntilElementIsVisible(driver, elTitle);
    }

    public void checkOrderSummary(Double subtotalPrice, Double shippingPrice) {
        waitUntilElementIsVisible(driver, elCartSubtotalPrice);
        waitUntilElementIsVisible(driver, elShippingPrice);
        waitUntilElementIsVisible(driver, elOrderTotalPrice);

        double cartSubtotalPrice = Double.parseDouble(elCartSubtotalPrice.getText().replace("$", ""));
        double currentShippingPrice = Double.parseDouble(elShippingPrice.getText().replace("$", ""));
        double orderTotalPrice = Double.parseDouble(elOrderTotalPrice.getText().replace("$", ""));

        //Assert.assertEquals(cartSubtotalPrice, subtotalPrice, "expected cart subtotal doesn't match actual cart subtotal");
        Assert.assertEquals(currentShippingPrice, shippingPrice, "expected shipping price doesn't match actual shipping price");
        Assert.assertEquals(orderTotalPrice, cartSubtotalPrice + currentShippingPrice, "expected order total doesn't match actual order total");
    }

    public void isSuccessfulCheckout() {
        waitUntilUrlIsLoaded(driver, "/checkout/onepage/success/");
        waitUntilElementIsVisible(driver, elSuccessTitle);
        Assert.assertEquals(elSuccessTitle.getText(), "Thank you for your purchase!", "expected message doesn't match actual message");
    }
}
