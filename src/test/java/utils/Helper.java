package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Helper extends BaseRunner{

    public static void fillField(String xpath, String value){
        driver.findElement(By.xpath(xpath)).sendKeys(Keys.CONTROL, "a", "\b");
        driver.findElement(By.xpath(xpath)).sendKeys(value);
        driver.findElement(By.xpath(xpath)).sendKeys(Keys.ENTER);
    }

    //универсальный xpath локатор, вернет все элементы, содержащие текст
    public static List<WebElement> xpathSearcherByText(String searchText) {
        String xpath = String.format("//*[contains(text(),'%s')]", searchText);
        return driver.findElements(By.xpath(xpath));
    }
}