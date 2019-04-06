package utils;

import org.openqa.selenium.WebElement;

public class Button {

    private WebElement button;

    public Button(WebElement button){
        this.button = button;
    }

    public void click(){
         button.click();
    }

    public boolean isEnabled() {
        return button.isEnabled();
    }

    //TODO другие методы
}

