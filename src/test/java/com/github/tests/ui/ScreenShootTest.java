package com.github.tests.ui;

import com.github.helpers.annotations.Critical;
import com.github.utils.AssertScreenShootUtil;
import com.github.utils.PathScreenShot;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.github.tests.ui.pages.LoginPage;
import com.github.tests.ui.pages.MainPage;

import static com.codeborne.selenide.Configuration.baseUrl;
import static io.qameta.allure.Allure.step;

@Owner("SLomako")
@Epic("Скриншоты страниц")
@Feature("UI: Скриншоты страниц")
public class ScreenShootTest extends UiTestBase {

    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final AssertScreenShootUtil assertScreenShootUtil = new AssertScreenShootUtil();
    private final PathScreenShot pathScreenShot = new PathScreenShot();

    @Critical
    @DisplayName("Скриншот заголовка главной страницы")
    @Test
    void testMainPageHeader() {
        String screenshotName = "mainpage_header";
        String expectedScreenshotPath = pathScreenShot.getExpectedScreenshotPath(screenshotName);
        String actualScreenshotPath = pathScreenShot.getActualScreenshotPath(screenshotName);
        String diffImagePath = pathScreenShot.getDiffImagePath(screenshotName);

        step("Открыть главную страницу", () ->
                mainPage.openPage(baseUrl));

        step("Сделать скриншот заголовка главной страницы", () ->
                step("Проверка идентичности скриншотов", () ->
                        assertScreenShootUtil.takeAndCompareScreenshots(mainPage.getHeaderElement(), expectedScreenshotPath,
                                actualScreenshotPath, diffImagePath)));
    }

    @Critical
    @DisplayName("Скриншот тела формы входа")
    @Test
    void testLoginBody() {
        String screenshotName = "login";
        String expectedScreenshotPath = pathScreenShot.getExpectedScreenshotPath(screenshotName);
        String actualScreenshotPath = pathScreenShot.getActualScreenshotPath(screenshotName);
        String diffImagePath = pathScreenShot.getDiffImagePath(screenshotName);

        step("Открыть страницу входа", () ->
                loginPage.openLoginPage(testData.getUrlLoginPage()));

        step("Нажать на заголовок формы входа", loginPage::clickLoginFormHeader);


        step("Сделать скриншот тела формы входа", () ->
                step("Проверка индентичности скриншотов", () ->
                        assertScreenShootUtil.takeAndCompareScreenshots(loginPage.getBodyElement(),
                                expectedScreenshotPath, actualScreenshotPath, diffImagePath)));
    }
}