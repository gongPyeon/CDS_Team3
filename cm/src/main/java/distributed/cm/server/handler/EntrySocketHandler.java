package distributed.cm.server.handler;

import distributed.cm.server.BoardManager;
import distributed.cm.server.ClientMessageResponser;
import distributed.cm.server.domain.User;
import distributed.cm.server.parser.ClientResponseParser;
import distributed.cm.server.repository.SessionRepository;
import distributed.cm.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntrySocketHandler {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final ClientMessageResponser clientMessageResponser;
    private final ClientResponseParser clientResponseParser;
    private final BoardManager boardManager;

    public void openSocketHandle(String sessionId, WebSocketSession session) throws IOException {
        //세션
        sessionRepository.saveSession(sessionId, session);
    }

    public void closeSocketHandle(String sessionId, WebSocketSession session) throws IOException {
        User user = userRepository.findUserBySessionId(sessionId);
        if (user != null) {
            userRepository.removeUser(sessionId);
            String closeSocketMessage = clientResponseParser.createCloseSocketMessage(user.getUserId());
            List<WebSocketSession> sessions = sessionRepository.findAllSessions();
            clientMessageResponser.sendMessageAllSocket(closeSocketMessage, sessions, sessionId);
        }
        sessionRepository.removeSession(sessionId);
    }
}
