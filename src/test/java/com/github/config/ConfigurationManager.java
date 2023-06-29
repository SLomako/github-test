package com.github.config;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {

    public static ApiConfig getApiConfig() {
        return ConfigCache.getOrCreate(ApiConfig.class, System.getProperties());
    }

    public static UiConfig getUiConfig() {
        return ConfigCache.getOrCreate(UiConfig.class, System.getProperties());
    }

    public static SelenoidConfig getAuthSelenoidConfig() {
        return ConfigCache.getOrCreate(SelenoidConfig.class, System.getProperties());
    }
}