package com.github.tests.ui;

import com.github.data.TestData;
import com.github.helpers.annotations.Blocker;
import com.github.tests.ui.pages.MainPage;
import com.github.tests.ui.pages.SearchPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Configuration.baseUrl;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("SLomako")
@Epic("Неаутентифицированные тесты")
@Feature("UI: Неаутентифицированные тесты")
public class UnauthenticatedTests extends UiTestBase {

    private final MainPage mainPage  = new MainPage();
    private final SearchPage searchPage = new SearchPage();
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

    @Test
    @Blocker
    @DisplayName("Тест: Поиск на главной странице")
    void testSearch() {
        step("Открыть главную страницу", () ->
                mainPage.openPage(baseUrl));

        step("Выполнить поиск", () ->
                mainPage.search(testData.getSearchRepo()));

        step("Проверить результаты поиска", () ->
                assertThat(searchPage.isSearchResultDisplayed(testData.getSearchResult())))
                .isTrue();
    }

    @Test
    @Blocker
    @DisplayName("Расширенный поиск")
    void testAdvancedSearch() {
        step("Открыть страницу расширенного поиска", () ->
                searchPage.openAdvancedSearch(testData.getUrlSearchAdvanced()));

        step("Выбрать язык поиска", () ->
                searchPage.clickSearchLanguage()
                        .selectSearchLanguage(testData.getAdvancedSearchLanguage()));

        step("Установить параметры поиска", () ->
                searchPage.setSearchFrom(testData.getAdvancedSearchFrom())
                        .setSearchDate(testData.getAdvancedSearchDate())
                        .submitAdvancedSearch());

        step("Проверить результаты поиска", () ->
                assertThat(searchPage.isSearchResultDisplayed(testData.getSearchResult()))
                        .isTrue());
    }
}
