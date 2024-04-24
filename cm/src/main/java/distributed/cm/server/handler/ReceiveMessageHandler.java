package distributed.cm.server.handler;

import distributed.cm.common.message.DefaultMessage;
import distributed.cm.common.message.DrawMessage;
import distributed.cm.common.message.Message;
import distributed.cm.server.board.BoardManager;
import distributed.cm.common.domain.User;
import distributed.cm.common.parser.ClientRequestParser;
import distributed.cm.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceiveMessageHandler {
    private final UserRepository userRepository;
    private final ClientRequestParser clientRequestParser;

    private final BoardManager boardManager;
    public String handle(String sessionId, String payload) throws IOException {
        //요청 메세지 파싱
        Message message = clientRequestParser.parse(payload);
        if (message instanceof DefaultMessage) {
            userEntryHandle(sessionId, (DefaultMessage) message);
        } else {
            drawMessageHandle((DrawMessage) message);
        }
        return payload;
    }

    private void userEntryHandle(String sessionId, DefaultMessage message) throws IOException {
        if(message.getEntry() == 1){//퇴장
            userRepository.removeUser(sessionId);
            return;
        }

        User user = new User(sessionId, message.getUserId());
        userRepository.saveUser(sessionId, user);

        //TODO
        /*Map<String, Object> draws = boardManager.loadBoard();
        String responseMessage = clientResponseParser.createAllDrawsMessage(draws);

        WebSocketSession userSession = sessionRepository.findSession(sessionId);
        clientMessageResponser.sendMessageSocket(responseMessage, userSession);*/


    }

    private void drawMessageHandle(DrawMessage message) {
        switch (message.getDrawType()){
            case 1,2,4,6 :
                boardManager.saveDraw(message.getDraw());
                break;
            case 3,5 :
                boardManager.editDraw(message.getDraw());
                break;
        }
    }
}
