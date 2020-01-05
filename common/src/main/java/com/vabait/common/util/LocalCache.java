package com.vabait.common.util;

import java.util.HashMap;
import java.util.Map;

public class LocalCache<K, V> {
    Map<K, V> cache = new HashMap();

    public V getValue(K key, LocalCacheInterface<K, V> provider) {
        V value;
        boolean contain = cache.containsKey(key);
        if (!contain) {
            value = provider.getValue(key);
            cache.put(key, value);
        } else {
            value = cache.get(key);
        }

        return value;
    }
}