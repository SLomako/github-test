package ru.lomakosv.tests.ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.*;
import ru.lomakosv.data.TestData;
import ru.lomakosv.helpers.Annotations.Blocker;
import ru.lomakosv.tests.ui.pages.MainPage;
import ru.lomakosv.tests.ui.pages.SearchPage;

import static com.codeborne.selenide.Configuration.baseUrl;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Неаутентифицированные пользователи")
@Feature("UI-тестирование")
@DisplayName("UI: Неаутентифицированные тесты")
public class UnauthenticatedTests extends UiTestBase {

    private MainPage mainPage;
    private SearchPage searchPage;
    private TestData testData;

    @BeforeEach
    void setUpTest() {
        mainPage = new MainPage();
        searchPage = new SearchPage();
        testData = new TestData();
    }

    @Owner("SLomako")
    @Blocker
    @Test
    @DisplayName("Тест: Загрузка главной страницы")
    void testMainPageLoads() {
        step("Открыть главную страницу", () ->
                mainPage.openPage(baseUrl));

        step("Проверить заголовок страницы", () ->
                Assertions.assertTrue(mainPage.getTitle().contains(testData.getPageTitle())));
    }

    @Owner("SLomako")
    @Blocker
    @Test
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

    @Owner("SLomako")
    @Blocker
    @Test
    @DisplayName("Тест: Расширенный поиск")
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
