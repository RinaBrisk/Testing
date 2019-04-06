package test;

import org.junit.Test;
import pages.GooglePage;
import pages.TinkoffMobileTariffsPage;

public class TinkoffMobileTariffsTests extends BaseRunner {

    @Test
    public void switchingBetweenTabs() {
        GooglePage googlePage = app.getGoogle();
        googlePage.open();
        googlePage.selectAutoSubstitutionForRequest("мобайл тинькофф", "мобайл тинькофф тарифы");
        googlePage.openPageFromSearchResults(TinkoffMobileTariffsPage.getTITLE());
        TinkoffMobileTariffsPage tariffsPage = app.getMobileTariffs();
        tariffsPage.switchToWindow(TinkoffMobileTariffsPage.getTITLE());
        googlePage.switchToWindow("мобайл тинькофф тарифы - Поиск в Google");
        googlePage.closeCurrentTab();
        tariffsPage.switchToMainTab();
        tariffsPage.checkPageURL(TinkoffMobileTariffsPage.getURL());
    }

    @Test
    public void changeOfRegion() {
        TinkoffMobileTariffsPage tariffsPage = app.getMobileTariffs();
        tariffsPage.open();
        tariffsPage.confirmMoscowRegion();
        tariffsPage.checkCurrentRegion("Москва и Московская область");
        tariffsPage.open();
        tariffsPage.checkCurrentRegion("Москва и Московская область");
        //с дефолтными пакетами сумма не равна
        String priceMoscowDef = tariffsPage.getCurrentPrice();
        tariffsPage.changeRegion("Краснодарский кр.");
        String priceKrasnodarDef = tariffsPage.getCurrentPrice();
        tariffsPage.checkPriceIsDifferent(priceMoscowDef, priceKrasnodarDef);
        //c максимальными пакетами сумма равна
        tariffsPage.setMaximumServices();
        String priceKrasnodarMax = tariffsPage.getCurrentPrice();
        tariffsPage.changeRegion("Москва и Московская обл.");
        tariffsPage.setMaximumServices();
        String priceMoscowMax = tariffsPage.getCurrentPrice();
        tariffsPage.checkPriceIsEquals(priceMoscowMax, priceKrasnodarMax);
    }

    @Test
    public void activeButton() {
        TinkoffMobileTariffsPage tariffsPage = app.getMobileTariffs();
        tariffsPage.open();
        tariffsPage.disableAllServices();
        String price = tariffsPage.getCurrentPrice();
        tariffsPage.checkPriceIsEquals(price,"Общая цена: 0 ₽");
        tariffsPage.typeNameField("Рина Сергеева");
        tariffsPage.typePhoneField("9850913459");
        tariffsPage.orderSIMClick();
        tariffsPage.checkScheduleAppointmentIsDisplayed();
    }
}
