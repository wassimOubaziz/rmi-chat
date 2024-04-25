import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChatClient extends JFrame {
    private ChatServerInterface server;
    private JTextArea chatArea;
    private JTextField messageField;
    private JComboBox<String> connectedUsersComboBox;

    public ChatClient(String name) {
        super(name);

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1098);
            server = (ChatServerInterface) registry.lookup("ChatServer");
            server.registerClient(name, new ChatClientImpl());

            setupUI();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private void setupUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        messageField.addActionListener(e -> sendMessage());
        bottomPanel.add(messageField, BorderLayout.CENTER);

        connectedUsersComboBox = new JComboBox<>();
        bottomPanel.add(connectedUsersComboBox, BorderLayout.SOUTH);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        updateConnectedUsers();

        setVisible(true);
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            String receiver = (String) connectedUsersComboBox.getSelectedItem();
            try {
                server.sendMessageToClient(getTitle(), receiver, message);
                chatArea.append("You: " + message + "\n");
                messageField.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateConnectedUsers() {
        try {
            List<String> connectedUsers = server.getConnectedClients();
            connectedUsersComboBox.removeAllItems();
            for (String user : connectedUsers) {
                if (!user.equals(getTitle())) { // Exclude self from list
                    connectedUsersComboBox.addItem(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ChatClientImpl extends UnicastRemoteObject implements ChatClientInterface {
        private static final long serialVersionUID = 1L;

        protected ChatClientImpl() throws RemoteException {
            super();
        }

        @Override
        public void receiveMessage(String sender, String message) throws RemoteException {
            chatArea.append(sender + ": " + message + "\n");
        }
    }

    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog("Enter your name:");
        if (name != null && !name.isEmpty()) {
            SwingUtilities.invokeLater(() -> new ChatClient(name));
        }
    }
}
