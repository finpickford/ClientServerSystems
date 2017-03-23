import ClientAndServer.*;

import org.omg.PortableServer.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.io.*;
import java.util.Properties;

import javax.swing.*;



class RelayServant extends RelayPOA {
    
    private ORB orb;
    private ClientAndServer.HelloWorld server;
    private HomeHub parent;
    
    public RelayServant(HomeHub parentGUI, ORB orb_val) {
	// store reference to parent GUI
	parent = parentGUI;

	// store reference to ORB
	orb = orb_val;


//	// look up the server
//	try {
//	    // read in the 'stringified IOR'
//      	    BufferedReader in = new BufferedReader(new FileReader("server.ref"));
//      	    String stringified_ior = in.readLine();
//
//	    // get object reference from stringified IOR
//      	    org.omg.CORBA.Object server_ref = 		
//		orb.string_to_object(stringified_ior);
//	    server = ClientAndServer.HelloWorldHelper.narrow(server_ref);
//	} catch (Exception e) {
//	    System.out.println("ERROR : " + e) ;
//	    e.printStackTrace(System.out);
//	}
	
	try {
		Properties property = new Properties();
		property.put("org.omg.COBRA.ORBInitialPort", "1050");
		property.put("org.omg.COBRA.ORBInitialPort", "localhost");
	    
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
	    server = HelloWorldHelper.narrow(nameService.resolve_str(name));
	} catch (Exception e) {
	    System.err.println("ERROR: " + e);
	    e.printStackTrace(System.out);
	}

    }
    
    
    public String fetch_message() {
	parent.addMessage("fetch_alert called by client.  Calling server..\n");

	String messageFromServer = server.hello_world();

	parent.addMessage("message from server = " + messageFromServer + "\n"
			   + "   Now forwarding to client..\n\n");

	return messageFromServer;
    }
}


public class HomeHub extends JFrame {
    private JPanel panel;
    private JScrollPane scrollpane;
    private JTextArea textarea;

    public HomeHub(String[] args) {
//	try {
//	    // create and initialize the ORB
//	    ORB orb = ORB.init(args, null);
//	    
//	    // get reference to rootpoa & activate the POAManager
//	    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
//	    rootpoa.the_POAManager().activate();
//	    
//	    // create servant and register it with the ORB
//	    RelayServant relayRef = new RelayServant(this, orb);
//	    
//	    // Get the 'stringified IOR'
//	    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(relayRef);
//            String stringified_ior = orb.object_to_string(ref);
//	    
//    	    // Save IOR to file
//            BufferedWriter out = new BufferedWriter(new FileWriter("relay.ref"));
//            out.write(stringified_ior);
//	    out.close();
    	
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
	    panel = new JPanel();

	    panel.add(scrollpane);
	    getContentPane().add(panel, "Center");

	    setSize(400, 500);
            setTitle("HomeHub Relay");

            addWindowListener (new java.awt.event.WindowAdapter () {
                public void windowClosing (java.awt.event.WindowEvent evt) {
                    System.exit(0);;
                }
            } );

	    
	    // wait for invocations from clients
	    textarea.append("Relay started.  Waiting for clients...\n\n");

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
		    new  HomeHub(arguments).setVisible(true);
		}
	    });
    }

}

