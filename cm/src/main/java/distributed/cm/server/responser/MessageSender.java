package distributed.cm.server.responser;

import distributed.cm.server.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageSender {

    private final SessionRepository sessionRepository;
    private final Lock lock = new ReentrantLock();

    public void sendMessageAllSocket(String message, String exceptId) {
        lock.lock();
        try {
            List<WebSocketSession> sessions = sessionRepository.findAllSessions();
            for (WebSocketSession session : sessions) {
                try {
                    if(session.getId().equals(exceptId)) continue;
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void sendMessageAllSocket(String message) {
        lock.lock();
        try {
            List<WebSocketSession> sessions = sessionRepository.findAllSessions();
            TextMessage textMessage = new TextMessage(message);

            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(textMessage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void sendMessage(String sessionId, String message){
        lock.lock();
        try {
            WebSocketSession session = sessionRepository.findSession(sessionId);
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            lock.unlock();
        }
    }
}
