package distributed.cm.server.handler;

import distributed.cm.common.domain.User;
import distributed.cm.server.parser.ClientResponseParser;
import distributed.cm.server.repository.SessionRepository;
import distributed.cm.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntrySocketHandler {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final ClientResponseParser clientResponseParser;

    public void openSocketHandle(String sessionId, WebSocketSession session) throws IOException {
        //세션
        sessionRepository.saveSession(sessionId, session);
    }

    public String closeSocketHandle(String sessionId) throws IOException {
        sessionRepository.removeSession(sessionId);
        User user = userRepository.findUserBySessionId(sessionId);
        if (user != null) {
            userRepository.removeUser(sessionId);
            String closeSocketMessage = clientResponseParser.createCloseSocketMessage(user.getUserId());
            return closeSocketMessage;
        }
        return null;
    }
}
