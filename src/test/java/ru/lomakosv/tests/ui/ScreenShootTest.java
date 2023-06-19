package ru.lomakosv.tests.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.lomakosv.helpers.annotations.Critical;
import ru.lomakosv.tests.ui.pages.LoginPage;
import ru.lomakosv.tests.ui.pages.MainPage;
import ru.lomakosv.utils.AssertScreenShootUtil;
import ru.lomakosv.utils.PathScreenShot;

import static com.codeborne.selenide.Configuration.baseUrl;
import static io.qameta.allure.Allure.step;

@Owner("SLomako")
@Feature("UI-тестирование")
@DisplayName("UI: Скриншоты страниц")
public class ScreenShootTest extends UiTestBase {

    private MainPage mainPage;
    private LoginPage loginPage;
    private AssertScreenShootUtil assertScreenShootUtil;

    @BeforeEach
    void setUpTest() {
        mainPage = new MainPage();
        loginPage = new LoginPage();
        assertScreenShootUtil = new AssertScreenShootUtil();
    }

    @Critical
    @DisplayName("Тест: Скриншот заголовка главной страницы")
    @Test
    void testMainPageHeader() {
        String screenshotName = "mainpage_header";
        String expectedScreenshotPath = PathScreenShot.getExpectedScreenshotPath(screenshotName);
        String actualScreenshotPath = PathScreenShot.getActualScreenshotPath(screenshotName);
        String diffImagePath = PathScreenShot.getDiffImagePath(screenshotName);

        step("Открыть главную страницу", () ->
                mainPage.openPage(baseUrl));

        step("Сделать скриншот заголовка главной страницы", () ->
                step("Проверка идентичности скриншотов", () ->
                        assertScreenShootUtil.takeAndCompareScreenshots(mainPage.getHeaderElement(), expectedScreenshotPath,
                                actualScreenshotPath, diffImagePath)));
    }

    @Critical
    @DisplayName("Тест: Скриншот тела формы входа")
    @Test
    void testLoginBody() {
        String screenshotName = "login";
        String expectedScreenshotPath = PathScreenShot.getExpectedScreenshotPath(screenshotName);
        String actualScreenshotPath = PathScreenShot.getActualScreenshotPath(screenshotName);
        String diffImagePath = PathScreenShot.getDiffImagePath(screenshotName);

        step("Открыть страницу входа", () ->
                loginPage.openLoginPage(testData.getUrlLoginPage()));

        step("Нажать на заголовок формы входа", () ->
                loginPage.clickLoginFormHeader());


        step("Сделать скриншот тела формы входа", () ->
                step("Проверка индентичности скриншотов", () ->
                        assertScreenShootUtil.takeAndCompareScreenshots(loginPage.getBodyElement(),
                                expectedScreenshotPath, actualScreenshotPath, diffImagePath)));
    }
}