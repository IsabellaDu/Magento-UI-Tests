package org.example.pages;

import lombok.Getter;
import org.example.utils.ConfigProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

import static org.example.utils.WaitUtils.waitUntilElementIsClickable;
import static org.example.utils.WaitUtils.waitUntilElementIsVisible;

public class ProductPage extends BasePage {
    @Getter
    @FindBy(xpath = "//span[@data-ui-id='page-title-wrapper']")
    private WebElement elTitle;
    @Getter
    @FindBy(xpath = "//span[@class='counter-number']")
    private WebElement elCounterNumber;

    @FindBy(xpath = "//div[@class='swatch-attribute size']//div[@class='swatch-attribute-options clearfix']//div")
    private List<WebElement> elsSizeList;
    @FindBy(xpath = "//div[@id='super_attribute[157]-error']")
    private WebElement elSizeErrorMessage;

    @FindBy(xpath = "//div[@class='swatch-attribute color']//div[@class='swatch-attribute-options clearfix']//div")
    private List<WebElement> elsColorList;
    @FindBy(xpath = "//div[@id='super_attribute[93]-error']")
    private WebElement elColorErrorMessage;

    @FindBy(xpath = "//input[@id='qty']")
    private WebElement elQuantityInput;
    @FindBy(xpath = "//div[@id='qty-error']")
    private WebElement elQuantityErrorMessage;

    @FindBy(xpath = "//div[@id='product-options-wrapper']")
    private WebElement elOptionsWrapper;
    @Getter
    @FindBy(xpath = "//button[@id='product-addtocart-button']")
    private WebElement elAddToCartCta;
    @Getter
    @FindBy(xpath = "//div[@data-ui-id='message-success']//div")
    private WebElement elMessageSuccess;

    public ProductPage(WebDriver driver) {
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
        waitUntilElementIsVisible(driver, elTitle);
    }

    public void selectSize(String size) throws InterruptedException {
        Thread.sleep(2000);
        waitUntilElementIsClickable(driver, elsSizeList.get(0));
        for (WebElement elSizeCta : elsSizeList) {
            if (elSizeCta.getAttribute("data-option-label").equalsIgnoreCase(size)) {
                elSizeCta.click();
            }
        }
    }

    public void selectColor(String color) {
        waitUntilElementIsClickable(driver, elsColorList.get(0));
        for (WebElement elColorCta : elsColorList) {
            if (elColorCta.getAttribute("data-option-label").equalsIgnoreCase(color)) {
                elColorCta.click();
            }
        }
    }

    public void setQuantity(String quantity) {
        waitUntilElementIsVisible(driver, elQuantityInput);
        elQuantityInput.clear();
        elQuantityInput.sendKeys(quantity);
    }

    public void checkErrorMessagesWorkCorrectly(String size, String color, String quantity) {
        checkErrorMessageForList(size, elSizeErrorMessage, elsSizeList);
        checkErrorMessageForList(color, elColorErrorMessage, elsColorList);
        checkQuantityErrorMessage(quantity);
    }

    public void checkErrorMessageForList(String parameter, WebElement errorField, List<WebElement> list) {
        boolean noneMatchInList = list.stream().noneMatch(x -> x.getAttribute("data-option-label").equalsIgnoreCase(parameter));
        if (parameter.isEmpty() || noneMatchInList) {
            waitUntilElementIsVisible(driver, errorField);
            Assert.assertEquals(errorField.getText(), "This is a required field.", "expected message doesn't match actual message");
        }
    }

    public void checkQuantityErrorMessage(String quantity) {
        if (Integer.parseInt(quantity) < 1) {
            waitUntilElementIsVisible(driver, elQuantityErrorMessage);
            Assert.assertEquals(elQuantityErrorMessage.getText(), "Please enter a quantity greater than 0.", "expected message doesn't match actual message");
        }
    }

    public void checkMessageSuccessWorks() {
        waitUntilElementIsVisible(driver, elMessageSuccess);
        Assert.assertEquals(elMessageSuccess.getText(), String.format("You added %s to your shopping cart.", elTitle.getText()), "expected message doesn't match actual message");
    }

    public int getCounterNumber() {
        if (elCounterNumber.getText().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(elCounterNumber.getText());
        }
    }

    public void checkDoesQuantityOfItemsOnCartWorkCorrectly(int currentQuantity) {
        Assert.assertEquals(Integer.parseInt(elCounterNumber.getText()), currentQuantity, "expected number of items in the cart doesn't match actual number");
    }
}
