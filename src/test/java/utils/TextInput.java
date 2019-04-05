package utils;

import app.Application;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TextInput extends Application {

    public static void setText(WebElement element, String value) {
        element.sendKeys(Keys.CONTROL, "a", "\b");
        element.sendKeys(value);
        element.sendKeys(Keys.ENTER);
    }

    public static String getText(WebElement element)
    {
        return element.getAttribute("value");
    }

    //TODO другие методы
}

