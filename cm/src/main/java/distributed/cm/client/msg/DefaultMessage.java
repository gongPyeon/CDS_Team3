package distributed.cm.client.msg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultMessage implements Message{
    private int messageType;

    private int entry;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private int drawType;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String userId;
}
