package org.example.pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

import static org.example.utils.WaitUtils.waitUntilElementIsVisible;

public class CatalogSearchPage extends BasePage {
    private final String SEARCH_PAGE_URL = "https://magento2-demo.magebit.com/catalogsearch/result/";
    @Getter
    @FindBy(xpath = "//li[@class='item search']//strong")
    private WebElement elTitleBreadcrumb;
    @Getter
    @FindBy(xpath = "//span[@data-ui-id='page-title-wrapper']")
    private WebElement elTitle;
    @Getter
    @FindBy(xpath = "//strong[@class='product name product-item-name']/a")
    private List<WebElement> elsNameItemList;
    @FindBy(xpath = "//div[@class='message notice']//div")
    private WebElement elMessageNotice;

    public CatalogSearchPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public void open() {
        super.driver.get(SEARCH_PAGE_URL);
        ensureOpened();
    }

    @Override
    public void ensureOpened() {
        waitUntilElementIsVisible(driver, elTitle);
    }

    public void checkPageTitleMatchExpected(String item) {
        waitUntilElementIsVisible(driver, elTitle);
        Assert.assertTrue(elTitle.getText().toLowerCase().contains(item.toLowerCase()), "expected text on the title doesn't match actual text");
    }

    public void checkTitleBreadcrumbMatchExpected(String item) {
        waitUntilElementIsVisible(driver, elTitleBreadcrumb);
        Assert.assertTrue(elTitleBreadcrumb.getText().toLowerCase().contains(item.toLowerCase()), "expected text on the breadcrumb title doesn't match actual text");
    }

    public void checkResultItemsMatchExpected(String item) {
        if (item.length() < 3) {
            waitUntilElementIsVisible(driver, elMessageNotice);
            Assert.assertEquals(elMessageNotice.getText(), "Minimum Search query length is 3", "expected message doesn't match actual message");
        } else {
            if (elsNameItemList.size() != 0) {
                boolean isAllResultItemsMatchExpected = elsNameItemList.stream().allMatch(x -> x.getText().toLowerCase().contains(item.toLowerCase()));
                Assert.assertTrue(isAllResultItemsMatchExpected, "not all actual result items match expected items");
            } else {
                waitUntilElementIsVisible(driver, elMessageNotice);
                Assert.assertEquals(elMessageNotice.getText(), "Your search returned no results.", "expected message doesn't match actual message");
            }
        }
    }

    public void chooseItem() {
        if (elsNameItemList.size() != 0) {
            /*Random random = new Random();
            int itemIndex = random.nextInt(elsNameItemList.size());*/
            elsNameItemList.get(1).click();
        }
    }
}
