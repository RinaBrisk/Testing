package tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utils.BaseRunner;
import utils.Helper;

import javax.xml.bind.Element;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TinkoffMobileTests extends BaseRunner {

    private static String URL_TARIFFS = "https://www.tinkoff.ru/mobile-operator/tariffs/";

    @Test
    public void switchingBetweenTabs() {
        // создаем wait на 10 секунд
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
        driver.findElement(By.xpath("//span[@class='MvnoRegionConfirmation__option_v9PfP']")).click();
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
        wait.until(d -> {
            String priceMoscowDef = d.findElement(By.xpath("//h3")).getText();
            driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]")).click();
            By listItems = By.xpath("//div[contains(@class,'MobileOperatorRegionsPopup__listParts_16aoL')]/div[2]/div");
            List<WebElement> elements = driver.findElements(listItems);
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
        By listItems = By.xpath("//div[contains(@class,'MobileOperatorRegionsPopup__listParts_16aoL')]/div[1]/div");
        List<WebElement> elements = driver.findElements(listItems);
        wait.until(d -> {
            boolean check = false;
            for (WebElement el : elements) {
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

    private void setMaxPackets() {
        Actions actionsInternet = new Actions(driver);
        actionsInternet.click(driver.findElement(By.xpath("//div[@class='ui-select_close ui-select_changed']" +
                "//span[text()='Интернет']/../../div[@class='ui-select__additional']"))).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Безлимитный интернет']/.."))).click();

        Actions actions = new Actions(driver);
        actions.click(driver.findElement(By.xpath("//div[@class='ui-select_close ui-select_changed']//span[text()='Звонки']/../../div[@class='ui-select__additional']"))).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Безлимитные минуты']/.."))).click();

        driver.findElement(By.xpath("//label[text()='Режим модема (499 \u20BD)']/../div")).click();
        driver.findElement(By.xpath("//label[text()='Безлимитные СМС (49 \u20BD)']/../div")).click();
    }

    @Test
    public void ActiveButton() {
        driver.get(URL_TARIFFS);
        //обнулить выпадающие списки
        driver.findElement(By.xpath("//div[contains(@class, 'Checkbox__container_AZX42 Checkbox__container_checked_3yg5S')])[1]")).click();
        driver.findElement(By.xpath("//div[contains(@class, 'Checkbox__container_AZX42 Checkbox__container_checked_3yg5S')])[2]")).click();
        driver.findElement(By.xpath("//div[contains(@class, 'LoaderRound__container_no-background_GvpfD')]")).click();
        Assert.assertEquals("Укажите ваше ФИО", driver.findElement(
                By.xpath("//div[contains(@class, 'ui-form__row ui-form__row_dropdownFIO')]//div[contains(@class, 'ui-form-field-error-message_ui-form')]")).getText());
    }
}
