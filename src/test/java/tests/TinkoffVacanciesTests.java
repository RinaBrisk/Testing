package tests;

import org.junit.*;

import org.openqa.selenium.*;
import utils.BaseRunner;
import utils.CheckBox;
import utils.Helper;
import utils.TextInput;

public class TinkoffVacanciesTests extends BaseRunner {

    @Test
    public void testEmptyValue() {
        driver.get("https://www.tinkoff.ru/career/vacancies/");
        driver.findElement(By.xpath("//input[@name='name']")).click();
        driver.findElement(By.xpath("//input[@name='birthday']")).click();
        driver.findElement(By.xpath("//input[@name='city']")).click();
        driver.findElement(By.xpath("//input[@name='email']")).click();
        driver.findElement(By.xpath("//input[@name='phone']")).click();
        driver.findElement(By.xpath("//div[@class='ui-checkbox__check']")).click();
        driver.findElement(By.xpath("//span[contains(@class, 'Button__content_3MlYx')]")).click();
        Assert.assertEquals("Поле обязательное", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row ui-form__row_default-error-view-visible')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());
        Assert.assertEquals("Поле обязательное", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row_date')]//div[contains(@class, 'ui-form-field-error-message')]")).getText());
        Assert.assertEquals("Поле обязательное", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row_dropdownRegion')]//div[contains(@class, 'ui-form-field-error-message')]")).getText());
        Assert.assertEquals("Поле обязательное", driver.findElement(
                By.xpath("//div[contains(@class, 'schema__email_uTUlf')]//div[contains(@class, 'ui-form-field-error-message')]")).getText());
        Assert.assertEquals("Поле обязательное", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row_tel')]//div[contains(@class, 'ui-form-field-error-message_ui-form')]")).getText());
        Assert.assertEquals("Поле обязательное", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row_uploadImage')]//div[contains(@class, 'ui-form-field-error-message')]")).getText());
        Assert.assertEquals("Поле обязательное", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row_checkbox')]//div[contains(@class, 'ui-form-field-error-message')]")).getText());
    }

    @Test
    public void testInvalidValue() {
        driver.get("https://www.tinkoff.ru/career/vacancies/");
        TextInput textInput = new TextInput();
        textInput.setText("//input[@name='name']","85гаг8г858");
        Assert.assertEquals("Допустимо использовать только буквы русского алфавита и дефис", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row ui-form__row_default-error-view-visible')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());

        textInput.setText("//input[@name='name']","kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" +
                                                        "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" );
        Assert.assertEquals("Максимальное количество символов – 133", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row ui-form__row_default-error-view-visible')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());

        textInput.setText("//input[@name='name']","Мария" );
        Assert.assertEquals("Необходимо ввести фамилию и имя через пробел",driver.findElement(
                  By.xpath("//div[contains(@class, 'ui-form__row ui-form__row_default-error-view-visible')]" +
                          "//div[contains(@class, 'ui-form-field-error-message')]")).getText());

        textInput.setText("//input[@name='birthday']","00.49.4904");
        Assert.assertEquals("Поле заполнено некорректно", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row_date')]//div[contains(@class, 'ui-form-field-error-message')]")).getText());

        textInput.setText("//input[@name='email']", "ок49к4шк94ш");
        Assert.assertEquals("Введите корректный адрес эл. почты", driver.findElement(
                By.xpath("//div[contains(@class, 'schema__email_uTUlf')]//div[contains(@class, 'ui-form-field-error-message')]")).getText());

        textInput.setText("//input[@name='phone']", "+7(093) 409-49-99");
        Assert.assertEquals("Код города/оператора должен начинаться с цифры 3, 4, 5, 6, 8, 9", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row_tel')]//div[contains(@class, 'ui-form-field-error-message_ui-form')]")).getText());
    }

}



