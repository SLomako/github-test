package com.github.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/test.properties",
        "system:properties"
})
public interface SelenoidConfig extends Config {

    @Key("selenoid.username")
    String userName();

    @Key("selenoid.password")
    String password();

    @DefaultValue("selenoid.autotests.cloud")
    @Key("ui.remoteURL")
    String remoteUrl();
}


