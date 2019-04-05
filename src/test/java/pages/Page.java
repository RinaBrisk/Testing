package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Page {

    Logger logger = LoggerFactory.getLogger(Page.class);
    WebDriver driver;
    WebDriverWait wait;

    Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public boolean isLoadedByTitleContains(String substring) {
        wait.until(d -> d.getTitle().contains(substring));
        return true;
    }

    public void switchToWindow(String windowName) {
        wait.until(d -> {
            boolean check = false;
            for (String title : driver.getWindowHandles()) {
                driver.switchTo().window(title);
                check = d.getTitle().equals(windowName);
                if (check) break;
            }
            return check;
        });
    }

    public void switchToMainTab() {
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
    }

    public void closeCurrentTab() {
        driver.close();
        logger.info("Закрыта активная вкладка");
    }

    public void checkPageURL(String URL) {
        wait.until(d -> driver.getCurrentUrl().equals(URL));
    }

    public List<WebElement> xpathSearcherByText(String searchText) {
        String xpath = String.format("//*[contains(text(),'%s')]", searchText);
        return driver.findElements(By.xpath(xpath));
    }
}
