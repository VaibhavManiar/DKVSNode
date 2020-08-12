package org.dkvs.node.store;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Value<T> {
    private final T value;
    private final LocalDateTime creationTime;
    private final long expiryInMillis;

    public Value(T value, LocalDateTime creationTime, long expiryInMillis) {
        this.value = value;
        this.creationTime = creationTime;
        this.expiryInMillis = expiryInMillis;
    }

    public T getValue() {
        return value;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public long getExpiryInMillis() {
        return expiryInMillis;
    }

    public boolean isExpired() {
        return this.creationTime.plus(expiryInMillis, ChronoUnit.MILLIS).compareTo(LocalDateTime.now()) < 0;
    }
}
