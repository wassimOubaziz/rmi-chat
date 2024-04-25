import java.rmi.*;
import java.util.List;

public interface ChatServerInterface extends Remote {
    void registerClient(String name, ChatClientInterface client) throws RemoteException;

    void broadcastMessage(String sender, String message) throws RemoteException;

    List<String> getConnectedClients() throws RemoteException;

    void sendMessageToClient(String sender, String receiver, String message) throws RemoteException;
}
