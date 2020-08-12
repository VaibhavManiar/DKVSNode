package org.dkvs.node.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Request<T> {
    private final Type type;
    private final T data;

    @JsonCreator
    public Request(@JsonProperty("type") Type type, @JsonProperty("data") T data) {
        this.type = type;
        this.data = data;
    }

    public enum Type {
        GET, PUT
    }

    public Type getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}