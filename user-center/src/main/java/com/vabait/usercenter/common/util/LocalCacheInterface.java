package com.vabait.usercenter.common.util;

/*
 * Provider*/

public interface LocalCacheInterface<K, V> {
    V getValue(K key);
}
