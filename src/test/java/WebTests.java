import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;

public class WebTests extends BaseRunner {

    @Test
    public void testEmptyValue() {
        driver.get(baseUrl);
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("birthday")).click();
        driver.findElement(By.name("city")).click();
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.cssSelector("svg.ui-icon-checkbox.ui-checkbox__icon")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text())and normalize-space(.)='условиями передачи информации'])[1]/following::button[1]")).click();
        assertEquals("Поле обязательное", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Фамилия и имя'])[1]/following::div[2]")).getText());
        assertEquals("Поле обязательное", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Дата рождения'])[1]/following::div[3]")).getText());
        assertEquals("Поле обязательное", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Город проживания'])[1]/following::div[3]")).getText());
        assertEquals("Поле обязательное", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Электронная почта'])[1]/following::div[2]")).getText());
        assertEquals("Поле обязательное", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Мобильный телефон'])[1]/following::div[2]")).getText());
        assertEquals("Поле обязательное", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='загрузите резюме/портфолио'])[1]/following::div[1]")).getText());
        assertEquals("Поле обязательное", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='условиями передачи информации'])[1]/following::div[1]")).getText());
    }

    @Test
    public void testInvalidValue() {
        driver.get(baseUrl);
        driver.findElement(By.name("name")).sendKeys("85гаг8г858");
        driver.findElement(By.name("name")).sendKeys(Keys.ENTER);
        assertEquals("Допустимо использовать только буквы русского алфавита и дефис", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Фамилия и имя'])[1]/following::div[2]")).getText());

        driver.findElement(By.name("name")).sendKeys(Keys.CONTROL, "a", "\b");
        driver.findElement(By.name("name")).sendKeys("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" +
                                                                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        driver.findElement(By.name("name")).sendKeys(Keys.ENTER);
        assertEquals("Максимальное количество символов – 133", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Фамилия и имя'])[1]/following::div[2]")).getText());

        driver.findElement(By.name("name")).sendKeys(Keys.CONTROL, "a", "\b");
        driver.findElement(By.name("name")).sendKeys("Мария");
        driver.findElement(By.name("name")).sendKeys(Keys.ENTER);
        assertEquals("Необходимо ввести фамилию и имя через пробел",driver.findElement(
                  By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Фамилия и имя'])[1]/following::div[2]")).getText());

        driver.findElement(By.name("birthday")).sendKeys("00.49.4904");
        driver.findElement(By.name("email")).sendKeys("ок49к4шк94ш");
        driver.findElement(By.name("phone")).sendKeys("+7(093) 409-49-99");
        driver.findElement(By.name("name")).sendKeys(Keys.ENTER);
        assertEquals("Поле заполнено некорректно", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Дата рождения'])[1]/following::div[3]")).getText());
        assertEquals("Введите корректный адрес эл. почты", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Электронная почта'])[1]/following::div[2]")).getText());
        assertEquals("Код города/оператора должен начинаться с цифры 3, 4, 5, 6, 8, 9", driver.findElement(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Мобильный телефон'])[1]/following::div[2]")).getText());
    }
}



