package distributed.cm.server.handler;

import distributed.cm.common.domain.User;
import distributed.cm.server.parser.ClientResponseParser;
import distributed.cm.server.repository.SessionRepository;
import distributed.cm.server.repository.UserRepository;
import distributed.cm.server.responser.MessageSender;
import distributed.cm.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntrySocketHandler {

    private final ClientResponseParser clientResponseParser;
    private final UserService userService;
    private final MessageSender messageSender;

    private final SessionRepository sessionRepository;

    public void openSocketHandle(String sessionId, WebSocketSession session) {
        sessionRepository.saveSession(sessionId, session);
    }

    public void closeSocketHandle(String sessionId) {
        sessionRepository.removeSession(sessionId);

        if(userService.findUser(sessionId) != null){
            String exitUserId = userService.userExit(sessionId);
            String closeSocketMessage = clientResponseParser.createCloseSocketMessage(exitUserId);
            messageSender.sendMessageAllSocket(closeSocketMessage);
        }
    }
}
