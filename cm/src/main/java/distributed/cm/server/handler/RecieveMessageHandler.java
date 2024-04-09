package distributed.cm.server.handler;

import distributed.cm.client.msg.*;
import distributed.cm.server.BoardManager;
import distributed.cm.server.ClientMessageResponser;
import distributed.cm.server.domain.Draw;
import distributed.cm.server.domain.User;
import distributed.cm.server.parser.ClientRequestParser;
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
public class RecieveMessageHandler {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final ClientRequestParser clientRequestParser;

    private final ClientResponseParser clientResponseParser;
    private final ClientMessageResponser clientMessageResponser;
    private final BoardManager boardManager;
    public void handle(String sessionId, String payload) throws IOException {
        //요청 메세지 파싱
        Message message = clientRequestParser.parse(payload);

        //응답 메세지
        List<WebSocketSession> sessions = sessionRepository.findAllSessions();
        clientMessageResponser.sendMessageAllSocket(payload, sessions);

        if (message instanceof DefaultMessage) {
            userEntryHandle(sessionId, (DefaultMessage) message);
        } else {
            drawMessageHandle((DrawMessage) message);
        }
    }

    private void userEntryHandle(String sessionId, DefaultMessage message) throws IOException {
        if(message.getEntry() == 1){//퇴장
            userRepository.removeUser(sessionId);
            return;
        }

        User user = new User(sessionId, message.getUserId());
        userRepository.saveUser(sessionId, user);

        Map<String, Object> draws = boardManager.loadBoard();
        String responseMessage = clientResponseParser.createAllDrawsMessage(draws);

        WebSocketSession userSession = sessionRepository.findSession(sessionId);
        clientMessageResponser.sendMessageSocket(responseMessage, userSession);
    }

    private void drawMessageHandle(DrawMessage message) {
        switch (message.getDrawType()){
            case 1,2,4,6 :
                boardManager.saveDraw(message.getDraw());
                break;
            case 3,5 :
                boardManager.editDraw(message.getDraw());
        }
    }
}
