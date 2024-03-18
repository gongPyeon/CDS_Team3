package distributed.cm.server.handler;

import distributed.cm.server.BoardManager;
import distributed.cm.server.ClientMessageResponser;
import distributed.cm.server.domain.Shape;
import distributed.cm.server.parser.ClientRequestParser;
import distributed.cm.server.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecieveMessageHandler {
    private final SessionRepository sessionRepository;
    private final ClientRequestParser clientRequestParser;
    private final ClientMessageResponser clientMessageResponser;
    private final BoardManager boardManager;
    public void recieveMessage(String sessionId, String payload) {
        //요청 메세지 파싱
        Shape shape = null; //파싱값

        //응답 메세지
        List<WebSocketSession> sessions = sessionRepository.findAllSessions();
        String message = payload;
        clientMessageResponser.sendMessageAllSocket(message, sessions);

        //boardManager로 보드 저장
//        boardManager.saveBoard(shape);
    }
}
