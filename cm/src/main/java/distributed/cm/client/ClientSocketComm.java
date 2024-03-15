package distributed.cm.client;

import java.io.IOException;

public class ClientSocketComm {
    private final ClientSocket clientSocket;

    public ClientSocketComm() {
        clientSocket = new ClientSocket();
        clientSocket.openSocket();
    }

    public void draw(int x, int y) {
        clientSocket.sendMessage("drag:(" + x + ", " + y + ")");
    }
}
