package uiTests;

import io.qameta.allure.Description;
import org.example.core.data.DataProviderMethods;
import org.example.listeners.RetryListener;
import org.example.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.example.utils.WaitUtils.*;

public class AddToCartAndCheckoutVerificationTest extends BaseTest {
    int currentQuantity;

    @Test(dataProvider = "dataForAddToCartTest",
            dataProviderClass = DataProviderMethods.class, retryAnalyzer = RetryListener.class)
    @Description("Test Description: on the Catalog Search Page choose item and on the Product Page check can add to cart with all test data cases.")
    public void canAddToCartItem(
            String size, String color, String quantity) throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.setSearchInput("bra");
        CatalogSearchPage catalogSearchPage = new CatalogSearchPage(driver);
        catalogSearchPage.ensureOpened();
        catalogSearchPage.chooseItem();

        ProductPage productPage = new ProductPage(driver);
        productPage.ensureOpened();
        Assert.assertTrue(productPage.getElTitle().getText().toLowerCase().contains("bra".toLowerCase()), "expected text on the title doesn't match actual text");
        productPage.selectSize(size);
        productPage.selectColor(color);
        productPage.setQuantity(quantity);
        productPage.getElAddToCartCta().click();
        productPage.checkErrorMessagesWorkCorrectly(size, color, quantity);
        currentQuantity = Integer.parseInt(quantity);
    }

    double summaryTotalPrice;
    double shippingMethodPrice;
    @Test(dependsOnMethods = {"canAddToCartItem"}, retryAnalyzer = RetryListener.class)
    @Description("Test Description: on the Product Page check is success message displayed.")
    public void isMessageSuccessDisplayed() {
        //Perform actions to initiate checkout process from cart page
        ProductPage productPage = new ProductPage(driver);
        productPage.checkMessageSuccessWorks();
        productPage.checkDoesQuantityOfItemsOnCartWorkCorrectly(currentQuantity);
    }

    @Test(dependsOnMethods = {"canAddToCartItem","isMessageSuccessDisplayed"}, dataProvider = "dataForCheckoutTest",
            dataProviderClass = DataProviderMethods.class, retryAnalyzer = RetryListener.class)
    @Description("Test Description: on the CheckOut Page check items and total price,on the Shipping Address Page check can set shipping address with all test data cases.")
    public void canCheckout(String email, String firstName, String lastName, String company,
                            String streetAddress, String countryCode, String province,
                            String city, String postalCode, String phoneNumber, String shippingMethod) {

        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.getElShowCartCta().click();

        //Assuming that the correct total cost is displayed
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        waitUntilElementIsVisible(driver, checkOutPage.getElTotalPrice());
        checkOutPage.checkIsTotalPriceCorrect();

        summaryTotalPrice = checkOutPage.getSummaryTotalPrice();

        waitUntilElementIsClickable(driver, checkOutPage.getElCheckoutCta());
        checkOutPage.getElCheckoutCta().click();

        //Verify that checkout process is initiated properly
        ShippingAddressPage shippingAddressPage = new ShippingAddressPage(driver);
        shippingAddressPage.ensureOpened();
        waitUntilUrlIsLoaded(driver, "/checkout/");

        shippingAddressPage.setEmail(email);
        shippingAddressPage.setFirstName(firstName);
        shippingAddressPage.setLastName(lastName);
        shippingAddressPage.setCompany(company);
        shippingAddressPage.setStreetAddress(streetAddress);
        shippingAddressPage.setCountryAndProvince(countryCode, province);
        shippingAddressPage.setCity(city);
        shippingAddressPage.setPostalCode(postalCode);
        shippingAddressPage.setPhoneNumber(phoneNumber);
        shippingAddressPage.setShippingMethod(shippingMethod);

        shippingMethodPrice = shippingAddressPage.getShippingMethodPrice();

        shippingAddressPage.getElNextCta().click();
        shippingAddressPage.checkErrorMessagesWorkCorrectly(countryCode, province);
    }

    @Test(dependsOnMethods = {"canAddToCartItem","isMessageSuccessDisplayed", "canCheckout"}, retryAnalyzer = RetryListener.class)
    @Description("Test Description: on the Payment Page check can place order.")
    public void canPlaceOrder() {
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.ensureOpened();
        paymentPage.checkOrderSummary(summaryTotalPrice, shippingMethodPrice);
        waitUntilElementIsClickable(driver, paymentPage.getElPlaceOrderCta());
        paymentPage.getElPlaceOrderCta().click();
        paymentPage.isSuccessfulCheckout();
    }
}
