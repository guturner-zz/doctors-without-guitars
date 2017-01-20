package org.guy.rpg.dwg.config;

import java.util.Properties;

public class StormpathConfig extends Properties {

    public StormpathConfig() {
        super.put("apiKey.id", System.getenv("STORMPATH_CLIENT_APIKEY_ID"));
        super.put("apiKey.secret", System.getenv("STORMPATH_CLIENT_APIKEY_SECRET"));
    }
}
