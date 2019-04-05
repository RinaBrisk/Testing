import org.junit.Test;

public class TinkoffMobileTests extends BaseRunner {

    private static String URL_TARIFFS = "https://www.tinkoff.ru/mobile-operator/tariffs/";
    private static String URL_GOOGLE = "https://www.google.ru/";

    @Test
    public void switchingBetweenTabs() {
        app.getPage(URL_GOOGLE);
        app.selectAutoSubstitutionForRequest("мобайл тинькофф", "мобайл тинькофф тарифы");
        app.openPageFromSearchResults("Тарифы Тинькофф Мобайла");
        app.switchToWindow("Тарифы Тинькофф Мобайла");
        app.switchToWindow("мобайл тинькофф тарифы - Поиск в Google");
        app.closeCurrentTab();
        app.switchToMainTab();
        app.checkPageURL(URL_TARIFFS);
    }
//
//    @Test
//    public void changeOfRegion() {
//        driver.get(URL_TARIFFS);
//        wait.until(d -> d.findElement(By.xpath("//span[@class='MvnoRegionConfirmation__option_v9PfP']"))).click();
//        wait.until(d ->
//                d.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]"))
//                        .getText()
//                        .equals("Москва и Московская область"));
//
//        wait.until(d -> {
//            boolean check = false;
//            d.get(URL_TARIFFS);
//            if (d.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]"))
//                    .getText()
//                    .equals("Москва и Московская область")) check = true;
//            return check;
//        });
//
//        //с дефолтными пакетами сумма не равна
//        String priceMoscowDef = driver.findElement(By.xpath("//h3")).getText();
//        logger.info("Стоимость услуг для Москвы: " + priceMoscowDef);
//        driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]")).click();
//        By listItems = By.xpath("//div[contains(@class,'MobileOperatorRegionsPopup__listParts_16aoL')]/div[2]/div");
//        List<WebElement> elements = driver.findElements(listItems);
//        wait.until(d -> {
//            for (WebElement el : elements) {
//                if (el.getText().equals("Краснодарский кр.")) {
//                    logger.info("Выбранный район: " + el.getText());
//                    el.click();
//                    break;
//                }
//            }
//            String priceKrasnodarDef = d.findElement(By.xpath("//h3")).getText();
//            logger.info("Стоимость услуг для Краснодарского края: " + priceKrasnodarDef);
//            boolean check = false;
//            if (!priceMoscowDef.equals(priceKrasnodarDef)) check = true;
//            return check;
//        });
//
//        //c максимальными пакетами сумма равна
//        setMaxPackets();
//        String priceKrasnodarMax = driver.findElement(By.xpath("//h3")).getText();
//        logger.info("Стоимость услуг для Краснодарского края: " + priceKrasnodarMax);
//
//        driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title_DOqnW')]")).click();
//        listItems = By.xpath("//div[contains(@class,'MobileOperatorRegionsPopup__listParts_16aoL')]/div[1]/div");
//        List<WebElement> regions = driver.findElements(listItems);
//        wait.until(d -> {
//            boolean check = false;
//            for (WebElement el : regions) {
//                if (el.getText().equals("Москва и Московская обл.")) {
//                    el.click();
//                    check = true;
//                    break;
//                }
//            }
//            return check;
//        });
//        setMaxPackets();
//        String priceMoscowMax = driver.findElement(By.xpath("//h3")).getText();
//        logger.info("Стоимость услуг для Москвы: " + priceMoscowMax);
//        wait.until(d -> (priceMoscowMax.equals(priceKrasnodarMax)));
//    }
//
//    @Test
//    public void activeButton() {
//        driver.get(URL_TARIFFS);
//        driver.manage().timeouts().setScriptTimeout(4, TimeUnit.SECONDS);
//        Select select = new Select();
//        wait.until(d -> select.valueChoice("Интернет", "0 ГБ").equals("0 ГБ"));
//        wait.until(d -> select.valueChoice( "Звонки", "0 минут").equals("0 минут"));
//
//        CheckBox checkBox = new CheckBox();
//        checkBox.click("Мессенджеры (59 ₽)");
//        checkBox.click("Социальные сети (59 ₽)");
//
//        String price = driver.findElement(By.xpath("//h3")).getText();
//        logger.info(price);
//        wait.until(d -> price.equals("Общая цена: 0 ₽"));
//
//        TextInput textInput = new TextInput();
//        wait.until(d -> textInput.setText("//div[@class='ui-input__column']//span[text()='Фамилия, имя и отчество']/parent::span/parent::div/input", "Сергеева Рина").equals("Сергеева Рина"));
//        wait.until(d -> textInput.setText("//div[@class='ui-input__column']//span[text()='Контактный телефон']/parent::span/parent::div/input[@name='phone_mobile']", "9009090909").equals("+7(900) 909-09-09"));
//
//        Button button = new Button();
//        button.click("Заказать сим-карту");
//        wait.until(d -> {
//            boolean check = false;
//            WebElement el = driver.findElement(By.xpath("//div[contains(@class, 'UIAppointment__container_3A8ha UIAppointment__container_highlighted_3lFo8')]"));
//            if(el.isDisplayed()) check = true;
//            return check;
//        });
//    }
//
//    private void setMaxPackets() {
//        Select select = new Select();
//        wait.until(d -> select.valueChoice("Интернет", "Безлимитный интернет").equals("Безлимитный интернет"));
//        wait.until(d -> select.valueChoice("Звонки", "Безлимитные минуты").equals("Безлимитные минуты"));
//
//        CheckBox checkBox = new CheckBox();
//        checkBox.click("Режим модема (499 ₽)");
//        checkBox.click("Безлимитные СМС (49 ₽)");
//    }
}
