package com.bigfishgames.gaeskeleton.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// Copied from https://developer.okta.com/blog/2018/07/30/10-ways-to-secure-spring-boot
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private SecurityConfigurationProperties properties;

    @Autowired
    public SecurityConfiguration(SecurityConfigurationProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // App Engine does not allow http sessions unless explicitly enabled. We don't need them for a REST api.
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable(); // csrf is not needed for REST apis, and it will try to create sessions unless disabled

        // Ssl should be required in production but may be disabled in other environments
        if (properties.getRequireSsl()) {
            http.requiresChannel()
                    .anyRequest()
                    .requiresSecure();
        }
    }
}
