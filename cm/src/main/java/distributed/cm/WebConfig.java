package distributed.cm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebConfig implements WebSocketConfigurer {

    @Autowired private WebSocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/board").setAllowedOrigins("*");
    }

/*    @Bean
    public WebSocketHandler socketHandler(){
        return new SocketHandler(
                new EntrySocketHandler(
                        new SessionRepository(),
                        new ClientMessageResponser(),
                        new ClientResponseParser(),
                        new BoardManager(
                                new LineRepository(),
                                new CircleRepository(),
                                new SquareRepository(),
                                new TextBoxRepository()
                        )),
                new RecieveMessageHandler(
                        new SessionRepository(),
                        new ClientRequestParser(),
                        new ClientMessageResponser(),
                        new BoardManager(
                                new LineRepository(),
                                new CircleRepository(),
                                new SquareRepository(),
                                new TextBoxRepository()
                        ))
        );
    }*/
}
