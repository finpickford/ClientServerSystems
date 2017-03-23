import ClientAndServer.*;

import org.omg.CORBA.*;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Camera extends JFrame {
    private JPanel textpanel, buttonpanel;
    private JScrollPane scrollpane;
    private JTextArea textarea, status;
    private JButton getItButton, onButton, offButton, resetButton, requestImgButton;
    private JLabel statusLabel;
    private ORB orb; 
    
    public Camera(String[] args) {
    	
  //  orb = orb_val;
//	try {
//	    // create and initialize the ORB
//	    ORB orb = ORB.init(args, null);
//	    
//	    // read in the 'stringified IOR' of the Relay
//      	    BufferedReader in = new BufferedReader(new FileReader("relay.ref"));
//      	    String stringified_ior = in.readLine();
//	    
//	    // get object reference from stringified IOR
//      	    org.omg.CORBA.Object server_ref = 		
//		orb.string_to_object(stringified_ior);
//	    
//	    final ClientAndServer.Relay relay = 
//		ClientAndServer.RelayHelper.narrow(server_ref);
    	
    	try {
    	    // Initialize the ORB
    	    System.out.println("Initializing the ORB");
    	    ORB orb = ORB.init(args, null);
    	    
    	    // Get a reference to the Naming service
    	    org.omg.CORBA.Object nameServiceObj = 
    		orb.resolve_initial_references ("NameService");
    	    if (nameServiceObj == null) {
    		System.out.println("nameServiceObj = null");
    		return;
    	    }

    	    // Use NamingContextExt instead of NamingContext. This is 
    	    // part of the Interoperable naming Service.  
    	    NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
    	    if (nameService == null) {
    		System.out.println("nameService = null");
    		return;
    	    }
    	    
    	    // resolve the Count object reference in the Naming service
    	    String name = "countName";
    	    ClientAndServer.Relay relay = RelayHelper.narrow(nameService.resolve_str(name));
    	


	    // set up the GUI
	    textarea = new JTextArea(20,25);
	    scrollpane = new JScrollPane(textarea);
	    textpanel = new JPanel();

	    buttonpanel = new JPanel();
	    getItButton = new JButton("Alert Office");
	    getItButton.addActionListener (new ActionListener() {
		    public void actionPerformed (ActionEvent evt) {
			textarea.append("Calling relay...\n");
			String result = relay.fetch_message();
			textarea.append("   Result = " + result + "\n\n");
			status.setText("Panic"); 
		    }
		});
	    
	    
	    onButton = new JButton("On");
	    offButton = new JButton("Off");
	    
	    resetButton = new JButton("Reset Alarm");
	    resetButton.addActionListener (new ActionListener() 
	    {
		    public void actionPerformed (ActionEvent evt) {
		    	status.setText("Okay");
		    }
		});
	  
	    
	    requestImgButton = new JButton("Request Image");
	    
	    status = new JTextArea(4, 15); 
	    statusLabel = new JLabel("Status: ");

	    textpanel.add(scrollpane);
	    buttonpanel.add(getItButton);
	    buttonpanel.add(onButton);
	    buttonpanel.add(offButton);
	    buttonpanel.add(resetButton);
	    buttonpanel.add(requestImgButton);
	    buttonpanel.add(statusLabel);
	    buttonpanel.add(status);

	    getContentPane().add(textpanel, "Center");
	    getContentPane().add(buttonpanel, "South");

	    setSize(400, 500);
            setTitle("Camera Client");

            addWindowListener (new java.awt.event.WindowAdapter () {
                public void windowClosing (java.awt.event.WindowEvent evt) {
                    System.exit(0);;
                }
            } );

	    textarea.append("Client started.  Click the button to contact relay...\n\n");
	    
	} catch (Exception e) {
	    System.out.println("ERROR : " + e) ;
	    e.printStackTrace(System.out);
	}
    }



    public static void main(String args[]) {
	final String[] arguments = args;
        java.awt.EventQueue.invokeLater(new Runnable() {
		public void run() {
		    new  Camera(arguments).setVisible(true);
		}
	    });
    }
}
