//Client chat program  Termianl based

import java.io.*;
import java.net.*;
public class myclient {
	InputStreamReader inps= new InputStreamReader(System.in);
	BufferedReader buf= new BufferedReader(inps);
	Socket req;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message;
	void run() throws IOException, UnknownHostException, ClassNotFoundException
	{
		req = new Socket("localhost", 6666);
		System.out.println("Connected to localhost in port 6666");
		out = new ObjectOutputStream(req.getOutputStream());
		out.flush();
		in = new ObjectInputStream(req.getInputStream());
		do {
			message = (String)in.readObject();
			System.out.println("SERVER >> " + message);
			message = buf.readLine();
			out.writeObject(message);
			out.flush();
		} while(!message.equals("bye"));
		in.close();
		out.close();
		req.close();
	}
	public static void main(String args[]) throws IOException, ClassNotFoundException
	{
		myclient client = new myclient();
		client.run();
	}
}
