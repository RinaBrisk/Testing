package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CheckBox;
import utils.Select;

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

    @FindBy(xpath = "//span[text()='Интернет']/parent::div/following-sibling::div")
    WebElement internetSelectOpen;

    @FindBy(xpath = "//span[text()='Интернет']/parent::div//span[contains(@class,'flex-text')]")
    WebElement internetSelectStatus;

    @FindBy(xpath = "//span[text()='Звонки']/parent::div/following-sibling::div")
    WebElement callSelectOpen;

    @FindBy(xpath = "//span[text()='Звонки']/parent::div//span[contains(@class,'flex-text')]")
    WebElement callSelectStatus;

    @FindBy(xpath = "//span[text()='Безлимитный интернет']")
    WebElement unlimitedInternetSelectPosition;

    @FindBy(xpath = "//span[text()='Безлимитные минуты']")
    WebElement unlimitedMinutesSelectPosition;

    @FindBy(xpath = "//div[contains(@class,'CheckboxSet')]//label[contains(text(),'Режим модема')]")
    WebElement modemModeCheckBox;

    @FindBy(xpath = "//div[contains(@class,'CheckboxSet')]//label[contains(text(),'Безлимитные СМС')]")
    WebElement unlimitedSMSCheckBox;

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

    public void setMaximumServices(){
        wait.until(d -> Select.valueChoice(internetSelectStatus, internetSelectOpen, unlimitedInternetSelectPosition));
        wait.until(d -> Select.valueChoice(callSelectStatus, callSelectOpen, unlimitedMinutesSelectPosition));

        CheckBox.click(modemModeCheckBox);
        CheckBox.click(unlimitedSMSCheckBox);
    }

    public void checkPriceIsDifferent(String price1, String price2){
        wait.until(d -> (!price1.equals(price2)));
    }

    public void checkPriceIsEquals(String price1, String price2){
        wait.until(d -> (price1.equals(price2)));
    }


}
