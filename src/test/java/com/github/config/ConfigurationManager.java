package com.github.config;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {

    private static volatile ApiConfig apiConfig;
    private static volatile UiConfig uiConfig;
    private static volatile SelenoidConfig authSelenoidConfig;

    private ConfigurationManager() {
    }

    public static synchronized ApiConfig getApiConfig() {
        if (apiConfig == null) {
            apiConfig = ConfigCache.getOrCreate(ApiConfig.class, System.getProperties());
        }
        return apiConfig;
    }

    public static synchronized UiConfig getUiConfig() {
        if (uiConfig == null) {
            uiConfig = ConfigCache.getOrCreate(UiConfig.class, System.getProperties());
        }
        return uiConfig;
    }

    public static synchronized SelenoidConfig getAuthSelenoidConfig() {
        if (authSelenoidConfig == null) {
                    authSelenoidConfig = ConfigCache.getOrCreate(SelenoidConfig.class, System.getProperties());
                }
        return authSelenoidConfig;
    }
}