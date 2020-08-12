package org.dkvs.node.store;

public class DefaultKeyValueStore extends KeyValueStore<String, String> {

    private static DefaultKeyValueStore keyValueStore;

    private DefaultKeyValueStore(int maxCapacity) {
        super(maxCapacity);
    }

    public static KeyValueStore<String, String> create(int maxCapacity) {
        keyValueStore = new DefaultKeyValueStore(maxCapacity);
        return keyValueStore;
    }

    public static KeyValueStore<String, String> instance() {
        return keyValueStore;
    }

}
