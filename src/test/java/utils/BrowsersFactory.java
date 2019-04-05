package utils;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

class BrowsersFactory {

    public static class MyListener extends AbstractWebDriverEventListener {

        private Logger logger = LoggerFactory.getLogger(BrowsersFactory.class);

        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            logger.info("Обращение к элементу " + by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            logger.info("Найден элемент " + by);
        }

        @Override
        public void beforeClickOn(WebElement element, WebDriver driver) {
            logger.info("Клик по элементу " + element);
        }

        @Override
        public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
            logger.info("Изменено значение элемента " + element + "на" + Arrays.toString(keysToSend));
        }

        @Override
        public void afterSwitchToWindow(String windowName, WebDriver driver) {
            logger.info("Переход на станицу " + driver.getTitle());
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File file = new File("target", "sccreen-" + System.currentTimeMillis() + ".png");
            try {
                Files.copy(tmp, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.error(file.getAbsolutePath());
        }
    }

    static WebDriver buildDriver(String browserName) {
        switch (browserName) {

            case "chrome_invisible":
                ChromeOptions chromeInvisibleOpt = new ChromeOptions();
                chromeInvisibleOpt.addArguments("--disable-notifications");
                chromeInvisibleOpt.addArguments("--headless");
                return new ChromeDriver(chromeInvisibleOpt);

            case "firefox":
                //Disable login to console and redirect log to an external file
                System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "./src/test/java/firefox_logs/log");

                FirefoxOptions ffOpt = new FirefoxOptions();
                ffOpt.addPreference("dom.webnotifications.enabled", false);
                return new FirefoxDriver(ffOpt);

            case "opera":
                System.setProperty("webdriver.opera.driver", "C:\\DRIVERS\\operadriver\\operadriver.exe");
                System.setProperty("opera.binary", "C:\\Program Files (x86)\\Opera\\launcher.exe");
                return new OperaDriver();

            default:
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                return new ChromeDriver(options);
        }
    }
}
