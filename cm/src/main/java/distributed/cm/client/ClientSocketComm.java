package distributed.cm.client;

import jakarta.websocket.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;

@Slf4j
@ClientEndpoint
public class ClientSocketComm {
    public static final String uri = "ws://localhost:8080/board";
    private Session session;

    public void openSocket(){
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            session = container.connectToServer(ClientSocketComm.class, URI.create(uri));
            log.info("open socket success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(int x, int y) {
        try {
            session.getBasicRemote().sendText("drag:(" + x + ", " + y + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

