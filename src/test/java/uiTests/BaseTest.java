package uiTests;

import lombok.extern.log4j.Log4j2;
import org.example.core.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

@Log4j2
public class BaseTest {
    protected static WebDriver driver;

    @BeforeSuite
    public void beforeAll() {
        driver = WebDriverFactory.getDriver();
    }

    @BeforeMethod
    public void testWasStartedLog(Method method) {
        log.info(String.format("Test %s was started", method.getName()));
    }

    @AfterMethod
    public void testFinishedLog(ITestResult iTestResult) {
        if (iTestResult.getStatus() == ITestResult.SUCCESS) {
            log.info(String.format("Test %s finished with result SUCCESS", iTestResult.getName()));
        } else if (iTestResult.getStatus() == ITestResult.FAILURE) {
            log.info(String.format("Test %s finished with result FAILURE", iTestResult.getName()));
        } else if (iTestResult.getStatus() == ITestResult.SKIP) {
            log.info(String.format("Test %s finished with result SKIP", iTestResult.getName()));
        }
    }

    @AfterSuite
    public void afterAll() {
        driver.quit();
    }
}
