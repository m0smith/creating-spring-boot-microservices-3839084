package com.example.explorecalijpa.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Simple feature flag service that reads boolean flags from the
 * configuration properties prefixed with {@code features}.
 */
@Component
@ConfigurationProperties(prefix = "features")
public class FeatureFlagService {

    /**
     * Map of feature names to whether they are enabled. Populated from
     * application properties.
     */
    private Map<String, Boolean> flags = new HashMap<>();

    public Map<String, Boolean> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, Boolean> flags) {
        this.flags = flags;
    }

    /**
     * Determine if the given feature is enabled. If the feature flag is not
     * present it is considered disabled.
     *
     * @param featureName name of the feature
     * @return true if enabled, otherwise false
     */
    public boolean isEnabled(String featureName) {
        return flags.getOrDefault(featureName, false);
    }
}
