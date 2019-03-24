import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;

public enum BrowsersFactory {
    chrome {
        public WebDriver create() {
            updateProperty("chrome");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            return new ChromeDriver();
        }
    },
    firefox {
        public WebDriver create() {
            updateProperty("firefox");
            System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "./firefox_logs");
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("dom.webnotifications.enabled", false);
            return new FirefoxDriver();
        }
    },
    opera {
        public WebDriver create() {
            updateProperty("opera");
            System.setProperty("webdriver.opera.driver","C:\\DRIVERS\\operadriver\\operadriver.exe");
            System.setProperty("opera.binary","C:\\Program Files (x86)\\Opera\\launcher.exe");
            return new OperaDriver();
        }
    };


    public WebDriver create() {
        return null;
    }

    void updateProperty(String browserName) {
        System.out.println(String.format("\nstarting %s-browser......", browserName));
        if (System.getProperty("browser") == null) System.setProperty("browser", browserName);
    }
}
