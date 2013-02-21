//Server side program to send consecutive screenshots into the client

import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;

public class FileServer {

	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssa");
	static String screenshotName;

	public void robo() throws Exception
    {
        Calendar now = Calendar.getInstance();
        Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		screenshotName = "screen"+formatter.format(now.getTime())+".jpg";
		System.out.println(screenshotName);
        ImageIO.write(screenShot, "JPG", new File(screenshotName));
        System.out.println(formatter.format(now.getTime()));
    }

  	public static void main (String [] args ) throws IOException, InterruptedException, Exception {
    
		// create socket
    	ServerSocket servsock = new ServerSocket(6666);
	
	    while (true) {
	      	System.out.println("Waiting...");

			FileServer s2i = new FileServer();
           	s2i.robo();

      		Socket sock = servsock.accept();
      		System.out.println("Accepted connection : " + sock);

      		// sendfile
      		File myFile = new File (screenshotName);
      		byte [] mybytearray  = new byte [(int)myFile.length()];
      		FileInputStream fis = new FileInputStream(myFile);
      		BufferedInputStream bis = new BufferedInputStream(fis);
      		bis.read(mybytearray,0,mybytearray.length);
      		OutputStream os = sock.getOutputStream();
      		System.out.println("Sending...");
      		os.write(mybytearray,0,mybytearray.length);
      		os.flush();
      		sock.close();
			//Thread.sleep(10000);
      	}
    }
}
