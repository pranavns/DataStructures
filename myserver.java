//Server Chat program : Terminal Based

import java.io.*;
import java.net.*;
public class myserver
{
	InputStreamReader inps= new InputStreamReader(System.in);
	BufferedReader buf= new BufferedReader(inps);
	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message, serverTalk;
	void run() throws IOException
	{
		providerSocket = new ServerSocket(6666, 10);
		System.out.println("Waiting for connection");
		connection = providerSocket.accept();
		System.out.println("Connection received from "+connection.getInetAddress().getHostName());
		out = new ObjectOutputStream(connection.getOutputStream());
		out.flush();
		in = new ObjectInputStream(connection.getInputStream());
		sendMessage("Connection successful");
		do{
			try{
				message = (String)in.readObject();
				System.out.println("CLIENT >> " + message);
				serverTalk = buf.readLine();
				sendMessage(serverTalk);
				if (message.equals("bye"))
					sendMessage("bye");
			} catch(ClassNotFoundException classnot) {
				System.err.println("Data received in unknown format");
			}
		} while(!message.equals("bye"));
		in.close();
		out.close();
		providerSocket.close();
	}
	void sendMessage(String msg) throws IOException
	{
		out.writeObject(msg);
		out.flush();
	}
	public static void main(String args[]) throws IOException
	{
		myserver server = new myserver();
		server.run();
	}
}


