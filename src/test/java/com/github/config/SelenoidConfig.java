package com.github.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/test.properties",
})
public interface SelenoidConfig extends Config {

    @Key("selenoid.userName")
    String userName();

    @Key("selenoid.password")
    String password();

    @Key("selenoid.remoteURL")
    String url();
}


