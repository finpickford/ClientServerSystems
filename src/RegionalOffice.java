import ClientAndServer.*;

import org.omg.CORBA.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.*;

import java.io.*;
import java.util.Properties;

import javax.swing.*;


class HelloServant extends HelloWorldPOA {
    private RegionalOffice parent;
    private ORB orb; 
    
    public HelloServant(RegionalOffice parentGUI, ORB orb_val) {
	// store reference to parent GUI
	parent = parentGUI;
	
	// store reference to ORB
	orb = orb_val;
    }
    
    public String hello_world() {
	parent.addMessage("rtn_alert called by relay.\n    Replying with message...\n\n");
	
	return "Alert!!";
    }

}


public class RegionalOffice extends JFrame {
    private JPanel panel;
    private JScrollPane scrollpane;
    private JTextArea textarea;

    public RegionalOffice(String[] args){
//	try {
//	    // create and initialize the ORB
//	    ORB orb = ORB.init(args, null);
//	    
//	    // get reference to rootpoa & activate the POAManager
//	    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
//	    rootpoa.the_POAManager().activate();
//	    
//	    // create servant and register it with the ORB
//	    HelloServant helloRef = new HelloServant(this);
//	    
//	    // get the 'stringified IOR'
//	    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloRef);
//	    String stringified_ior = orb.object_to_string(ref);
//	    
//    	    // Save IOR to file
//            BufferedWriter out = new BufferedWriter(new FileWriter("server.ref"));
//            out.write(stringified_ior);
//	    out.close();

    	try {
    		Properties property = new Properties();
    		property.put("org.omg.COBRA.ORBInitialPort", "1050");
    		property.put("org.omg.COBRA.ORBInitialPort", "localhost");
    		
    	    // Initialize the ORB
    	    ORB orb = ORB.init(args, property);
    	    
    	    //get reference to rootpoa & activate the POAManager
    	    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
    	    rootpoa.the_POAManager().activate();
    	    
    	    // Create the Count servant object
    	    HelloServant helloRef = new HelloServant(this, orb);
    	    
    	    // get object reference from the servant
    	    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloRef);
    	    ClientAndServer.HelloWorld cref = HelloWorldHelper.narrow(ref);
    	     
    	    // Get a reference to the Naming service
    	    org.omg.CORBA.Object nameServiceObj = 
    		orb.resolve_initial_references ("NameService");
    	    if (nameServiceObj == null) {
    		System.out.println("nameServiceObj = null");
    		return;
    	    }

    	    // Use NamingContextExt which is part of the Interoperable
    	    // Naming Service (INS) specification.
    	    NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
    	    if (nameService == null) {
    		System.out.println("nameService = null");
    		return;
    	    }
    	    
    	    // bind the Count object in the Naming service
    	    String name = "countName";
    	    NameComponent[] countName = nameService.to_name(name);
    	    nameService.rebind(countName, cref);

	    // set up the GUI
	    textarea = new JTextArea(20,25);
	    scrollpane = new JScrollPane(textarea);
	    panel = new JPanel();

	    panel.add(scrollpane);
	    getContentPane().add(panel, "Center");

	    setSize(400, 500);
            setTitle("Regional Office Service");

            addWindowListener (new java.awt.event.WindowAdapter () {
                public void windowClosing (java.awt.event.WindowEvent evt) {
                    System.exit(0);;
                }
            } );

	    
	    // wait for invocations from clients
	    textarea.append("Server started.  Waiting for clients...\n\n");

	    // remove the "orb.run()" command,
	    // or the server will run but the GUI will not be visible
	    // orb.run();
	    
	} catch (Exception e) {
	    System.err.println("ERROR: " + e);
	    e.printStackTrace(System.out);
	}

    }


    public void addMessage(String message){
	textarea.append(message);
    }

    
    public static void main(String args[]) {
	final String[] arguments = args;
        java.awt.EventQueue.invokeLater(new Runnable() {
		public void run() {
		    new  RegionalOffice(arguments).setVisible(true);
		}
	    });
    }   
}


