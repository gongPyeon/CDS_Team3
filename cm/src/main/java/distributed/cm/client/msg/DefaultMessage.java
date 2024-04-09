package distributed.cm.client.msg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
}
