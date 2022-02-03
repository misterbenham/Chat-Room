import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler>clientHandlers = new ArrayList<>();   // Array List that holds every clientHandler object.
                                                                                // Keeps track of all clients (users).
    private Socket socket;                                                      // Socket object that will be passed from server class.
                                                                                // Used to establish connection between client and server.
    private BufferedReader bufferedReader;                                      // Reads data (messages sent from client).
    private BufferedWriter bufferedWriter;                                      // Sends data (messages sent to client/s).
    private String clientUserName;                                              // String variable to store client usernames.

    public ClientHandler(Socket socket){                                        // Constructor that passes socket objects from server class.
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER " + clientUserName + " has joined the Chat.");
        }catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }                                                                       // this. is the object made from this class.
    }                                                                           // bufferedWriter used for sockets output stream (send messages).
                                                                                // bufferedReader used for sockets input stream (read messages).
                                                                                // client enters their username and is stored into clientUserName, using a bufferedReader.
                                                                                // clientHandlers.add(this) adds the client to the array list.
                                                                                // runs the broadcastMessage() method and prints that client has joined chat in chatroom.
                                                                                // catches exception by running closeEverything() method.
    @Override
    public void run() {                                                         // run method used to send messages.
        String messageFromClient;                                               // String variable messageFromClient holds the message entered by a client.

        while(socket.isConnected()){                                            // isConnected() used to constantly listen for new message.
            try{
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);                            // reads from bufferedReader and runs broadcastMessage() method with the clients message.
            }catch(IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);        // runs closeEverything() method.
                break;                                                          // breaks out of while loop when client disconnects.
            }
        }
    }


    public void broadcastMessage(String messageToSend){                         // method used to broadcast clients messages to all clients in chatroom.
        for(ClientHandler clientHandler : clientHandlers){                      // for each clientHandler in clientHandlers array list:
            try{
                clientHandler.bufferedWriter.write(messageToSend);              // writes the message.
                clientHandler.bufferedWriter.newLine();                         // newLine() sends the message.
                clientHandler.bufferedWriter.flush();                           // flush() deletes message from clientHandler so a new message can be sent.
            }catch(IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);        // runs closeEverything() method.
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER " + clientUserName + " has left the Chat.");
    }
                                                    // Method that removes a client when disconnected and prints that the client has left the chat.

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }                                           // This method closes the socket, bufferedReader and bufferedWriter.
    }
}
