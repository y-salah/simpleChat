package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.IOException;

import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  @Override
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println(client+" has connected to the server.");
  }
  
  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  // Since we don't track which ID belongs to this client directly,
	  // remove by value.
	  System.out.println(client+" has disconnected from the server.");
	  super.clientDisconnected(client);
  }
  
  public void handleMessageFromServerUI(String message) throws IOException {
  
    if(message.startsWith("#")) {
    	handleCommand(message);
    }
    else {
    	System.out.println("SERVER MSG> " + message);
    	this.sendToAllClients("SERVER MSG> " + message);
    }		
  }
  
  private void handleCommand(String command) throws IOException {
	  if(command.equals("#quit")) {
		  quit();
	  }
	  else if(command.equals("#stop")) {
		  stopListening();
	  }
	  else if(command.equals("#close")) {
		  close();
	  }
	  else if(command.startsWith("#setport")) {
		  if(!isListening() && (getNumberOfClients() == 0)) {
			  int port = Integer.parseInt(command.substring(8).trim());
			  setPort(port);
		  }
		  else {
			  System.out.println("The server must be closed to set port.");
		  }
	  }
	  else if(command.equals("#getport")) {
		  System.out.println(Integer.toString(getPort()));
	  }
	  else if(command.equals("#start")) {
		  if(!isListening()) {
			  listen();
		  }
		  else {
			  System.out.println("The server must be stopped to start listening for new clients.");
		  }
	  }
  }
  
  public void quit()
  {
    try
    {
      close();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  
  
  //Class methods ***************************************************
  
}
//End of EchoServer class
