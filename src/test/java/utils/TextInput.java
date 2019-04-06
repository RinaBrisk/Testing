package utils;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TextInput{

    private WebElement textInput;

    public TextInput(WebElement textInput){
        this.textInput = textInput;
    }

    public boolean setText(String value) {
        textInput.sendKeys(Keys.CONTROL, "a", "\b");
        textInput.sendKeys(value);
        textInput.sendKeys(Keys.ENTER);
        return getText().equals(value);
    }

    public String getText()
    {
        return textInput.getAttribute("value");
    }

    //TODO другие методы
}

