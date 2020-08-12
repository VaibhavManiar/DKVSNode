package org.dkvs.node;

import org.dkvs.node.config.NodeConfiguration;
import org.dkvs.tcp.server.ServerProperties;
import org.dkvs.tcp.server.TcpServerBootstrap;

public class Bootstrap {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        TcpServerBootstrap.main(new String[0]);
    }
}
