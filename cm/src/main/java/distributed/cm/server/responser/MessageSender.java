package distributed.cm.server.responser;

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
public class MessageSender {

    private final SessionRepository sessionRepository;

    public void sendMessageAllSocket(String message, String exceptId) {
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

    public void sendMessageAllSocket(String message) {
        List<WebSocketSession> sessions = sessionRepository.findAllSessions();
        TextMessage textMessage = new TextMessage(message);

        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(textMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessage(String sessionId, String message){
        WebSocketSession session = sessionRepository.findSession(sessionId);
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
