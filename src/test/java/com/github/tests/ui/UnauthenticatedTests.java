package com.github.tests.ui;

import com.github.data.TestData;
import com.github.helpers.annotations.Blocker;
import com.github.tests.ui.pages.MainPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Configuration.baseUrl;
import static io.qameta.allure.Allure.step;

@Owner("SLomako")
@Epic("Неаутентифицированные тесты")
@Feature("UI: Неаутентифицированные тесты")
public class UnauthenticatedTests extends TestBase {

    private final MainPage mainPage  = new MainPage();
    private final TestData testData = new TestData();

    @Test
    @Blocker
    @DisplayName("Загрузка главной страницы")
    void testMainPageLoads() {
        step("Открыть главную страницу", () ->
                mainPage.openPage(baseUrl));

        step("Проверить заголовок страницы", () ->
                Assertions.assertTrue(mainPage.getTitle().contains(testData.getPageTitle())));
    }
}
