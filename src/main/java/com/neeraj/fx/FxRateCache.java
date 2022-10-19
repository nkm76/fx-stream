package com.neeraj.fx;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple Thread safe cache implementation
 */
@Component
public class FxRateCache {

    private final Map<String, FxRate> cache = new ConcurrentHashMap<>();

    /**
     * Retains the latest FxRate based on timestamp
     *
     * @param key
     * @param value
     */
    public void put(String key, FxRate value) {
        cache.merge(key, value, (oldValue, newValue) -> newValue.timestamp().isAfter(oldValue.timestamp()) ? newValue : oldValue);
    }

    public Optional<FxRate> get(String key) {
        return Optional.ofNullable(cache.get(key));
    }

    public Collection<FxRate> values() {
        return cache.values();
    }

}
