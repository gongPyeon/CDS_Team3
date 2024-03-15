package distributed.cm.client;

import jakarta.websocket.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;

@Slf4j
@ClientEndpoint
public class ClientSocket {
    public static final String uri = "ws://localhost:8080/board";
    private Session session;

    public void openSocket(){
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            session = container.connectToServer(ClientSocket.class, URI.create(uri));
            log.info("open socket success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

