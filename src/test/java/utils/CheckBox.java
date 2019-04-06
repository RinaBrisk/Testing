package utils;

import org.openqa.selenium.WebElement;

public class CheckBox {

    private WebElement checkBox;

    public CheckBox(WebElement checkBox){
        this.checkBox = checkBox;
    }

    public void click(){
        checkBox.click();
    }

    public boolean isSelected(){
        return checkBox.isSelected();
    }

    //TODO другие методы
}
