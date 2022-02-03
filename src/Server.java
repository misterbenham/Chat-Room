import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;              // Object that listens for new connections.

    public Server(ServerSocket serverSocket) {      // Constructor for serverSocket.
        this.serverSocket = serverSocket;
    }

    public void startServer(){                      // Method that keeps server running (listening).

        try{                                        // Try block for error handling.
            while(!serverSocket.isClosed()){        // While the serverSocket is not closed:
                System.out.println("Server running...");
                Socket socket = serverSocket.accept();                   // .accept() method used to wait for connection.
                System.out.println("A new user has connected");          // once a new connection is made, print line.
                ClientHandler clientHandler = new ClientHandler(socket); // creates a new clientHandler object (clientHandler class).
                                                                         // each object will be responsible for communicating with a client
                Thread thread = new Thread(clientHandler);               // creates a new instance of clientHandler that implements runnable thread.
                thread.start();                                          // begins thread execution.

            }
        } catch(IOException e){                                          // handles errors:
            closeServerSocket();                                         // runs closeServerSocket method below:
        }
    }

    public void closeServerSocket(){                                     // method that closes server socket.
        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {           // main method with exception for errors.

        ServerSocket serverSocket = new ServerSocket(1234);          // Creates new serverSocket, passing a port number for network connection.
        Server server = new Server(serverSocket);
        server.startServer();                                             // runs the startServer method to keep server running.
    }
}
