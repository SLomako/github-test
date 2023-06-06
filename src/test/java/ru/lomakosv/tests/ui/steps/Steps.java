package ru.lomakosv.tests.ui.steps;

import io.qameta.allure.Step;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Steps {

    @Step("Открываем страницу репозитория {searchQuery}")
    public void openMainPage(String searchQuery) {
        open(String.format("https://github.com/%s", searchQuery));
    }

    @Step("Нажать кнопку '<> Code'")
    public void clickCodeButton() {
        $("[class*='Button--primary']").click();
    }

    @Step("Нажать кнопку 'Download ZIP'")
    public File clickDownloadZipButton() throws FileNotFoundException {

        return $("[href*='.zip']").download();
    }

}
