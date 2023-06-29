package com.github.tests.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    private final SelenideElement
            loginFormHeader = $("[class*=auth-form-header]"),
            body = $("body"),
            loginField = $("#login_field"),
            passwordField = $("#password"),
            loginButton = $("[name=commit]"),
            featurePreviewIndicator = $("[class='avatar circle']"),
            userNameString = $("[class='Truncate-text']"),
            alertElement = $(".js-flash-alert");

    public LoginPage openLoginPage(String urlLoginPage) {
        open(urlLoginPage);
        return this;
    }

    public void clickLoginFormHeader() {
        loginFormHeader.click();
    }

    public SelenideElement getBodyElement() {
        return body;
    }

    public LoginPage enterLogin(String login) {
        loginField.setValue(login);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    public LoginPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    public void clickFeaturePreviewIndicator() {
        featurePreviewIndicator.click();
    }

    public String getUsername() {
        return userNameString.getText();
    }

    public String getAlertText() {
        return alertElement.getText();
    }
}