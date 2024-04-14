package distributed.cm.server;

import distributed.cm.server.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClientMessageResponser {

    private final SessionRepository sessionRepository;

    public void sendMessageAllSocket(String message, String exceptId) {
        if (message == null) return;

        List<WebSocketSession> sessions = sessionRepository.findAllSessions();
        for (WebSocketSession session : sessions) {
            try {
                if(session.getId().equals(exceptId)) continue;
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessageSocket(String message, WebSocketSession session) throws IOException {
        if (message == null) return;
        session.sendMessage(new TextMessage(message));
    }
}
