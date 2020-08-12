package org.dkvs.node.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Failure<T> extends Response<T> {
    private final Error error;

    @JsonCreator
    public Failure(@JsonProperty("data") T data, @JsonProperty("error") Error error) {
        super(data);
        this.error = error;
    }

    public static final class Error {
        private final String errorMessage;

        public Error(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public Error getError() {
        return error;
    }
}
