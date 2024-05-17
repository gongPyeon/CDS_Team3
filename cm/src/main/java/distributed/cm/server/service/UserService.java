package distributed.cm.server.service;

import distributed.cm.common.domain.User;
import distributed.cm.common.message.DefaultMessage;
import distributed.cm.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void userEnter(String sessionId, DefaultMessage message){
        User user = new User(sessionId, message.getUserId());
        userRepository.saveUser(sessionId, user);
    }

    public String userExit(String sessionId){
        return userRepository.removeUser(sessionId);
    }

    public User findUser(String sessionId){
        return userRepository.findUserBySessionId(sessionId);
    }
}
