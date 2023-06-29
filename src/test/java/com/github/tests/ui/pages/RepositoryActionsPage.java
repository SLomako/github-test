package com.github.tests.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RepositoryActionsPage {

    private SelenideElement codeButton = $("[class*='Button--primary']");
    private SelenideElement downloadZipButton = $("[href*='.zip']");

    @Step("Открываем страницу репозитория {searchQuery}")
    public void openMainPage(String searchQuery) {
        open(String.format("https://github.com/%s", searchQuery));
    }

    @Step("Нажать кнопку '<> Code'")
    public void clickCodeButton() {
        codeButton.click();
    }

    @Step("Нажать кнопку 'Download ZIP'")
    public File clickDownloadZipButton() throws FileNotFoundException {
        return downloadZipButton.download();
    }
}