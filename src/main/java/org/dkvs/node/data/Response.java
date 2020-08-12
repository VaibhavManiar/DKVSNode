package org.dkvs.node.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Response<T> {
    private final T data;

    @JsonCreator
    public Response(@JsonProperty("data") T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
