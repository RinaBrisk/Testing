package utils;

import org.openqa.selenium.WebElement;

public class Select{

    public boolean valueChoice(WebElement selectOpen, WebElement option, WebElement selectTitle) {
        selectOpen.click();
        option.click();
        return (getCurrentValue(selectTitle).equals(option.getText()));
    }

    public String getCurrentValue(WebElement selectTitle) {
        return selectTitle.getText();
    }

    //TODO другие методы
}
