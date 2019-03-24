import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public enum  BrowsersFactory {
    chrome {
        public WebDriver create() {
            ChromeOptions options = new ChromeOptions();
           // options.addArguments("--disable-notifications");
            return new ChromeDriver();
        }
    },
    firefox {
        public WebDriver create() {
            return new FirefoxDriver();
        }
    };

    public WebDriver create() {
        return null;
    }
}
