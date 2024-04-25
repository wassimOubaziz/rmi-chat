import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {
    private static final long serialVersionUID = 1L;
    private Map<String, ChatClientInterface> connectedClients;

    protected ChatServer() throws RemoteException {
        super();
        connectedClients = new HashMap<>();
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1098);
            ChatServer server = new ChatServer();
            registry.rebind("ChatServer", server);
            System.out.println("Server is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void registerClient(String name, ChatClientInterface client) throws RemoteException {
        connectedClients.put(name, client);
        System.out.println(name + " connected.");
    }

    @Override
    public synchronized void broadcastMessage(String sender, String message) throws RemoteException {
        for (ChatClientInterface client : connectedClients.values()) {
            client.receiveMessage(sender, message);
        }
    }

    @Override
    public synchronized List<String> getConnectedClients() throws RemoteException {
        return new ArrayList<>(connectedClients.keySet());
    }

    @Override
    public synchronized void sendMessageToClient(String sender, String receiver, String message) throws RemoteException {
        ChatClientInterface client = connectedClients.get(receiver);
        if (client != null) {
            client.receiveMessage(sender, message);
        } else {
            System.out.println("Client " + receiver + " not found.");
        }
    }
}
