package org.example.pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

import static org.example.utils.WaitUtils.waitUntilElementIsVisible;

public class CheckOutPage extends BasePage {
    @Getter
    private final String CHECKOUT_PAGE_URL = "https://magento2-demo.magebit.com/checkout/";
    @FindBy(xpath = "//a[@class='logo']")
    private WebElement elLogoCta;

    @Getter
    @FindBy(xpath = "//button[@data-role='proceed-to-checkout']")
    private WebElement elCheckoutCta;

    @FindBy(xpath = "//tr[@class='item-info']")
    private List<WebElement> elsItemList;

    @FindBy(xpath = "//td[@class='col price']//span[@class='price']")
    private List<WebElement> elsPriceList;

    @FindBy(xpath = "//input[@data-role='cart-item-qty']")
    private List<WebElement> elsQuantityInputList;

    @FindBy(xpath = "//td[@class='col subtotal']//span[@class='price']")
    private List<WebElement> elsSubtotalPriceList;

    @FindBy(xpath = "//tr[@class='totals sub']//span[@class='price']")
    private WebElement elSubtotalPriceSummary;
    @FindBy(xpath = "//tr[@class='totals sub']//following-sibling::tr[1]//span[@class='price']")
    private WebElement elTax;

    @FindBy(xpath = "//tr[@class='totals']//span[@class='price']")
    private WebElement elDiscount;
    @Getter
    @FindBy(xpath = "//tr[@class='grand totals']//span[@class='price']")
    private WebElement elTotalPrice;

    @FindBy(xpath = "//div[@id='cart-totals']")
    private WebElement elCartTotals;

    public CheckOutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public void open() {
        super.driver.get(CHECKOUT_PAGE_URL);
        ensureOpened();
    }

    @Override
    public void ensureOpened() {
        waitUntilElementIsVisible(driver, elLogoCta);
    }

    @Getter
    private double summaryTotalPrice;

    public void checkIsTotalPriceCorrect() {
        waitUntilElementIsVisible(driver, elsItemList.get(0));
        double subtotalPriceSummary = Double.parseDouble(elSubtotalPriceSummary.getText().replace("$", ""));
        double totalPrice = Double.parseDouble(elTotalPrice.getText().replace("$", ""));
        double tax = Double.parseDouble(elTax.getText().replace("$", ""));
       /* waitUntilElementIsVisible(driver, elDiscount);
        double discount = Double.parseDouble(elDiscount.getText().replace("$", "").replace("-", ""));*/
        double generalSubtotalPrice = 0;

        for (int itemIndex = 0; itemIndex < elsItemList.size(); itemIndex++) {
            double priceOfItem = Double.parseDouble(elsPriceList.get(itemIndex).getText().replace("$", ""));
            double subtotalPrice = Double.parseDouble(elsSubtotalPriceList.get(itemIndex).getText().replace("$", ""));
            int quantity = Integer.parseInt(elsQuantityInputList.get(itemIndex).getAttribute("value"));

            Assert.assertEquals(subtotalPrice, priceOfItem * quantity, "expected subtotal price doesn't match actual subtotal price");
            generalSubtotalPrice += subtotalPrice;
        }
        waitUntilElementIsVisible(driver, elCartTotals);
        Assert.assertEquals(subtotalPriceSummary, generalSubtotalPrice, "expected subtotal price doesn't match actual subtotal price");
        //Assert.assertEquals(totalPrice, subtotalPriceSummary + tax - discount, "expected total price doesn't match actual total price");
        Assert.assertEquals(totalPrice, subtotalPriceSummary + tax, "expected total price doesn't match actual total price");
        summaryTotalPrice = totalPrice;
    }
}
