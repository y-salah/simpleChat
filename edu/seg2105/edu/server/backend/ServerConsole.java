package edu.seg2105.edu.server.backend;

import java.util.Scanner;

import edu.seg2105.client.common.ChatIF;

public class ServerConsole implements ChatIF {
	
	final public static int DEFAULT_PORT = 5555;
	
	EchoServer server;
	
	Scanner fromConsole;
	
	public ServerConsole(int port) {
		
		server = new EchoServer(port);
	      
	    fromConsole = new Scanner(System.in);
	}
	
	public void accept() {
		try
	    {

	      String message;

	      while (true) 
	      {
	        message = fromConsole.nextLine();
	        server.handleMessageFromServerUI(message);
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	        ("Unexpected error while reading from console!");
	    }
	}

	@Override
	public void display(String message) {
		System.out.println("SERVER MSG> " + message);
	}
	
	public static void main(String[] args) {
		int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
	    
	    ServerConsole sv = new ServerConsole(port);
	    
	    try 
	    {
	      sv.server.listen(); //Start listening for connections
	      sv.accept();
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println("ERROR - Could not listen for clients!");
	    }
	}
}
