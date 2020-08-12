package org.dkvs.node.store;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class KeyValueStore<K, V> {

    Map<K, Value<V>> store = new ConcurrentHashMap<>();
    private final int maxCapacity;
    private int size = 0;
    private final int percentage = 75;
    private boolean isSweepCleanInitiated = Boolean.FALSE;

    public KeyValueStore(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void put(K key, V value, long expiryInMillis) {
        if (!store.containsKey(key)) {
            size++;
        }
        store.put(key, new Value<>(value, LocalDateTime.now(), expiryInMillis));
        asyncSweepClean();
    }

    public V get(K key) {
        if (store.containsKey(key)) {
            return store.get(key).getValue();
        }
        return null;
    }

    public V getOrDefault(K key, V defaultValue) {
        V val = this.get(key);
        return val == null ? defaultValue : val;
    }

    public void remove(K key) {
        if (store.containsKey(key)) {
            size--;
        }
        this.store.remove(key);
    }

    private void asyncSweepClean() {
        if (((this.maxCapacity * this.percentage) / 100) >= this.size) {
            if (!isSweepCleanInitiated) {
                new Thread(() -> {
                    try {
                        this.isSweepCleanInitiated = true;
                        List<K> keysToBeSweeped = new ArrayList<>();
                        this.store.forEach((k, v) -> {
                            if (v.isExpired()) {
                                keysToBeSweeped.add(k);
                            }
                        });
                        keysToBeSweeped.forEach(k -> this.store.remove(k));
                    } finally {
                        this.isSweepCleanInitiated = false;
                    }
                }).start();
            }
        }
    }
}
