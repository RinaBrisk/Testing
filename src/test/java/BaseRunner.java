import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BaseRunner {

    private static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>();
    private String browserName = System.getProperty("browser");
    WebDriver driver;
    String baseUrl;

    @Before
    public void setUp(){
        if (threadLocal.get() != null) {
            driver = threadLocal.get();
        } else {
            driver = getDriver();
            threadLocal.set(driver);
        }
        driver.manage().window().maximize();
        baseUrl = "https://www.tinkoff.ru/career/vacancies/";
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
            browserName = "firefox";
        }
        return BrowsersFactory.valueOf(browserName).create();
    }

    @After
    public void tearDown(){
  //      driver.quit();
    }
}
