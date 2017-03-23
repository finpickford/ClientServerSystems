import ClientAndServer.*;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Sensor extends JFrame {
    private JPanel textpanel, buttonpanel;
    private JScrollPane scrollpane;
    private JTextArea textarea;
    private JButton getItButton;

    public Sensor(String[] args) {
	try {
	    // create and initialize the ORB
	    ORB orb = ORB.init(args, null);
	    
	    // read in the 'stringified IOR' of the Relay
      	    BufferedReader in = new BufferedReader(new FileReader("relay.ref"));
      	    String stringified_ior = in.readLine();
	    
	    // get object reference from stringified IOR
      	    org.omg.CORBA.Object server_ref = 		
		orb.string_to_object(stringified_ior);
	    
	    final ClientAndServer.Relay relay = 
		ClientAndServer.RelayHelper.narrow(server_ref);


	    // set up the GUI
	    textarea = new JTextArea(20,25);
	    scrollpane = new JScrollPane(textarea);
	    textpanel = new JPanel();

	    buttonpanel = new JPanel();
	    getItButton = new JButton("Alert Office");
	    getItButton.addActionListener (new ActionListener() {
		    public void actionPerformed (ActionEvent evt) {
			textarea.append("Calling relay...\n");
			String result = relay.fetch_alert();
			textarea.append("   Result = " + result + "\n\n");
		    }
		});
	    

	    textpanel.add(scrollpane);
	    buttonpanel.add(getItButton);

	    getContentPane().add(textpanel, "Center");
	    getContentPane().add(buttonpanel, "South");

	    setSize(400, 500);
            setTitle("Sensor Client");

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
		    new  Sensor(arguments).setVisible(true);
		}
	    });
    }
}
