package utils;

import org.openqa.selenium.By;

public class Button extends BaseRunner{

    public void click(String name){
         driver.findElement(By.xpath("//div[text()='" + name + "']/../..")).click();
    }
}

