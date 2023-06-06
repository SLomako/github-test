package ru.lomakosv.tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.lomakosv.config.ConfigurationManager;
import ru.lomakosv.config.SelenoidConfig;
import ru.lomakosv.config.UiConfig;
import ru.lomakosv.data.TestData;
import ru.lomakosv.helpers.Attach;

import java.util.Map;

@Execution(ExecutionMode.CONCURRENT)
public class UiTestBase {

    protected UiConfig webConfig = ConfigurationManager.getUiConfig();
    protected SelenoidConfig authSelenoidConfig = ConfigurationManager.getAuthSelenoidConfig();
    protected TestData testData;

    @BeforeEach
    void setUpBase() {
        webConfig = ConfigurationManager.getUiConfig();
        authSelenoidConfig = ConfigurationManager.getAuthSelenoidConfig();
        testData = new TestData();
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.pageLoadStrategy = System.getProperty("selenide.pageLoadStrategy", "eager");
        Configuration.baseUrl = webConfig.baseUrl();
        String[] browserWithVersion = webConfig.getBrowserAndVersion();
        Configuration.browser = browserWithVersion[0];
        Configuration.browserVersion = browserWithVersion[1];
        Configuration.browserSize = webConfig.browserSize();

        boolean isRemote = Boolean.getBoolean("isRemote");
        if (isRemote) {
            String remoteUrl = webConfig.remoteUrl();
            Configuration.remote = "https://" + authSelenoidConfig.getRemoteUsername() + ":" + authSelenoidConfig.getRemotePassword() + "@" + remoteUrl + "/wd/hub";
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }
    }

    @AfterEach
    void tearDownBase() {
        Attach.addVideo();
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.screenshotAs("Last screenshot");
        Selenide.closeWebDriver();
    }
}
