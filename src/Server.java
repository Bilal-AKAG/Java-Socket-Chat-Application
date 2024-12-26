import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean running;
    private ExecutorService pool;
    public Server() {
        connections = new ArrayList<>();
        running = false;
    }

    @Override
    public void run() {

        try {
            server = new ServerSocket(8000);
            pool= Executors.newCachedThreadPool();
            while(!running) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (Exception e) {
            shutdown();
        }
    }
    public void broadcast(String message) {
        for (ConnectionHandler handler : connections) {
            if(handler != null) {
                handler.sendMessage(message);
            }
        }
    }
    public void shutdown() {
        try {
            running = true;
            if(!server.isClosed()) {
                server.close();
            }
        } catch (IOException e) {
            //TODO HANDLE
        }

    }
    class ConnectionHandler implements Runnable {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String username;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(), true);
                out.println("Please enter your username: ");
                username = in.readLine();
                System.out.println(username + " has connected");
                broadcast(username + " has connected");
                String message;
                while ((message = in.readLine()) != null) {
                    if(message.startsWith("/username")){
                        String[] parts = message.split(" ",2);
                        if(parts.length == 2) {

                            String oldUsername = username;
                            username = parts[1];
                            broadcast(oldUsername + " is now known as " + username);
                            System.out.println("Succesfully changed username " + username);
                            out.println("Succesfully changed username " + username);
                        }
                        else {
                            out.println("No username provided");
                        }

                    } else if (message.startsWith("/quit")) {
                        broadcast(username + " has disconnected");
                        shutdown();

                    } else {
                        broadcast(username + ": " + message);

                    }


                }
            } catch (IOException e) {
                shutdown();
            }
        }
        public void sendMessage(String message) {
            out.println(message);
        }
        public void shutdown() {

            try {
                in.close();
                out.close();
                if(!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {

            }
        }
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

}
