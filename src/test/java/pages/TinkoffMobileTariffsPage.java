package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Button;
import utils.CheckBox;
import utils.Select;
import utils.TextInput;


import java.util.List;

public class TinkoffMobileTariffsPage extends Page{

    private static final String URL = "https://www.tinkoff.ru/mobile-operator/tariffs/";
    private static final String TITLE = "Тарифы Тинькофф Мобайла";

    public TinkoffMobileTariffsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public static String getURL() {
        return URL;
    }
    public static String getTITLE() {
        return TITLE;
    }

    @FindBy(xpath = "//h3")
    WebElement price;

    @FindBy(xpath = "//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]")
    WebElement region;

    @FindBy(xpath = "//div[contains(@class,'MobileOperatorRegionsPopup__listParts_16aoL')]//div[contains(@class,'region')]")
    List<WebElement> allRegions;

    @FindBy(xpath = "//span[@class='MvnoRegionConfirmation__option_v9PfP']")
    WebElement confirmMoscowRegion;

    @FindBy(xpath = "//div[@class='ui-input__column']//span[text()='Фамилия, имя и отчество']/ancestor::div/input")
    WebElement fullName;

    @FindBy(xpath = "//div[@class='ui-input__column']//span[text()='Контактный телефон']/ancestor::div/input[@name='phone_mobile']")
    WebElement phone;

    @FindBy(xpath = "//div[text()='Заказать сим-карту']//ancestor::button")
    WebElement orderSIM;

    @FindBy(xpath = "//div[contains(@class, 'UIAppointment__container_3A8ha UIAppointment__container_highlighted_3lFo8')]")
    WebElement scheduleAppointment;

    public void open() {
        driver.navigate().to(URL);
        isLoadedByTitleContains(TITLE);
    }

    public void confirmMoscowRegion(){
        confirmMoscowRegion.click();
    }

    public void checkCurrentRegion(String region){
        wait.until(d -> this.region
                        .getText()
                        .equals(region));
    }

    public void changeRegion(String newRegion){
        region.click();
        wait.until(d -> {
            boolean check = false;
            for (WebElement el : allRegions) {
                if (el.getText().equals(newRegion)) {
                    logger.info("Выбранный район: " + el.getText());
                    el.click();
                    check = true;
                    break;
                }
            }
            return check;
        });
    }

    public String getCurrentPrice(){
        assert price != null;
        logger.info(price.getText());
        return price.getText();
    }

    public void typeNameField(String value){
        wait.until(d -> {
            boolean check = false;
            TextInput name_ti = new TextInput(fullName);
            if(name_ti.setText(value)) check = true;
            return check;
        });
    }

    public void typePhoneField(String value){
        wait.until(d -> {
            TextInput phone_ti = new TextInput(phone);
            phone_ti.setText(value);
            return true;
        });
    }

    public void orderSIMClick(){
        Button orderSIM_btn = new Button(orderSIM);
        wait.until(d -> orderSIM_btn.isEnabled());
        orderSIM_btn.click();
    }

    public void checkScheduleAppointmentIsDisplayed(){
        wait.until(d -> scheduleAppointment.isDisplayed());
    }

    private WebElement getSelectElementForGetTitle(String selectName){
        return driver.findElement(By.xpath("//span[text()='" + selectName + "']/parent::div//span[contains(@class,'flex-text')]"));
    }

    private WebElement getSelectElementForOpen(String selectName){
        return driver.findElement(By.xpath("//span[text()='" + selectName + "']/parent::div/following-sibling::div"));
    }

    private WebElement getSelectOptionElement(String optionName){
        return driver.findElement(By.xpath("//span[text()='" + optionName + "']"));
    }

    private WebElement getCheckBoxElement(String checkBoxName){
        return driver.findElement(By.xpath("//div[contains(@class,'CheckboxSet')]//label[contains(text(),'" + checkBoxName + "')]"));
    }

    public void setMaximumServices(){
        Select internet = new Select();
        Select calls = new Select();
        wait.until(d -> internet.valueChoice(getSelectElementForOpen("Интернет"),
                                             getSelectOptionElement("Безлимитный интернет"),
                                             getSelectElementForGetTitle("Интернет")));
        wait.until(d -> calls.valueChoice(getSelectElementForOpen("Звонки"),
                                          getSelectOptionElement("Безлимитные минуты"),
                                          getSelectElementForGetTitle("Звонки")));

        CheckBox modemMode = new CheckBox(getCheckBoxElement("Режим модема"));
        CheckBox unlimitedSMS = new CheckBox(getCheckBoxElement("Безлимитные СМС"));
        modemMode.click();
        unlimitedSMS.click();
    }

    public void disableAllServices(){
        Select internet = new Select();
        Select calls = new Select();
        wait.until(d -> internet.valueChoice(getSelectElementForOpen("Интернет"),
                                             getSelectOptionElement("0 ГБ"),
                                             getSelectElementForGetTitle("Интернет")));
        wait.until(d -> calls.valueChoice(getSelectElementForOpen("Звонки"),
                                          getSelectOptionElement("0 минут"),
                                          getSelectElementForGetTitle("Звонки")));
        CheckBox messenger = new CheckBox(getCheckBoxElement("Мессенджеры"));
        CheckBox socialNetwork = new CheckBox(getCheckBoxElement("Социальные сети"));
        messenger.click();
        socialNetwork.click();
    }

    public void checkPriceIsDifferent(String price1, String price2){
        wait.until(d -> (!price1.equals(price2)));
    }

    public void checkPriceIsEquals(String price1, String price2){
        wait.until(d -> (price1.equals(price2)));
    }

}
