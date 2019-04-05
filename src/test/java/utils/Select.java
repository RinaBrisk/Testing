//package utils;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.interactions.Actions;
//
//public class Select extends BaseRunner {
//
//    public String valueChoice(String listName, String valueName){
//        Actions actionsInternet = new Actions(driver);
//        actionsInternet.click(driver.findElement(By.xpath("//div[@class='ui-select_close ui-select_changed']" +
//                "//span[text()='" + listName + "']/../../div[@class='ui-select__additional']")))
//                .click(driver.findElement(By.xpath("//span[text()='" + valueName + "']/..")))
//                .perform();
//        return getCurrentValue(listName);
//    }
//
//    public String getCurrentValue(String listName){
//        return driver.findElement(By.xpath("//span[text()='" + listName + "']/../div/span[@class='ui-select__title-flex-text']")).getText();
//    }
//}
