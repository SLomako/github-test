package ru.lomakosv.config;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {

    private static final ApiConfig apiConfig = ConfigCache.getOrCreate(ApiConfig.class);
    private static final UiConfig uiConfig = ConfigCache.getOrCreate(UiConfig.class);
    private static final SelenoidConfig authSelenoidConfig = ConfigCache.getOrCreate(SelenoidConfig.class);

    public static ApiConfig getApiConfig() {
        return apiConfig;
    }

    public static UiConfig getUiConfig() {
        return uiConfig;
    }

    public static SelenoidConfig getAuthSelenoidConfig() {
        return authSelenoidConfig;
    }
}
