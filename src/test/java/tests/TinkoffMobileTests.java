package tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import utils.BaseRunner;

import java.time.Duration;
import java.util.List;

public class TinkoffMobileTests extends BaseRunner {

    @Test
    public void switchingBetweenTabs(){
        // создаем wait на 10 секунд
        driver.get("https://www.google.ru/");
        driver.findElement(By.xpath("//input[contains(@class, 'gLFyf gsfi')]")).sendKeys( "мобайл тинькофф" );
        driver.findElements(By.xpath("//ul[@role='listbox']"));

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
                        if (el.getText().equals("мобайл тинькофф тарифы")){
                            el.click();
                            break;
                        }
                    }
                    return d.getTitle().equals("мобайл тинькофф тарифы - Поиск в Google");
                });

        //поиск нужного сайта
        String tinkoffTariffsLink = "https://www.tinkoff.ru/mobile-operator/tariffs/";
        wait.until(d -> xpathSearcherByText(tinkoffTariffsLink).size() > 0);
        xpathSearcherByText(tinkoffTariffsLink).get(0).click();

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
                if(check){
                    driver.close();
                    break;
                }
            }
            return check;
        });

        //переключаемся к первой попавшейся вкладке (а у нас она теперь одна)
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
        wait.until(d -> driver.getCurrentUrl().equals("https://www.tinkoff.ru/mobile-operator/tariffs/"));
    }

    //универсальный xpath локатор, вернет все элементы, содержащие текст
    private List<WebElement> xpathSearcherByText(String searchText) {
        String xpath = String.format("//*[contains(text(),'%s')]", searchText);
        return driver.findElements(By.xpath(xpath));
    }
}
