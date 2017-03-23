package ClientAndServer;


/**
* ClientAndServer/RelayPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Relay.idl
* Thursday, 23 March 2017 15:34:07 o'clock GMT
*/

public abstract class RelayPOA extends org.omg.PortableServer.Servant
 implements ClientAndServer.RelayOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("fetch_message", new java.lang.Integer (0));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // ClientAndServer/Relay/fetch_message
       {
         String $result = null;
         $result = this.fetch_message ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ClientAndServer/Relay:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Relay _this() 
  {
    return RelayHelper.narrow(
    super._this_object());
  }

  public Relay _this(org.omg.CORBA.ORB orb) 
  {
    return RelayHelper.narrow(
    super._this_object(orb));
  }


} // class RelayPOA
