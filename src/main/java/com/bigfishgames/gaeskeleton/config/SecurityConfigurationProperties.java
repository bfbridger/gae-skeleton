package com.bigfishgames.gaeskeleton.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("genesis.security")
public class SecurityConfigurationProperties {
    private boolean requireSsl = true; // require ssl by default

    public boolean getRequireSsl() {
        return requireSsl;
    }

    public void setRequireSsl(boolean requireSsl) {
        this.requireSsl = requireSsl;
    }
}
