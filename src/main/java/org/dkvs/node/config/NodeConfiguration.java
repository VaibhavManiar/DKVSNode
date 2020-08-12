package org.dkvs.node.config;

import org.dkvs.node.StoreMessageProcessor;
import org.dkvs.node.data.Pair;
import org.dkvs.node.data.Request;
import org.dkvs.node.data.Response;
import org.dkvs.node.store.DefaultKeyValueStore;
import org.dkvs.node.store.KeyValueStore;
import org.dkvs.tcp.config.Configuration;
import org.dkvs.tcp.message.parser.MessageParser;
import org.dkvs.tcp.message.processor.MessageProcessor;
import org.dkvs.tcp.server.ServerProperties;

import java.util.Optional;

public class NodeConfiguration implements Configuration<Request<Pair>, Response<Pair>> {

    private final ServerProperties serverProperties;
    private final KeyValueStore<String, String> keyValueStore;

    public NodeConfiguration(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
        this.keyValueStore = DefaultKeyValueStore.create(serverCapacity());
    }

    @Override
    public MessageProcessor<Request<Pair>, Response<Pair>> getMessageProcessor() {
        return new StoreMessageProcessor(keyValueStore, storeEntryExpiry());
    }

    @Override
    public MessageParser<Request<Pair>> getRequestParser() {
        return null;
    }

    @Override
    public MessageParser<Response<Pair>> getResponseParser() {
        return null;
    }

    @Override
    public int port() {
        return Integer.parseInt(serverProperties.getPortIfConfigured().orElse("8661"));
    }

    public int serverCapacity() {
        return Integer.parseInt(Optional.ofNullable(serverProperties.getProperties().getProperty("store.capacity")).orElse("10"));
    }

    public long storeEntryExpiry() {
        return Integer.parseInt(Optional.ofNullable(serverProperties.getProperties().getProperty("store.data.expiry")).orElse("1000"));
    }
}
