package distributed.cm.client;

import jakarta.websocket.MessageHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ClientSocketManager {
    private final ClientSocket clientSocket;

    public ClientSocketManager() {
        clientSocket = new ClientSocket();
        clientSocket.openSocket();
    }

    public void draw(int x, int y) {
        clientSocket.sendMessage("drag:(" + x + ", " + y + ")");
    }

    static class MessageHandler implements jakarta.websocket.MessageHandler.Whole<String> {
        @Override
        public void onMessage(String message) {
            log.info("message={}", message);
        }
    }

}