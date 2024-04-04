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

    public void draw(int x1, int y1, int x2, int y2) {
        clientSocket.sendMessage("drag:(" + x1 + ", " + y1 +", " + x2 + ", " + y2+ ")");
    }

    public void text(String input, int startX, int startY){
        clientSocket.sendMessage("text:(" + input + ", " + startX +", " + startY + ")");
    }

    public void shape(int startX, int startY, int endX, int endY){
        clientSocket.sendMessage("drag:(" + startX + ", " + startY +", " + startY + ", " + endY+ ")");
    }

    static class MessageHandler implements jakarta.websocket.MessageHandler.Whole<String> {
        @Override
        public void onMessage(String message) {
            log.info("message={}", message);
        }
    }

}