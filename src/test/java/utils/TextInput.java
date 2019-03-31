package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TextInput extends BaseRunner {

    public String setText(String xpath, String value) {
        driver.findElement(By.xpath(xpath)).sendKeys(Keys.CONTROL, "a", "\b");
        driver.findElement(By.xpath(xpath)).sendKeys(value);
        driver.findElement(By.xpath(xpath)).sendKeys(Keys.ENTER);
        return getText(xpath);
    }

    public String getText(String xpath) {
        WebElement el = driver.findElement(By.xpath(xpath));
        return el.getAttribute("value");
    }
}

