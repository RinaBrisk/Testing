package test;

import org.junit.*;
import pages.TinkoffVacanciesPage;

public class TinkoffVacanciesTests extends BaseRunner {


    @Test
    public void testEmptyValue() {
        TinkoffVacanciesPage vacancies = app.getVacancies();
        vacancies.open();
        vacancies.clickTermsOfUse();
        vacancies.clickSendBtn();
        vacancies.checkNameErrorField("Поле обязательное");
        vacancies.checkBirthdayErrorField("Поле обязательное");
        vacancies.checkCityErrorField("Поле обязательное");
        vacancies.checkEmailErrorField("Поле обязательное");
        vacancies.checkPhoneErrorField("Поле обязательное");
        vacancies.checkCVErrorField("Поле обязательное");
        vacancies.checkTermsOfUseErrorField("Поле обязательное");
    }

    @Test
    public void testInvalidValue() {
        TinkoffVacanciesPage vacancies = app.getVacancies();
        vacancies.open();
        vacancies.typeNameField("Наташа44");
        vacancies.clickSendBtn();
        vacancies.checkNameErrorField("Допустимо использовать только буквы русского алфавита и дефис");
        vacancies.typeNameField("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" +
                                                        "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        vacancies.checkNameErrorField("Максимальное количество символов – 133");
        vacancies.typeNameField("Мария");
        vacancies.checkNameErrorField("Необходимо ввести фамилию и имя через пробел");
        vacancies.typeBirthdayField("00.49.4904");
        vacancies.checkBirthdayErrorField("Поле заполнено некорректно");
        vacancies.typeEmailField("ок49к4шк94ш");
        vacancies.checkEmailErrorField("Введите корректный адрес эл. почты");
        vacancies.typePhoneField("+7(093) 409-49-99");
        vacancies.checkPhoneErrorField("Код города/оператора должен начинаться с цифры 3, 4, 5, 6, 8, 9");
    }
}



