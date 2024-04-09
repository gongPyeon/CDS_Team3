package distributed.cm.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import distributed.cm.client.msg.*;
import distributed.cm.server.domain.Line;
import distributed.cm.server.domain.TextBox;
import distributed.cm.server.parser.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
public class ClientSocketManager {
    private final ClientSocket clientSocket;
    private final ObjectMapper mapper;

    public ClientSocketManager() {
        clientSocket = new ClientSocket();
        clientSocket.openSocket();
        mapper = new ObjectMapper();
    }

    public void draw(int x1, int y1, int x2, int y2) {
        Line line1 = new Line(x1, y1, x2, y2, 1, "#000000");
        LineMessage lineMessage = new LineMessage(1, 1, line1);

        try{
            String message = mapper.writeValueAsString(lineMessage);
            clientSocket.sendMessage(message);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

    }

    public void text(String input, int startX, int startY){
        TextBox textBox = new TextBox(startX, startY, input, "#000000", 1);
        TextBoxMessage textBoxMessage = new TextBoxMessage(1, 6, textBox);

        try{
            String message = mapper.writeValueAsString(textBoxMessage);
            clientSocket.sendMessage(message);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }

    public void shape(int startX, int startY, int endX, int endY){
        clientSocket.sendMessage("drag:(" + startX + ", " + startY +", " + startY + ", " + endY+ ")");
    }

    @Slf4j
    static class MessageHandler implements jakarta.websocket.MessageHandler.Whole<String> {
        private ObjectMapper objectMapper = new ObjectMapper();
        private ClientRequestParser clientRequestParser = new ClientRequestParser(
                objectMapper,
                new LineMessageParser(objectMapper),
                new CircleMessageParser(objectMapper),
                new RectangleMassageParser(objectMapper),
                new TextBoxMessageParser(objectMapper));

        @Override
        public void onMessage(String message) {
            try {
                log.info("onMessage");
                Message socketMessage = clientRequestParser.parse(message);
                if (socketMessage instanceof DefaultMessage) {
                    onDefaultMessage((DefaultMessage) socketMessage);
                } else if (socketMessage instanceof DrawMessage){
                    onDrawMessage((DrawMessage) socketMessage);
                }
            } catch (JsonProcessingException e) {
                log.info("error={}", e);
            }
        }

        /**
         * 다른 유저 입퇴장
         */
        public void onDefaultMessage(DefaultMessage message) {

        }

        /**
         * 다른 유저의 draw 메세지 도착
         */
        public void onDrawMessage(DrawMessage message) {

        }
    }

}