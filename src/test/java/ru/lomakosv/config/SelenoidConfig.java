package ru.lomakosv.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/test.properties",
        "system:properties"
})
public interface SelenoidConfig extends Config {

    @Key("selenoid.username")
    String getRemoteUserName();

    @Key("selenoid.password")
    String getRemotePassword();
}


