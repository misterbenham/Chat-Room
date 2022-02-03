***Chat Room***

This application requires a server to allow multiple client (user) connections,
enabling them to broadcast messages to every other client (user) in the chat room.
The application must receive a user id, to identify the user connecting and broadcasting
messages.

Server Class:

Firstly, we must create a 'Server' class, that will listen for new client connections,
creating new threads to handle these new connections. To listen to new incoming client
connections, we will use an object class called 'ServerSocket'- waits for requests to
come in over a network, performs an operation based on that request, and then possibly
returns a result to the requester (https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html).

Next, we must make a new method name startServer() which will be responsible for
keeping the server running. A try block uses Java's .accept() method to wait for new client connection
and the prints to console that a new client has connected. When a new connection is made, we create a new
object of a clientHandler class (not yet made), which will be responsible for communicating with that client.
Each client needs its own thread to handle communication. The block will then create a new thread using the
clientHandler class (not yet made) that implements runnable. The catch will run a method, called closeServerSocket()
that closes the serverSocket if the server is no longer running (method not yet made).

Create the closeServerSocket() method that uses .close() to close the server.

Finally, the main method in the server class creates a new serverSocket using a specific port number
(1234). This port number must be what clients use to connect over the network. It then starts the
startServer() method made previously.

ClientHandler Class:

This new class implements runnable so that instances will be executed bny a separate thread. We need to
create a new arrayList that will store all of the clients that have connected to the chatroom server.

A method called clientHandler passes the socket object from the server class and uses a bufferedWriter
(output stream) for sending messages and a bufferedReader (input stream) for reading messages. A
bufferedReader is also used to store the clients user name into a variable called clientUserName. This method
adds this instance into the arrayList and lets the chatroom that the client has joined.

Next, the run() method overrides the runnable interface, as everything in this method will run on a separate
thread. The method will listen for messages from the bufferedReader using a while loop and then send the
message to another method called broadcastMessage (not yet made). The method will listen for messages so long
as the server socket is connected, else it will run the closeEverything() method (not yet made).

The broadcastMessage() method will send the clients message to all other clients in the chat room. The method
loops through all of the clientHandlers in the array list and sends the message to each client connected.

The removeClientHandler() method is called if a client terminates from the server- prints to the chat that the
user has left and removes client from clientHandlers array list.

The closeEverything() method calls the removeClientHandler() method and then closes the bufferedReader,
bufferedWriter and socket. The method uses an if statement to avoid a null pointer exception.

Client Class:

Similarly to the ClientHandler class, we will need a socket, bufferedReader and bufferedWriter. We also need
a String variable that will store client usernames when they connect and input one (userName). A constructor object
for client takes a socket and the userName variable. Again, similarly to the ClientHandler class, we need to
instantiate the socket and then from this object, create the bufferedReader and bufferedWriter. userName is
also instantiated. A catch exception runs the closeEverything() method.

The sendMessage() method uses another try catch statement. A scanner stores the users message into a variable named
'messageToSend' and then the bufferedWriter writes the userName of the client along with their message into the chat.
Again, a catch exception runs the closeEverything() method.

We now need another method that will listen for messages from the server (broadcast messages from other users).
The listenForMessage() method uses a new thread to allow concurrent chat messages to be sent and listened for.
Everything inside this overridden runnable method will be executed on a new thread. While we are still connected
to the server, we read from the bufferedReader (broadcast message 'messageFromGroupChat') and then output it from
the server to the chat.

closeEverything() method- already discussed.

The main method in this class uses a scanner to ask the user for their user name and then stores it into the userName
variable to display to the chat. The socket that makes the connection uses 'localhost' as its host and the same port
number (1234) as the server. We then create a new client object Client that takes the socket and userName to instantiate
the classes already made. Finally, we run our two methods to listen and send messages (listenForMessage() and sendMessage()).


TO USE:

- Run the server
- Run multiple instances of Client (however many clients you wish)
- Enter usernames for each client
- Begin sending messages to the chat
- You will see new client connections, client messages broadcast and notifications telling you when a client has disconnected.






