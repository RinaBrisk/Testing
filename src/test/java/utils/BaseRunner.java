package utils;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseRunner {

    private static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>();
    private String browserName = "chrome";
    protected static WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void start() {
        threadLocal = new ThreadLocal<>();
        driver = new EventFiringWebDriver(getDriver());
        ((EventFiringWebDriver) driver).register(new BrowsersFactory.MyListener());
        threadLocal.set(driver);
        wait = new WebDriverWait(driver, 15);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private WebDriver getDriver() {
        return BrowsersFactory.buildDriver(browserName);
    }
}
