//Cross platform solution to view a PPT File

import java.io.*;
import java.io.FileNotFoundException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.Desktop;
import java.awt.AWTException;
import java.net.*;
import java.lang.String;

public class presentationController {
	InputStreamReader inps= new InputStreamReader(System.in);
	BufferedReader buf= new BufferedReader(inps);
	Socket req;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message,mes;
	Robot robot;
	void run() throws IOException, UnknownHostException, ClassNotFoundException, InterruptedException, AWTException
	{
		robot = new Robot();
		req = new Socket("localhost", 6666);
		System.out.println("Connected to localhost in port 6666");
		out = new ObjectOutputStream(req.getOutputStream());
		out.flush();
		in = new ObjectInputStream(req.getInputStream());
		do {
			message = (String)in.readObject();
			System.out.print("SERVER >> " + message);
			if(message.contains("pptstart"))
				System.out.println("SERVER >> ");
				pptstart();
			if(message.contains("next") )
				System.out.println("SERVER >> ");
				next();
			//else if(message == "previous")
			//	previous();
			//else if(message == "escape")
			//	escape();
			message = buf.readLine();
			out.writeObject(message);
			out.flush();
		} while(!message.equals("bye"));
		in.close();
		out.close();
		req.close();
	}
	void pptstart() throws InterruptedException
	{
		try {
			File pptFile = new File("/home/guest/Desktop/javaNet/Sockets.ppt");
			if (pptFile.exists()) {
 
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(pptFile);
				} else {
					System.out.println("Awt Desktop is not supported!");
				}
 
			} else {
				System.out.println("File is not exists!");
			}
 
				System.out.println("Done");
 
		  } catch (Exception ex) {
			ex.printStackTrace();
		  }
		Thread.sleep(10000);

 		robot.keyPress(KeyEvent.VK_F5);
	    robot.keyRelease(KeyEvent.VK_F5);
	}
	void next() throws InterruptedException
	{
		Thread.sleep(1000);
		robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException, AWTException
	{
		presentationController client = new presentationController();
		client.run();
	}
}
