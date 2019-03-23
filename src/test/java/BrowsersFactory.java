import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public enum  BrowsersFactory {
    chrome {
        public WebDriver create() {
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
