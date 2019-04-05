package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Button;
import utils.CheckBox;
import utils.TextInput;

public class TinkoffVacanciesPage extends Page {

    private static final String URL = "https://www.tinkoff.ru/career/vacancies/";
    private static final String TITLE = "Вакансии";

    public TinkoffVacanciesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[contains(@class,'ui-input__column')]/input[@name='name']")
    WebElement name;

    @FindBy(xpath = "//*[contains(@class,'ui-input__column')]/input[@name='birthday']")
    WebElement birthday;

    @FindBy(xpath = "//*[contains(@class,'ui-input__column')]/input[@name='city']")
    WebElement city;

    @FindBy(xpath = "//*[contains(@class,'ui-input__column')]/input[@name='email']")
    WebElement email;

    @FindBy(xpath = "//*[contains(@class,'ui-input__column')]/input[@name='phone']")
    WebElement phone;

    @FindBy(xpath = "//div[@class='ui-checkbox__check']")
    WebElement termsOfUse;

    @FindBy(xpath = "//span[contains(@class, 'Button__content_3MlYx')]")
    WebElement sendBtn;

    @FindBy(xpath = "//div[contains(@class, 'ui-suggest')]/following-sibling::div")
    WebElement nameErrorMsg;

    @FindBy(xpath = "//div[contains(@class, 'ui-form__row_date')]//div[contains(@class, 'ui-form-field-error-message')]")
    WebElement birthdayErrorMsg;

    @FindBy(xpath = "//div[contains(@class, 'ui-form__row_dropdownRegion')]//div[contains(@class, 'ui-form-field-error-message')]")
    WebElement cityErrorMsg;

    @FindBy(xpath = "//div[contains(@class, 'schema__email_uTUlf')]//div[contains(@class, 'ui-form-field-error-message')]")
    WebElement emailErrorMsg;

    @FindBy(xpath = "//div[contains(@class, 'ui-form__row_tel')]//div[contains(@class, 'ui-form-field-error-message_ui-form')]")
    WebElement phoneErrorMsg;

    @FindBy(xpath = "//div[contains(@class, 'ui-form__row_uploadImage')]//div[contains(@class, 'ui-form-field-error-message')]")
    WebElement CVErrorMsg;

    @FindBy(xpath = "//div[contains(@class, 'ui-form__row_checkbox')]//div[contains(@class, 'ui-form-field-error-message')]")
    WebElement termsOfUseErrorMsg;

    public void open() {
        driver.navigate().to(URL);
        isLoadedByTitleContains(TITLE);
    }

    public void typeNameField(String value) {
        wait.until(d -> {
            TextInput.setText(name, value);
            return true;
        });
    }

    public void typeBirthdayField(String value) {
        wait.until(d -> {
            TextInput.setText(birthday, value);
            return true;
        });
    }

    public void typeCityField(String value) {
        wait.until(d -> {
            TextInput.setText(city, value);
            return true;
        });
    }

    public void typeEmailField(String value) {
        wait.until(d -> {
            TextInput.setText(email, value);
            return true;
        });
    }

    public void typePhoneField(String value) {
        wait.until(d -> {
            TextInput.setText(phone, value);
            return true;
        });
    }

    public void clickTermsOfUse(){
        CheckBox.click(termsOfUse);
    }

    public void clickSendBtn(){
        Button.click(sendBtn);
    }

    public void checkNameErrorField(String error){
        Assert.assertEquals(error, nameErrorMsg.getText());
    }

    public void checkBirthdayErrorField(String error){
        Assert.assertEquals(error, birthdayErrorMsg.getText());
    }

    public void checkCityErrorField(String error){
        Assert.assertEquals(error, cityErrorMsg.getText());
    }

    public void checkEmailErrorField(String error){
        Assert.assertEquals(error, emailErrorMsg.getText());
    }

    public void checkPhoneErrorField(String error){
        Assert.assertEquals(error, phoneErrorMsg.getText());
    }

    public void checkCVErrorField(String error){
        Assert.assertEquals(error,CVErrorMsg .getText());
    }

    public void checkTermsOfUseErrorField(String error){
        Assert.assertEquals(error, termsOfUseErrorMsg.getText());
    }
}
