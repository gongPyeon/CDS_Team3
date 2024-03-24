package distributed.cm.client.msg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    int messageType;
    int entry;
    String userId;

    public Message(int messageType, int entry, String userId) {
        this.messageType = messageType;
        this.entry = entry;
        this.userId = userId;
    }
}
