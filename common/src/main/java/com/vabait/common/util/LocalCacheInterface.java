package com.vabait.common.util;

/*
 * Provider*/

public interface LocalCacheInterface<K, V> {
    V getValue(K key);
}
