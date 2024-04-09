package distributed.cm.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class ClientMessageResponser {
    public void sendMessageAllSocket(String message, List<WebSocketSession> sessions) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessageSocket(String message, WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage(message));
    }
}
