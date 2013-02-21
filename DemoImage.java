    

import javax.swing.*;
import java.awt.*;

public class DemoImage extends JFrame {
    public void showImage() {
    	
    	JFrame frame = new JFrame("PPTexplorer");	// creates the actual frame 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(1350, 700);
    	frame.setResizable(true);
    	frame.setLocationRelativeTo(null);
    	String imgStr = "/home/guest/Desktop/abc.png";		//inserts the image icon
    	ImageIcon image = new ImageIcon(imgStr);
    	JLabel label1 = new JLabel(" ", image, JLabel.CENTER);
    	frame.getContentPane().add(label1);
    	frame.validate();
    	frame.setVisible(true);
    }
    
	public static void main(String[] args) {
	    DemoImage show1 = new DemoImage();
	    show1.showImage();
    }
}
