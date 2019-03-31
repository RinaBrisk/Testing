package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckBox {

    public void doSelectedOrDeselected(WebDriver driver, String checkboxName){
        driver.findElement(By.xpath("//label[text()='" + checkboxName + "']/../div")).click();
    }

    public boolean getStatus(WebDriver driver, String checkboxName){
        return driver.findElement(By.xpath("//label[text()='" + checkboxName + "'/..//input")).isSelected();
    }
}
