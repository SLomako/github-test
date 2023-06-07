package ru.lomakosv.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/test.properties",
        "system:properties"
})
public interface UiConfig extends Config {

    @DefaultValue("Chrome, 112.0")
    @Key("ui.browserWithVersion")
    String[] getBrowserAndVersion();

    @DefaultValue("1920x1080")
    @Key("ui.browserSize")
    String browserSize();

    @DefaultValue("https://github.com")
    @Key("ui.baseURL")
    String baseUrl();

    @Key("ui.remoteURL")
    String remoteUrl();
}
