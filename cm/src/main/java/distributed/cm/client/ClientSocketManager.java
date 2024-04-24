package distributed.cm.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import distributed.cm.client.msg.*;
import distributed.cm.client.swing.SwingClient;
import distributed.cm.server.domain.*;
import distributed.cm.server.parser.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

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


    public void circle(int x1, int x2, int y1, int y2, int bold, String boldColor, String paintColor){
        Circle circle = new Circle(x1, x2, y1, y2, bold, boldColor, paintColor);
        CircleMessage circleMessage = new CircleMessage(1, 2, circle);
        try{
            String message = mapper.writeValueAsString(circleMessage);
            clientSocket.sendMessage(message);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

    }

    public void circleEdit(int x1, int x2, int y1, int y2, int bold, String boldColor, String paintColor){
        Circle circle = new Circle(x1, x2, y1, y2, bold, boldColor, paintColor);
        CircleMessage circleMessage = new CircleMessage(1, 3, circle);
        try{
            String message = mapper.writeValueAsString(circleMessage);
            clientSocket.sendMessage(message);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

    }

    public void rectangle(int x1, int x2, int y1, int y2, int bold, String boldColor, String paintColor){
        Square square = new Square(x1, x2, y1, y2, bold, boldColor, paintColor);
        SquareMessage squareMessage = new SquareMessage(1, 4, square);
        try{
            String message = mapper.writeValueAsString(squareMessage);
            clientSocket.sendMessage(message);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }

    public void rectangleEdit(int x1, int x2, int y1, int y2, int bold, String boldColor, String paintColor){
        Square square = new Square(x1, x2, y1, y2, bold, boldColor, paintColor);
        SquareMessage squareMessage = new SquareMessage(1, 5, square);
        try{
            String message = mapper.writeValueAsString(squareMessage);
            clientSocket.sendMessage(message);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }


    public void userLogin(String usrId){
        /*User user = new User("0", usrId);
        DefaultMessage defaultMessage = new DefaultMessage(0, 0);*/
        UserEntryMessage userEntryMessage = new UserEntryMessage(0, 0, usrId);
//        defaultMessage.setUserId(user.getUserId());
        try{
            String message = mapper.writeValueAsString(userEntryMessage);
            clientSocket.sendMessage(message);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }

    public void userLogout(String usrId){
        Map<String, Object> userMessage = Map.of("messageType", 0, "entry", 1, "userId", usrId);
//        defaultMessage.setUserId(user.getUserId());
        try{
            String message = mapper.writeValueAsString(userMessage);
            clientSocket.sendMessage(message);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }

    @Slf4j
    static class MessageHandler implements jakarta.websocket.MessageHandler.Whole<String> {
        private ObjectMapper objectMapper = new ObjectMapper();
        private ClientRequestParser clientRequestParser = new ClientRequestParser(
                objectMapper,
                new LineMessageParser(objectMapper),
                new CircleMessageParser(objectMapper),
                new SquareMassageParser(objectMapper),
                new TextBoxMessageParser(objectMapper));

        @Override
        public void onMessage(String message) {
            try {
                log.info("onMessage={}", message);
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
            SwingClient client = SwingClient.getClient();
            if(message.getEntry() == 0){
                client.panelLogin(message.getUserId());
            }else{
                client.panelLogout(message.getUserId());
            }
        }

        /**
         * 다른 유저의 draw 메세지 도착
         */
        public void onDrawMessage(DrawMessage message) {
            SwingClient client = SwingClient.getClient();
            switch (message.getDrawType()){
                case 1,2,4,6 :
                    client.panelDraw(message.getDraw());
                    break;
                case 3,5 :
                    client.panelEdit(message.getDraw());
            }

        }
    }

}