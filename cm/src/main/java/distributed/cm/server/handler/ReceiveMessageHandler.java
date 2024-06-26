package distributed.cm.server.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import distributed.cm.common.domain.Draw;
import distributed.cm.common.message.DefaultMessage;
import distributed.cm.common.message.DrawMessage;
import distributed.cm.common.message.Message;
import distributed.cm.server.parser.ClientResponseParser;
import distributed.cm.server.responser.MessageSender;
import distributed.cm.server.service.BoardService;
import distributed.cm.server.parser.ClientRequestParser;
import distributed.cm.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceiveMessageHandler {

    private final ClientRequestParser clientRequestParser;
    private final ClientResponseParser clientResponseParser;

    private final BoardService boardService;
    private final UserService userService;

    private final MessageSender messageSender;

    public void handle(String sessionId, String payload){
        //요청 메세지 파싱
        try {
            Message message = clientRequestParser.parse(payload);
            if (message instanceof DefaultMessage) {
                DefaultMessage defaultMessage = (DefaultMessage) message;
                switch (defaultMessage.getMessageType()){
                    case 0:
                        userEntryHandle(sessionId, defaultMessage, payload);
                        break;
                    case 2, 3:
                        loadSaveHandle(sessionId, defaultMessage, payload);
                        break;
                }
            } else {
                drawMessageHandle((DrawMessage) message, sessionId, payload);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void userEntryHandle(String sessionId, DefaultMessage message, String payload) {
        if(message.getEntry() == 0) {
            userService.userEnter(sessionId, message);

            List<Draw> draws = boardService.loadBoard();
            String loadDrawMessage = clientResponseParser.createLoadDrawMessage(draws);
            messageSender.sendMessage(sessionId, loadDrawMessage);
        }
        else if (message.getEntry() == 1) userService.userExit(sessionId);

        messageSender.sendMessageAllSocket(payload, sessionId); //유저 입장 메세지 -> 해당 유저 제외한 모든 유저
    }

    private void drawMessageHandle(DrawMessage message, String sessionId, String payload) {
        switch (message.getDrawType()){
            case 1,2,4,6 :
                boardService.saveDraw(message.getDraw());
                messageSender.sendMessageAllSocket(payload, sessionId);
                break;
            case 3,5 :
                boolean isEdit = boardService.editDraw(message.getDraw(), sessionId);
                if(isEdit) {
                    messageSender.sendMessageAllSocket(payload);
                } else {
                    String responseMessage = clientResponseParser.createEditErrorMessage();
                    messageSender.sendMessage(sessionId, responseMessage);
                }
            case 7,8 :
                boolean isSelect = boardService.selectDraw(message.getDraw(), sessionId);
                if(isSelect) {
                    messageSender.sendMessageAllSocket(payload);
                } else {
                    String responseMessage = clientResponseParser.createEditErrorMessage();
                    messageSender.sendMessage(sessionId, responseMessage);
                }
            case 9,10:
                messageSender.sendMessageAllSocket(payload, sessionId);
        }
    }

    private void loadSaveHandle(String sessionId, DefaultMessage message, String payload){
        if(message.getMessageType() == 2){
            List<Draw> draws = boardService.loadFormalBoard();
            String responseMessage = clientResponseParser.createLoadDrawMessage(draws);
            messageSender.sendMessageAllSocket(responseMessage);
        }else if (message.getMessageType() == 3){
            boardService.saveBoard();
        }
    }
}
