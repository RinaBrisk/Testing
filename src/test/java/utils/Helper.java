package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class Helper extends BaseRunner{

    public static void fillField(String xpath, String value){
        driver.findElement(By.xpath(xpath)).sendKeys(Keys.CONTROL, "a", "\b");
        driver.findElement(By.xpath(xpath)).sendKeys(value);
        driver.findElement(By.xpath(xpath)).sendKeys(Keys.ENTER);
    }
}
