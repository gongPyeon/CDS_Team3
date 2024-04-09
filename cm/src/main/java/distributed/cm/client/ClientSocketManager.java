package distributed.cm.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import distributed.cm.client.msg.DefaultMessage;
import distributed.cm.client.msg.DrawMessage;
import distributed.cm.client.msg.LineMessage;
import distributed.cm.client.msg.Message;
import distributed.cm.server.ClientMessageResponser;
import distributed.cm.server.domain.Line;
import distributed.cm.server.parser.*;
import jakarta.websocket.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.awt.*;
import java.io.IOException;
import java.util.List;

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
            System.out.println("message = " + message);
            clientSocket.sendMessage(message);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

    }

    public void text(String input, int startX, int startY){
        clientSocket.sendMessage("text:(" + input + ", " + startX +", " + startY + ")");
    }

    public void shape(int startX, int startY, int endX, int endY){
        clientSocket.sendMessage("drag:(" + startX + ", " + startY +", " + startY + ", " + endY+ ")");
    }

    static class MessageHandler implements jakarta.websocket.MessageHandler.Whole<String> {
        private ObjectMapper objectMapper = new ObjectMapper();
        private ClientRequestParser clientRequestParser = new ClientRequestParser(
                objectMapper,
                new LineMessageParser(objectMapper),
                new CircleMessageParser(objectMapper),
                new RectangleMassageParser(objectMapper),
                new TextBoxMessageParser(objectMapper));
        private ClientResponseParser clientResponseParser;

        @Override
        public void onMessage(String message) {
            log.info("message={}", message);
            Message socketMessage = null;
            try {
                socketMessage = clientRequestParser.parse(message);
            } catch (JsonProcessingException e) {
            }

            if (socketMessage instanceof DefaultMessage) {
                System.out.println((DefaultMessage) socketMessage);
            } else {
                System.out.println((DrawMessage) socketMessage);
            }
        }

    }

}