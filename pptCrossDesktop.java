//Cross platform solution to view a PPT File

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class pptCrossDesktop {
 
	public static void main(String[] args) throws Exception {
 
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

		//InputStreamReader inps= new InputStreamReader(System.in);
		//BufferedReader buf= new BufferedReader(inps);
		int ch; // Read the file one character at a time.
		FileReader fr;
		Thread.sleep(10000);
		Robot robot = new Robot();

 		robot.keyPress(KeyEvent.VK_F5);
	    	robot.keyRelease(KeyEvent.VK_F5);

		//robot.keyPress(KeyEvent.VK_ENTER);
		//robot.keyRelease(KeyEvent.VK_ENTER);
		// Mouse pointer movement
		robot.mouseMove(300, 550);
		
		// LEFT CLICK
		robot.mousePress(InputEvent.BUTTON1_MASK);
        	robot.mouseRelease(InputEvent.BUTTON1_MASK);
		try {
			// Open the file.
			fr = new FileReader("/home/guest/javaTest/abc.txt");
		} catch(FileNotFoundException exc) {
			System.out.println("File Not Found");
			return;
		}

		/*while(true)
		{
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(5000);
			//robot.mousePress(InputEvent.BUTTON1_MASK);
        		//robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}*/

			while(true)
			{
				try {
					do {
						ch = fr.read();
						if(ch != -1)
							if(((char)ch) == '1') 
								{	
									//System.out.println("Ok");
									Thread.sleep(1000);
									robot.mousePress(InputEvent.BUTTON1_MASK);
        							robot.mouseRelease(InputEvent.BUTTON1_MASK);
								}
					} while(ch != -1);
				} catch(IOException exc) {
					System.out.println("Error Reading File");
					return;
				}
				try {
					fr.close();
				} catch(IOException exc) {
					System.out.println("Error closing File");
				}
			}
	
	}
		
 
}
