package ru.lomakosv.tests.ui;

import io.qameta.allure.*;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.lomakosv.config.ApiConfig;
import ru.lomakosv.config.ConfigurationManager;
import ru.lomakosv.helpers.annotations.LocalTestExtensions;
import ru.lomakosv.tests.ui.pages.LoginPage;

import static io.qameta.allure.Allure.step;

@Owner("SLomako")
@Epic("Авторизация")
@Feature("UI-тестирование")
@DisplayName("UI: Авторизация")

public class LoginTest extends UiTestBase {

    private ApiConfig apiConfig;
    private LoginPage loginPage;

    @BeforeEach
    void setUpTest() {
        loginPage = new LoginPage();
        apiConfig = ConfigurationManager.getApiConfig();
    }

    @Severity(SeverityLevel.BLOCKER)
    @LocalTestExtensions.LocalTest
    @DisplayName("Тест: Успешный вход в систему")
    @Test
    void testSuccessfulLogin() {
        step("Открыть страницу входа", () ->
                loginPage.openLoginPage(testData.getUrlLoginPage()));

        step("Ввести логин", () ->
                loginPage.enterLogin(apiConfig.getGitHubLogin()));

        step("Ввести пароль", () ->
                loginPage.enterPassword(apiConfig.getGitHubPassword()));

        step("Нажать на кнопку входа", () ->
                loginPage.clickLoginButton());

        step("Нажать на индикатор предварительного просмотра", () ->
                loginPage.clickFeaturePreviewIndicator());

        step("Проверить имя пользователя", () ->
                Assertions.assertEquals(loginPage.getUsername(), apiConfig.getGitHubLogin()));
    }

    @Severity(SeverityLevel.CRITICAL)
    @LocalTestExtensions.LocalTest
    @DisplayName("Тест: Вход в систему с недопустимым email")
    @Test
    void testLoginWithInvalidEmail() {
        step("Открыть страницу входа", () ->
                loginPage.openLoginPage(testData.getUrlLoginPage()));

        step("Ввести недопустимый email", () ->
                loginPage.enterLogin("invalidEmail"));

        step("Ввести пароль", () ->
                loginPage.enterPassword(apiConfig.getGitHubPassword()));

        step("Нажать на кнопку входа", () ->
                loginPage.clickLoginButton());

        step("Проверить текст предупреждения", () ->
                Assertions.assertEquals(loginPage.getAlertText(), "Incorrect username or password."));
    }

    @Severity(SeverityLevel.CRITICAL)
    @LocalTestExtensions.LocalTest
    @DisplayName("Тест: Вход в систему с недопустимым паролем")
    @Test
    void testLoginWithInvalidPassword() {
        step("Открыть страницу входа", () ->
                loginPage.openLoginPage(testData.getUrlLoginPage()));

        step("Ввести логин", () ->
                loginPage.enterLogin(apiConfig.getGitHubLogin()));

        step("Ввести недопустимый пароль", () ->
                loginPage.enterPassword("InvalidPassword"));

        step("Нажать на кнопку входа", () ->
                loginPage.clickLoginButton());

        step("Проверить текст предупреждения", () ->
                Assertions.assertEquals(loginPage.getAlertText(), "Incorrect username or password."));
    }
}
