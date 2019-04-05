package app;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GooglePage;
import pages.TinkoffMobileTariffsPage;
import pages.TinkoffVacanciesPage;
import test.BrowsersFactory;
import java.util.concurrent.TimeUnit;

public class Application {

    private String browserName = "chrome";
    public static WebDriver driver;
    public WebDriverWait wait;
    private TinkoffVacanciesPage vacancies;
    private TinkoffMobileTariffsPage mobileTariffs;
    private GooglePage google;

    public TinkoffVacanciesPage getVacancies() {
        return vacancies;
    }

    public TinkoffMobileTariffsPage getMobileTariffs() {
        return mobileTariffs;
    }

    public GooglePage getGoogle() {
        return google;
    }

    public Application() {
        driver = new EventFiringWebDriver(getDriver());
        ((EventFiringWebDriver) driver).register(new BrowsersFactory.MyListener());
        wait = new WebDriverWait(driver, 15);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        //pages
        vacancies = new TinkoffVacanciesPage(driver);
        mobileTariffs = new TinkoffMobileTariffsPage(driver);
        google = new GooglePage(driver);
    }

    public void quit() {
        driver.quit();
        driver = null;
    }

//    public void confirmMoscowRegion(){
//        wait.until(d -> d.findElement(By.xpath("//span[@class='MvnoRegionConfirmation__option_v9PfP']"))).click();
//    }
//
//    public void selectRegion(String region){
//
//        wait.until(d ->
//                d.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]"))
//                        .getText()
//                        .equals("Москва и Московская область"));
//
//    }
//
//    public void changeOfRegion(String region){
//        wait.until(d -> {
//            boolean check = false;
//            d.get(URL_TARIFFS);
//            if (d.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]"))
//                    .getText()
//                    .equals("Москва и Московская область")) check = true;
//            return check;
//        });
//    }

//    public void typeNameField(String value) {
//        //Заполняем форму максиально быстро, пытаясь игнорировать анимацию страницы
//        wait.ignoring(StaleElementReferenceException.class)
//                .ignoring(ElementNotInteractableException.class)
//                .until(d -> {
//                    driver.findElement(By.name("name")).sendKeys(value);
//                    return true;
//                });
//    }
//


    private WebDriver getDriver() {
        return BrowsersFactory.buildDriver(browserName);
    }
}
