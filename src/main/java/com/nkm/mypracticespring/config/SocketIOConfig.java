package com.nkm.mypracticespring.config;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Log4j2
@Configuration
public class SocketIOConfig {

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(9091);
        config.setMaxFramePayloadLength(1024 * 1024 * 50);
//        config.setMaxHttpContentLength(1024 * 1024 * 50);

        // config for socket
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        socketConfig.setTcpNoDelay(false);
        socketConfig.setTcpKeepAlive(true);
        socketConfig.setTcpSendBufferSize(128 * 1024 * 1000);

        config.setSocketConfig(socketConfig);

        SocketIOServer socketIOServer = new SocketIOServer(config);
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(client -> {
            System.out.println("client");
            log.warn("client[{}] is disconnected", client.getSessionId());
        });
        socketIOServer.addEventListener("authen", AuthClientRequest.class, new AuthRequestHandler());
//        socketIOServer.addEventListener("chatevent", ClientRequest.class, clientRequestHandler);
        socketIOServer.start();

        return socketIOServer;
    }

    private ConnectListener onConnected() {
        return (client) -> log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
    }

    @Data
    public static class AuthClientRequest {
        private String token;
    }

    public static class AuthRequestHandler implements DataListener<AuthClientRequest> {
        @Override
        public void onData(SocketIOClient client, AuthClientRequest authReq, AckRequest ackRequest) throws Exception {
            System.out.println(authReq);
            client.sendEvent("authen", new JSONObject(Collections.singletonMap("status", "success")).toString());
        }
    }

}