package distributed.cm;
import distributed.cm.server.BoardManager;
import distributed.cm.server.ClientMessageResponser;
import distributed.cm.server.handler.EntrySocketHandler;
import distributed.cm.server.handler.RecieveMessageHandler;
import distributed.cm.server.handler.SocketHandler;
import distributed.cm.server.parser.ClientRequestParser;
import distributed.cm.server.parser.ClientResponseParser;
import distributed.cm.server.repository.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler(), "/board").setAllowedOrigins("*");
    }

    @Bean
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
    }
}
