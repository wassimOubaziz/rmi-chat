# Chat Application with RMI (Remote Method Invocation)

This project is a simple chat application implemented in Java using RMI (Remote Method Invocation) technology. It allows users to connect to a central server and communicate with each other in real-time.

## Features:

- **Client-Server Architecture**: The application follows a client-server architecture where multiple clients can connect to a central server.
- **Real-Time Communication**: Users can send and receive messages in real-time, enabling instant communication between connected clients.
- **User Interface**: The application provides a graphical user interface (GUI) for ease of use. Clients can input messages in a text field and view the chat history in a scrollable area.
- **Connected Users List**: The application displays a list of connected users, allowing clients to select recipients for their messages.
- **Input Validation**: The application validates user input to ensure that messages are not empty before sending.
- **Self-Exclusion**: The connected users list excludes the current user, preventing self-messaging.

## Technologies Used:

- **Java**: The core programming language used for development.
- **RMI (Remote Method Invocation)**: Java RMI is a mechanism that allows an object running in one Java virtual machine to invoke methods on an object running in another Java virtual machine.

## How to Run:

1. **Compile Source Files**: Compile all Java source files in the project using a Java compiler.

```
    javac *.java
```

2. **Start RMI Registry**: Start the RMI registry on the server machine.

```
    start rmiregistry
```

3. **Run Server**: Run the `ChatServer` class on the server machine.

```
    java ChatServer
```

4. **Run Client**: Run the `ChatClient` class on each client machine. Enter your name when prompted to connect to the server.

```
    java ChatClient
```

## Future Enhancements:

- Implement encryption for secure communication.
- Add support for sending multimedia files such as images and documents.
- Enhance the user interface with additional features like emojis or customizable themes.

## Contributors:

- [Wassim Oubaziz](https://www.linkedin.com/in/wassim-oubaziz/)
- [Mounsif Chelgham](https://www.linkedin.com/in/mounsif-chelgham-b94202289/)

## License:

This project is licensed under the [MIT License](License).
