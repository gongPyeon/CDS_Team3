package distributed.cm.server.handler;

import distributed.cm.server.BoardManager;
import distributed.cm.server.ClientMessageResponser;
import distributed.cm.server.parser.ClientResponseParser;
import distributed.cm.server.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntrySocketHandler {

    private final SessionRepository sessionRepository;
    private final ClientMessageResponser clientMessageResponser;
    private final ClientResponseParser clientResponseParser;
    private final BoardManager boardManager;

    public void openSocket(String sessionId, WebSocketSession session) {
        //세션
        sessionRepository.saveSession(sessionId, session);

        //응답 메세지
        List<WebSocketSession> sessions = sessionRepository.findAllSessions();
        String message = clientResponseParser.createOpenSocketMessage(sessionId);
        clientMessageResponser.sendMessageAllSocket(message, sessions);

        //보드 불러오기
        boardManager.loadBoard();
    }

    public void closeSocket(String sessionId){
        sessionRepository.removeSession(sessionId);

//        List<WebSocketSession> sessions = sessionRepository.findAllSessions();
//        String message = clientResponseParser.createCloseSocketMessage(sessionId);
//        clientMessageResponser.sendMessageAllSocket(message, sessions);
    }
}
