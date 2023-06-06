package ru.lomakosv.tests.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private final SelenideElement
            searchInput = $(".header-search-input"),
            pageTitleElement = $("head > title"),
            headerElement = $("[class*=Header-old]");

    public void openPage() {
        open("https://github.com");
    }

    public void search(String text) {
        searchInput.setValue(text).pressEnter();
    }

    public String getTitle() {
        return pageTitleElement.innerText();
    }

    public SelenideElement getHeaderElement() {
        return headerElement;
    }
}