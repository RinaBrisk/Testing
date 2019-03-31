package utils;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseRunner {

    private static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>();
    private String browserName = System.getProperty("browser");
    protected static WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void setUp(){
        if (threadLocal.get() != null) {
            driver = threadLocal.get();
        } else {
            driver = getDriver();
            threadLocal.set(driver);
        }
        wait = new WebDriverWait(driver, 15);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            driver.quit();
            driver = null;
        }));
    }

    private WebDriver getDriver() {
        try {
            BrowsersFactory.valueOf(System.getProperty("browser"));
        } catch (NullPointerException | IllegalArgumentException e) {
            browserName = "chrome";
        }
        return BrowsersFactory.valueOf(browserName).create();
    }
}
