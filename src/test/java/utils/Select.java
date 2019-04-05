package utils;

import app.Application;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Select extends Application {

    public static boolean valueChoice(WebElement listStatus, WebElement listOpen, WebElement element) {
        Actions actionsInternet = new Actions(driver);
        actionsInternet
                .click(listOpen)
                .click(element)
                .perform();
        return (getCurrentValue(listStatus).equals(element.getText()));
    }

    public static String getCurrentValue(WebElement list) {
        return list.getText();
    }

    //TODO другие методы
}
