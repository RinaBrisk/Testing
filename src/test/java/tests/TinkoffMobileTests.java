package tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TinkoffMobileTests extends BaseRunner {

    private static String URL_TARIFFS = "https://www.tinkoff.ru/mobile-operator/tariffs/";
    private Logger logger = LoggerFactory.getLogger(TinkoffMobileTests.class);

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
                        if (el.getText().equals("мобайл тинькофф тарифы")) {
                            logger.info("Поиск по запросу: " + el.getText());
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
                    logger.info("Закрываем страницу с заголовком: " + d.getTitle());
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
        logger.info("Стоимость услуг для Москвы: " + priceMoscowDef);
        driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]")).click();
        By listItems = By.xpath("//div[contains(@class,'MobileOperatorRegionsPopup__listParts_16aoL')]/div[2]/div");
        List<WebElement> elements = driver.findElements(listItems);
        wait.until(d -> {
            for (WebElement el : elements) {
                if (el.getText().equals("Краснодарский кр.")) {
                    logger.info("Выбранный район: " + el.getText());
                    el.click();
                    break;
                }
            }
            String priceKrasnodarDef = d.findElement(By.xpath("//h3")).getText();
            logger.info("Стоимость услуг для Краснодарского края: " + priceKrasnodarDef);
            boolean check = false;
            if (!priceMoscowDef.equals(priceKrasnodarDef)) check = true;
            return check;
        });

        //c максимальными пакетами сумма равна
        setMaxPackets();
        String priceKrasnodarMax = driver.findElement(By.xpath("//h3")).getText();
        logger.info("Стоимость услуг для Краснодарского края: " + priceKrasnodarMax);

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
        logger.info("Стоимость услуг для Москвы: " + priceMoscowMax);
        wait.until(d -> (priceMoscowMax.equals(priceKrasnodarMax)));
    }

    @Test
    public void activeButton() {
        driver.get(URL_TARIFFS);
        driver.manage().timeouts().setScriptTimeout(4, TimeUnit.SECONDS);
        Select select = new Select();
        wait.until(d -> select.valueChoice("Интернет", "0 ГБ").equals("0 ГБ"));
        wait.until(d -> select.valueChoice( "Звонки", "0 минут").equals("0 минут"));

        CheckBox checkBox = new CheckBox();
        checkBox.click("Мессенджеры (59 ₽)");
        checkBox.click("Социальные сети (59 ₽)");

        String price = driver.findElement(By.xpath("//h3")).getText();
        logger.info(price);
        wait.until(d -> price.equals("Общая цена: 0 ₽"));

        TextInput textInput = new TextInput();
        wait.until(d -> textInput.setText("//div[@class='ui-input__column']//span[text()='Фамилия, имя и отчество']/parent::span/parent::div/input", "Сергеева Рина").equals("Сергеева Рина"));
        wait.until(d -> textInput.setText("//div[@class='ui-input__column']//span[text()='Контактный телефон']/parent::span/parent::div/input[@name='phone_mobile']", "9009090909").equals("+7(900) 909-09-09"));

        Button button = new Button();
        button.click("Заказать сим-карту");
        wait.until(d -> {
            boolean check = false;
            WebElement el = driver.findElement(By.xpath("//div[contains(@class, 'UIAppointment__container_3A8ha UIAppointment__container_highlighted_3lFo8')]"));
            if(el.isDisplayed()) check = true;
            return check;
        });
    }

    private void setMaxPackets() {
        Select select = new Select();
        wait.until(d -> select.valueChoice("Интернет", "Безлимитный интернет").equals("Безлимитный интернет"));
        wait.until(d -> select.valueChoice("Звонки", "Безлимитные минуты").equals("Безлимитные минуты"));

        CheckBox checkBox = new CheckBox();
        checkBox.click("Режим модема (499 ₽)");
        checkBox.click("Безлимитные СМС (49 ₽)");
    }
}
