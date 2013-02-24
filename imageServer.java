//Server program to recieve PPT images as JPG frames and Exhibit on the Frame
import java.lang.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.util.logging.Logger;

public class imageServer implements Runnable {
   //status constants
   public final static int NULL = 0;
   public final static int DISCONNECTED  = 1;
   public final static int DISCONNECTING = 2;
   public final static int BEGIN_CONNECT = 3;
   public final static int CONNECTED = 4;
   public final static int SEND = 5;
   public final static int OPEN = 6;

   //status showing messages
   public final static String statusMessages[] = { 
            " Error! Could not connect!", " Disconnected", 
			" Disconnecting...", " Connecting...", " Connected",
			" Sended", " Opened" };
   public final static imageServer tcpObj = new imageServer();
   public final static String END_CHAT_SESSION = new Character((char)0).toString(); // Indicates the end of a session


   // Connection state info
   public static String hostIP = "localhost";
   public static int port = 1234;
   public static int connectionStatus = DISCONNECTED;
   public static boolean isHost = true;
   public static String statusString = statusMessages[connectionStatus];
   public static StringBuffer toAppend = new StringBuffer("");
   public static StringBuffer toSend = new StringBuffer("");

   //various GUI components and info
   public static JFrame mainFrame = null;
   public static JPanel statusBar = null;
   public static JLabel statusField = null;
   public static JTextField statusColor = null;
   public static JTextField ipField = null;
   public static JTextField portField = null;
   public static JRadioButton hostOption = null;
   public static JRadioButton guestOption = null;
   public static JButton connectButton = null;
   public static JButton disconnectButton = null;
   public static JButton sendButton = null;
   public static JButton openButton = null;

   //TCP Components
   public static ServerSocket hostServer = null;
   public static Socket socket = null;
   public static BufferedReader in = null;
   public static PrintWriter out = null;
   public static Socket sock = null;

   public static String imgStr = "/home/guest/Pictures/indiaflag.jpg";		//inserts the image icon testing*********
   public static ImageIcon image = null;
   private static Logger logger = Logger.getLogger("logger");

   private static JPanel initOptionsPane() {
      JPanel pane = null;
      ActionAdapter buttonListener = null;
      JPanel optionsPane = new JPanel(new GridLayout(7, 1));  // Create an options pane

      //IP address input
      pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      pane.add(new JLabel("Host IP:"));
      ipField = new JTextField(10); ipField.setText(hostIP);
      ipField.setEnabled(false);
      ipField.addFocusListener(new FocusAdapter() {

            public void focusLost(FocusEvent e) {
               ipField.selectAll();
               if (connectionStatus != DISCONNECTED) { //Should be editable only when disconnected
                  changeStatusNTS(NULL, true);
               }
               else {
                  hostIP = ipField.getText();
               }
            }
         });
      pane.add(ipField);
      optionsPane.add(pane);

      //Port input
      pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      pane.add(new JLabel("Port:"));
      portField = new JTextField(10); portField.setEditable(true);
      portField.setText((new Integer(port)).toString());
      portField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
               if (connectionStatus != DISCONNECTED) { // should be editable only when disconnected
                  changeStatusNTS(NULL, true);
               }
               else {
                  int temp;
                  try {
                     temp = Integer.parseInt(portField.getText());
                     port = temp;
                  }
                  catch (NumberFormatException nfe) {
                     portField.setText((new Integer(port)).toString());
                     mainFrame.repaint();
                  }
               }
            }
         });
      pane.add(portField);
      optionsPane.add(pane);

      // Host/guest option
      buttonListener = new ActionAdapter() {
            public void actionPerformed(ActionEvent e) {
               if (connectionStatus != DISCONNECTED) {
                  changeStatusNTS(NULL, true);
               }
               else {
                  isHost = e.getActionCommand().equals("host");

                  // Cannot supply host IP if host option is chosen
                  if (isHost) {
                     ipField.setEnabled(false);
                     ipField.setText("localhost");
                     hostIP = "localhost";
                  }
                  else {
                     ipField.setEnabled(true);
                  }
               }
            }
         };
      ButtonGroup bg = new ButtonGroup();
      hostOption = new JRadioButton("Host", true);
      hostOption.setMnemonic(KeyEvent.VK_H);
      hostOption.setActionCommand("host");
      hostOption.addActionListener(buttonListener);
      guestOption = new JRadioButton("Guest", false);
      guestOption.setMnemonic(KeyEvent.VK_G);
      guestOption.setActionCommand("guest");
      guestOption.addActionListener(buttonListener);
      bg.add(hostOption);
      bg.add(guestOption);
      pane = new JPanel(new GridLayout(1, 2));
      pane.add(hostOption);
      pane.add(guestOption);
      optionsPane.add(pane);

      //buttons
      JPanel buttonPane1 = new JPanel();
	  JPanel buttonPane2 = new JPanel();
	  JPanel buttonPane3 = new JPanel();
	  JPanel buttonPane4 = new JPanel();
	  buttonListener = new ActionAdapter() {
            public void actionPerformed(ActionEvent e) {
               if (e.getActionCommand().equals("connect")) {//request a connection initiation
                  changeStatusNTS(BEGIN_CONNECT, true);
               }
				else if (e.getActionCommand().equals("disconnect")) {//disconnecting
                  changeStatusNTS(DISCONNECTING, true);
               }
				else if (e.getActionCommand().equals("open")) {//request for open file
                  changeStatusNTS(OPEN, true);
               }
				else if (e.getActionCommand().equals("send")) { // Request for a send
                  changeStatusNTS(SEND, true);
               }
            }
         };

      connectButton = new JButton("  Connect  ");
      connectButton.setMnemonic(KeyEvent.VK_C);
      connectButton.setActionCommand("connect");
      connectButton.addActionListener(buttonListener);
      connectButton.setEnabled(false);

      disconnectButton = new JButton("Disconnect");
      disconnectButton.setMnemonic(KeyEvent.VK_D);
      disconnectButton.setActionCommand("disconnect");
      disconnectButton.addActionListener(buttonListener);
      disconnectButton.setEnabled(false);

	  sendButton = new JButton("    Send    ");
	  sendButton.setMnemonic(KeyEvent.VK_S);
      sendButton.setActionCommand("send");
      sendButton.addActionListener(buttonListener);
      sendButton.setEnabled(false);

	  openButton = new JButton("    Open    ");
	  openButton.setMnemonic(KeyEvent.VK_O);
      openButton.setActionCommand("open");
      openButton.addActionListener(buttonListener);
      openButton.setEnabled(true);

      buttonPane1.add(connectButton);
      buttonPane2.add(disconnectButton);
	  buttonPane3.add(openButton);
	  buttonPane4.add(sendButton);
      optionsPane.add(buttonPane1);
	  optionsPane.add(buttonPane2);
	  optionsPane.add(buttonPane3);
	  optionsPane.add(buttonPane4);

      return optionsPane;
   }


   // Initialize all the GUI components and display the frame
   private static void initGUI() {
      // Set up the status bar
      statusField = new JLabel();
      statusField.setText(statusMessages[DISCONNECTED]);
      statusColor = new JTextField(1);
      statusColor.setBackground(Color.red);
      statusColor.setEditable(false);
      statusBar = new JPanel(new BorderLayout());
      statusBar.add(statusColor, BorderLayout.WEST);
      statusBar.add(statusField, BorderLayout.CENTER);

      // Set up the options pane
      JPanel optionsPane = initOptionsPane();

	  //set up image pane
	  image = new ImageIcon(imgStr);
      JLabel imagePane = new JLabel(" ", image, JLabel.CENTER);

      // Set up the chat pane
      JPanel chatPane = new JPanel(new BorderLayout());
	  chatPane.add(imagePane, BorderLayout.CENTER);
      chatPane.setPreferredSize(new Dimension(800, 600));

      // Set up the main pane
      JPanel mainPane = new JPanel(new BorderLayout());
      mainPane.add(statusBar, BorderLayout.SOUTH);
      mainPane.add(optionsPane, BorderLayout.WEST);
      mainPane.add(chatPane, BorderLayout.CENTER);

      // Set up the main frame
      mainFrame = new JFrame("PPT Server");
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.setContentPane(mainPane);
      //mainFrame.setSize(mainFrame.getPreferredSize());
	  mainFrame.setSize(1000, 750);
      mainFrame.setLocation(0, 0);
      mainFrame.pack();
      mainFrame.setVisible(true);
   }

   // The thread-safe way to change the GUI components while changing state
   private static void changeStatusTS(int newConnectStatus, boolean noError) {
      // Change state if valid state
      if (newConnectStatus != NULL) {
         connectionStatus = newConnectStatus;
      }

      // If there is no error, display the appropriate status message
      if (noError) {
         statusString = statusMessages[connectionStatus];
      }
      // Otherwise, display error message
      else {
         statusString = statusMessages[NULL];
      }

      // Call the run() routine (Runnable interface) on the error-handling and GUI-update thread
      SwingUtilities.invokeLater(tcpObj);
   }

   // The non-thread-safe way to change the GUI components while changing state
   private static void changeStatusNTS(int newConnectStatus, boolean noError) {
      // Change state if valid state
      if (newConnectStatus != NULL) {
         connectionStatus = newConnectStatus;
      }

      // If there is no error, display the appropriate status message
      if (noError) {
         statusString = statusMessages[connectionStatus];
      }
      // Otherwise, display error message
      else {
         statusString = statusMessages[NULL];
      }

      // Call the run() routine (Runnable interface) on the current thread
      tcpObj.run();
   }

   // Add text to send-buffer
   private static void sendString(String s) {
      synchronized (toSend) {
         toSend.append(s + "\n");
      }
   }

   // Cleanup for disconnect
   private static void cleanUp() {
      try {
         if (hostServer != null) {
            hostServer.close();
            hostServer = null;
         }
      }
      catch (IOException e) { hostServer = null; }

      try {
         if (socket != null) {
            socket.close();
            socket = null;
         }
      }
      catch (IOException e) { socket = null; }

      try {
         if (in != null) {
            in.close();
            in = null;
         }
      }
      catch (IOException e) { in = null; }

      if (out != null) {
          out.close();
          out = null;
	  }
   }

   // Checks the current state and sets the enables/disables accordingly
   public void run() {
      switch (connectionStatus) {
      case DISCONNECTED:
         connectButton.setEnabled(true);
         disconnectButton.setEnabled(false);
		 sendButton.setEnabled(false);
		 openButton.setEnabled(false);
         ipField.setEnabled(true);
         portField.setEnabled(true);
         hostOption.setEnabled(true);
         guestOption.setEnabled(true);
         statusColor.setBackground(Color.red);
         break;
      case DISCONNECTING:
         connectButton.setEnabled(false);
         disconnectButton.setEnabled(false);
		 sendButton.setEnabled(false);
		 openButton.setEnabled(false);
         ipField.setEnabled(false);
         portField.setEnabled(false);
         hostOption.setEnabled(false);
         guestOption.setEnabled(false);
         statusColor.setBackground(Color.orange);
         break;
      case CONNECTED:
         connectButton.setEnabled(false);
         disconnectButton.setEnabled(true);
		 sendButton.setEnabled(false);
		 openButton.setEnabled(true);
         ipField.setEnabled(false);
         portField.setEnabled(false);
         hostOption.setEnabled(false);
         guestOption.setEnabled(false);
         statusColor.setBackground(Color.green);
         break;
      case BEGIN_CONNECT:
         connectButton.setEnabled(true);
         disconnectButton.setEnabled(true);
		 sendButton.setEnabled(false);
		 openButton.setEnabled(false);
         ipField.setEnabled(false);
         portField.setEnabled(false);
         hostOption.setEnabled(false);
         guestOption.setEnabled(false);
         statusColor.setBackground(Color.orange);
         break;
	  case SEND:
         connectButton.setEnabled(false);
         disconnectButton.setEnabled(true);
	     sendButton.setEnabled(false);
		 openButton.setEnabled(false);
         ipField.setEnabled(false);
         portField.setEnabled(false);
         hostOption.setEnabled(false);
         guestOption.setEnabled(false);
         statusColor.setBackground(Color.blue);
         break;
	  case OPEN:
         connectButton.setEnabled(false);
         disconnectButton.setEnabled(false);
	     sendButton.setEnabled(true);
		 openButton.setEnabled(false);
         ipField.setEnabled(false);
         portField.setEnabled(false);
         hostOption.setEnabled(false);
         guestOption.setEnabled(false);
         statusColor.setBackground(Color.blue);
         break;
      }

      // Make sure that the button/text field states are consistent with the internal states
      ipField.setText(hostIP);
      portField.setText((new Integer(port)).toString());
      hostOption.setSelected(isHost);
      guestOption.setSelected(!isHost);
      statusField.setText(statusString);

      mainFrame.repaint();
   }

	private static File getFileOrDirectory()
	{
		File fileName = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(
		JFileChooser.FILES_AND_DIRECTORIES );
		int result = fileChooser.showOpenDialog(mainFrame );
		if ( result == JFileChooser.CANCEL_OPTION )
		{
			logger.fine("File open dialog canceled.");
           	//logger.exiting("imageServer.FileOpenListener", "actionPerformed");
		}
		fileName = fileChooser.getSelectedFile(); 
		if (  ( fileName.getName().equals( "" ) ) )
		{
			System.out.println("Error is this");
			System.exit( 1 );
		} 
		 if (result == JFileChooser.APPROVE_OPTION)
         {
			return fileName;
           // String name = chooser.getSelectedFile().getPath();
            //logger.log(Level.FINE, "Reading file {0}", name);
            //label.setIcon(new ImageIcon(name));
         }
         else 
		   
		return fileName;
	} 

   // The main procedure
   public static void main(String args[]) throws IOException, FileNotFoundException{
      String s;
	  File file = null;
	  File pptFile = null;
	  int filesize=6022386; // filesize temporary hardcoded
      int bytesRead;
      int current = 0;
	  byte [] mybytearray  = new byte [filesize];
      initGUI();

      while (true) {
         try { // Poll every ~10 ms
            Thread.sleep(10);
         }
         catch (InterruptedException e) {}

         switch (connectionStatus) {
         case BEGIN_CONNECT:
            try {
               // Try to set up a server if host
               if (isHost) {
                  hostServer = new ServerSocket(port);
                  socket = hostServer.accept();
               }

               // If guest, try to connect to the server
               else {
                  socket = new Socket(hostIP, port);
               }

               in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               out = new PrintWriter(socket.getOutputStream(), true);
               changeStatusTS(CONNECTED, true);
            }
            // If error, clean up and output an error message
            catch (IOException e) {
               cleanUp();
               changeStatusTS(DISCONNECTED, false);
            }
            break;

         case CONNECTED:
            try {
               // Send data
               if (toSend.length() != 0) {
                  out.print(toSend); out.flush();
                  toSend.setLength(0);
                  changeStatusTS(NULL, true);
               }

               // Receive data
               if (in.ready()) {
                  s = in.readLine();
                  if ((s != null) &&  (s.length() != 0)) {
                     // Check if it is the end of a trasmission
                     if (s.equals(END_CHAT_SESSION)) {
                        changeStatusTS(DISCONNECTING, true);
                     }

                     // Otherwise, receive what text
                     else {
                        //appendToChatBox("INCOMING: " + s + "\n");
                        changeStatusTS(NULL, true);
                     }
                  }
               }
            }
            catch (IOException e) {
               cleanUp();
               changeStatusTS(DISCONNECTED, false);
            }
            break;

         case DISCONNECTING:
            // Tell other chatter to disconnect as well
            out.print(END_CHAT_SESSION); out.flush();

            //clean up (close all streams/sockets)
            cleanUp();
            changeStatusTS(DISCONNECTED, true);
            break;

		 case OPEN:
			pptFile = getFileOrDirectory();
			System.out.println(pptFile);
			changeStatusTS(OPEN, true);
			break;

		 case SEND:
			changeStatusTS(SEND, true);	////////////////////ERROR pptfile should be sent here!!!!
			while(true)
			{
    			 InputStream is = socket.getInputStream();
    			 FileOutputStream fos = new FileOutputStream("image.jpg");	//random name for a image file
    			 BufferedOutputStream bos = new BufferedOutputStream(fos);
                 bytesRead = is.read(mybytearray,0,mybytearray.length);
                 current = bytesRead;

    			do {
       				bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
      				if(bytesRead >= 0) current += bytesRead;
    			} while(bytesRead > -1);

    			bos.write(mybytearray, 0 , current);
    			bos.flush();
				image = new ImageIcon("image.jpg");
      			JLabel imagePane = new JLabel(" ", image, JLabel.CENTER);
				mainFrame.repaint();
				file = new File("image.jpg");
				file.delete();
    			bos.close();
    			cleanUp();
				break;
			}

         default: break;
         }
      }
   }
}

// Action adapter for easy event-listener coding
class ActionAdapter implements ActionListener {
   public void actionPerformed(ActionEvent e) {}
}
