package ru.lomakosv.config;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {

    private static final ApiConfig apiConfig = ConfigCache.getOrCreate(ApiConfig.class, System.getProperties());
    private static final UiConfig uiConfig = ConfigCache.getOrCreate(UiConfig.class, System.getProperties());
    private static final SelenoidConfig authSelenoidConfig = ConfigCache.getOrCreate(SelenoidConfig.class, System.getProperties());

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
