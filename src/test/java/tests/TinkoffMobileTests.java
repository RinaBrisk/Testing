package tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.BaseRunner;
import utils.Helper;
import utils.Select;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TinkoffMobileTests extends BaseRunner {

    private static String URL_TARIFFS = "https://www.tinkoff.ru/mobile-operator/tariffs/";

    @Test
    public void switchingBetweenTabs() {
        // создаем wait на 15 секунд
        driver.get("https://www.google.ru/");
        driver.findElement(By.xpath("//input[contains(@class, 'gLFyf gsfi')]")).sendKeys("мобайл тинькофф");
        wait
                .ignoring(StaleElementReferenceException.class)
                .withMessage("Что-то пошло не так...")
                .pollingEvery(Duration.ofMillis(500))
                .until(d -> {
                    // список вариаций поисковой выдачи
                    By listItems = By.xpath("//ul[@role='listbox']/li[@role='presentation' and .//*[@role='option']]");
                    List<WebElement> elements = driver.findElements(listItems);
                    for (WebElement el : elements) {
                        System.out.println(el.getText());
                        if (el.getText().equals("мобайл тинькофф тарифы")) {
                            el.click();
                            break;
                        }
                    }
                    return d.getTitle().equals("мобайл тинькофф тарифы - Поиск в Google");
                });

        //поиск нужного сайта
        wait.until(d -> Helper.xpathSearcherByText(URL_TARIFFS).size() > 0);
        Helper.xpathSearcherByText(URL_TARIFFS).get(0).click();

        //сайт открывается в новом окне, явно переключаемся к окну с заголовком  "Тарифы Тинькофф Мобайла"
        wait.until(d -> {
            boolean check = false;
            for (String title : driver.getWindowHandles()) {
                driver.switchTo().window(title);
                System.out.println(d.getTitle());
                check = d.getTitle().equals("Тарифы Тинькофф Мобайла");
            }
            return check;
        });

        //закрываем вкладку с поиском
        wait.until(d -> {
            boolean check = false;
            for (String title : driver.getWindowHandles()) {
                driver.switchTo().window(title);
                check = d.getTitle().equals("мобайл тинькофф тарифы - Поиск в Google");
                if (check) {
                    driver.close();
                    break;
                }
            }
            return check;
        });

        //переключаемся к первой попавшейся вкладке (а у нас она теперь одна)
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
        wait.until(d -> driver.getCurrentUrl().equals(URL_TARIFFS));
    }

    @Test
    public void changeOfRegion() {
        driver.get(URL_TARIFFS);
        wait.until(d -> d.findElement(By.xpath("//span[@class='MvnoRegionConfirmation__option_v9PfP']"))).click();
        wait.until(d ->
                d.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]"))
                        .getText()
                        .equals("Москва и Московская область"));

        wait.until(d -> {
            boolean check = false;
            d.get(URL_TARIFFS);
            if (d.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]"))
                    .getText()
                    .equals("Москва и Московская область")) check = true;
            return check;
        });

        //с дефолтными пакетами сумма не равна
        String priceMoscowDef = driver.findElement(By.xpath("//h3")).getText();
        driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]")).click();
        By listItems = By.xpath("//div[contains(@class,'MobileOperatorRegionsPopup__listParts_16aoL')]/div[2]/div");
        List<WebElement> elements = driver.findElements(listItems);
        wait.until(d -> {
            for (WebElement el : elements) {
                System.out.println(el.getText());
                if (el.getText().equals("Краснодарский кр.")) {
                    el.click();
                    break;
                }
            }
            String priceKrasnodarDef = d.findElement(By.xpath("//h3")).getText();
            boolean check = false;
            if (!priceMoscowDef.equals(priceKrasnodarDef)) check = true;
            return check;
        });

        //c максимальными пакетами сумма равна
        setMaxPackets();
        String priceKrasnodarMax = driver.findElement(By.xpath("//h3")).getText();

        driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]")).click();
        listItems = By.xpath("//div[contains(@class,'MobileOperatorRegionsPopup__listParts_16aoL')]/div[1]/div");
        List<WebElement> regions = driver.findElements(listItems);
        wait.until(d -> {
            boolean check = false;
            for (WebElement el : regions) {
                if (el.getText().equals("Москва и Московская обл.")) {
                    el.click();
                    check = true;
                    break;
                }
            }
            return check;
        });
        setMaxPackets();
        String priceMoscowMax = driver.findElement(By.xpath("//h3")).getText();
        wait.until(d -> (priceMoscowMax.equals(priceKrasnodarMax)));
    }

    @Test
    public void activeButton() {
        driver.get(URL_TARIFFS);
        driver.manage().timeouts().setScriptTimeout(4, TimeUnit.SECONDS);
        Select select = new Select();
        select.valueChoice(driver, "Интернет", "0 ГБ");
        select.valueChoice(driver, "Звонки", "0 минут");

        driver.findElement(By.xpath("//label[text()='Мессенджеры (59 \u20BD)']/../div")).click();
        driver.findElement(By.xpath("//label[text()='Социальные сети (59 \u20BD)']/../div")).click();

        String price = driver.findElement(By.xpath("//h3")).getText();
        wait.until(d -> price.equals("Общая цена: 0 \u20BD"));

        driver.findElement(By.xpath("//div[contains(@class,'LoaderRound__container_coverParent_2-_fi')]")).click();
        Assert.assertEquals("Укажите ваше ФИО", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row_dropdownFIO')]//div[contains(@class, 'ui-form-field-error-message')]")).getText());
    }

    private void setMaxPackets() {
        Select select = new Select();
        if(!select.valueChoice(driver, "Интернет", "Безлимитный интернет")) {
            select.valueChoice(driver, "Интернет", "Безлимитный интернет");
        }
        select.valueChoice(driver, "Звонки", "Безлимитные минуты");

        driver.findElement(By.xpath("//label[text()='Режим модема (499 \u20BD)']/../div")).click();
        driver.findElement(By.xpath("//label[text()='Безлимитные СМС (49 \u20BD)']/../div")).click();
    }
}
