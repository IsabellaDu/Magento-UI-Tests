package uiTests;

import io.qameta.allure.Description;
import org.example.core.data.DataProviderMethods;
import org.example.listeners.RetryListener;
import org.example.pages.CatalogSearchPage;
import org.example.pages.HomePage;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

public class CatalogSearchVerificationTest extends BaseTest {
    @Test(dataProvider = "dataForCanSearchItemTest",
            dataProviderClass = DataProviderMethods.class, retryAnalyzer = RetryListener.class)
    @Description("Test Description: on the Home Page check can search item and check results on the Catalog Search Page with all test data cases.")
    public void canSearchItem(String itemName) {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.getElSearchInput().sendKeys(itemName, Keys.ENTER);
        CatalogSearchPage catalogSearchPage = new CatalogSearchPage(driver);
        catalogSearchPage.checkTitleBreadcrumbMatchExpected(itemName);
        catalogSearchPage.checkPageTitleMatchExpected(itemName);
        catalogSearchPage.checkResultItemsMatchExpected(itemName);
    }
}
