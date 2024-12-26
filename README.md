# Java Chat Server

This project is a simple chat server and client implementation in Java. It allows multiple clients to connect to a server and communicate with each other in real-time.

## Features

- **Multi-threaded Server**: Handles multiple client connections simultaneously.
- **Broadcast Messaging**: Clients can send messages to the server, which broadcasts them to all connected clients.
- **Username Management**: Clients can change their username.
- **Graceful Disconnection**: Clients can disconnect from the server.

## Requirements

- Java Development Kit (JDK) 8 or higher
- IntelliJ IDEA or any other Java IDE

## Getting Started

### Clone the Repository

```sh
git https://github.com/Bilal-AKAG/Java-Socket-Chat-Application.git
cd Java-Socket-Chat-Application
```
## Build the Project
Open the project in IntelliJ IDEA and build it using the IDE's build tools.
## Running the Server
To start the server, run the Server class located in src/Server.java.

```
cd src
javac Server.java
java Server
```
## Running the Client
To start a client, run the Client class located in src/Client.java.

```
cd src
javac Client.java
java Client
```
### Usage
- Connect to Server: When a client connects, they will be prompted to enter a username.
- Send Messages: Clients can send messages which will be broadcast to all connected clients.
- Change Username: Clients can change their username by typing /username <new_username>.
- Disconnect: Clients can disconnect by typing /quit.

### Server
The Server class handles incoming client connections and manages broadcasting messages to all connected clients. It uses a thread pool to manage client connections efficiently.  
### Client
The Client class connects to the server and allows the user to send and receive messages. It runs an InputHandler thread to handle user input and send messages to the server.
