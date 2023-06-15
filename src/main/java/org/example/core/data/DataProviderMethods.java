package org.example.core.data;

import org.testng.annotations.DataProvider;

public class DataProviderMethods {
    @DataProvider
    public Object[][] dataForCheckoutTest() {
        return new Object[][]{
                {"ivanov@gmail.", "", "test-lastname", "", "", "us", "Alaska", "", "q1w1e1", "", "tablerate_bestway"},
                {"@gmail.com", "test-firstname", "", "", "test-street", "us", "", "test-city", "", "", ""},
                {"john@.com", "", "test-firstname", "test-lastname", "", "test-street", "ua", "", "test-city", "q1w1e1", "", "flatrate_flatrate"},
                {"ivanov@mail.com", "", "", "test-company", "", "", "Alaska", "", "", "12345678", ""},
                {"i@mail.co", "", "test-lastname", "test-company", "", "ca", "", "", "q1w1e1", "12345678", "", "tablerate_bestway"},
                {"iva@mail.com", "test-firstname", "", "test-company", "test-street", "ua", "test-region", "test-city", "", "12345678", ""},
                {"ivanov@mail.com", "test-firstname", "test-lastname", "test-company", "test-street", "ca", "Alberta", "test-city", "q1w1e1", "12345678", "flatrate_flatrate"}
        };
    }

    @DataProvider
    public Object[][] dataForAddToCartTest() {
        return new Object[][]{
                {"", "", "0"},
                {"", "", "1"},
                {"", "red", "0"},
                {"", "green", "2"},
                {"s", "", "0"},
                {"m", "", "3"},
                {"m", "red", "0"},
                {"s", "yellow", "1"},
        };
    }

    @DataProvider
    public Object[][] dataForCanSearchItemTest() {
        return new Object[][]{
                {"aa"},
                {"jak"},
                {"yoga"},
                {"jacket"},
                {"short"},

        };
    }

    @DataProvider
    public Object[][] dataForCanLoginTest() {
        return new Object[][]{
                {"", ""},
                {"ivanov@gmail.com", ""},
                {"john@.com", "12345"},
                {"roni_cost@example.com", "roni_cost3@example.com"}
        };
    }

    @DataProvider
    public Object[][] dataForCanCreateAccountTest() {
        return new Object[][]{
                {"", "", "", "", ""},
                {"", "", "ivanov@gmail.com", "qwertyui", ""},
                {"", "test-lastname", "", "12345678", "12345"},
                {"", "test-lastname", "iva@gm.ca", "123qwe12", ""},
                {"test-firstname", "", "@gmail.com", "123Qwe123", ""},
                {"test-firstname", "", "ivanov@gmail.com", "123Qwe12*", "123Qwe12*"},
                {"test-firstname", "test-lastname", "john@.com", "123Qwe12", "123Qwe"},
                {"test-firstname", "test-lastname", "ivanov4@gmail.com", "123Qwe12", "123Qwe12"},
        };
    }
}
