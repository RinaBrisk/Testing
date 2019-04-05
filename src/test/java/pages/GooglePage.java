package pages;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class GooglePage extends Page {

    private static final String URL = "https://www.google.ru";
    private static final String TITLE = "Google";

    public GooglePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[contains(@class, 'gLFyf gsfi')]")
    WebElement selectMoscowRegion;

    @FindBy(xpath = "//ul[@role='listbox']/li[@role='presentation' and .//*[@role='option']]")
    List<WebElement> autoSubstitutionElements;

    @FindBy(xpath = "//h2[text()='Все результаты']/parent::div/div//h3")
    List<WebElement> searchResults;

    public void open() {
        driver.navigate().to(URL);
        isLoadedByTitleContains(TITLE);
    }

    public void selectAutoSubstitutionForRequest(String request, String autoSubRequest) {
        selectMoscowRegion.sendKeys(request);
        wait
                .ignoring(StaleElementReferenceException.class)
                .withMessage("Что-то пошло не так...")
                .pollingEvery(Duration.ofMillis(500))
                .until(d -> {
                    // список вариаций поисковой выдачи
                    for (WebElement el : autoSubstitutionElements) {
                        if (el.getText().equals(autoSubRequest)) {
                            logger.info("Поиск по запросу: " + el.getText());
                            el.click();
                            break;
                        }
                    }
                    return isLoadedByTitleContains(autoSubRequest);
                });
    }

    public void openPageFromSearchResults(String pageTitle) {
        wait.until(d -> {
            boolean isClicked = false;
            for (WebElement el : searchResults) {
                if (el.getText().contains(pageTitle)) {
                    el.click();
                    isClicked = true;
                    break;
                }
            }
            return isClicked;
        });
    }


}
