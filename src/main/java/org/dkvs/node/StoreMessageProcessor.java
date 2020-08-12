package org.dkvs.node;

import org.dkvs.node.data.*;
import org.dkvs.node.store.KeyValueStore;
import org.dkvs.tcp.message.processor.MessageProcessor;

public class StoreMessageProcessor implements MessageProcessor<Request<Pair>, Response<Pair>> {

    private final KeyValueStore<String, String> store;
    private final long expiryInMillis;

    public StoreMessageProcessor(KeyValueStore<String, String> store, long expiryInMillis) {
        this.store = store;
        this.expiryInMillis = expiryInMillis;
    }

    @Override
    public Response<Pair> process(Request<Pair> pairRequest) {
        try {
            return new Success<>(Request.Type.GET.equals(pairRequest.getType()) ? get(pairRequest.getData()) : put(pairRequest.getData()));
        } catch (Exception e) {
            return new Failure<>(Pair.empty(), new Failure.Error(e.getMessage()));
        }
    }

    private Pair get(Pair pair) {
        String value = store.get(pair.getKey());
        return new Pair(pair.getKey(), value);
    }

    private Pair put(Pair pair) {
        store.put(pair.getKey(), pair.getValue(), expiryInMillis);
        return pair;
    }
}
