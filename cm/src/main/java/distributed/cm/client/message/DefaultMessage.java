package distributed.cm.client.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import distributed.cm.server.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultMessage implements Message{
    private int messageType;

    private int entry;

    private int drawType;

    private String userId;

    private User usr;

    public DefaultMessage(int messageType, int entry){
        this.messageType = messageType;
        this.entry = entry;
    }

    public DefaultMessage() {
    }
}
