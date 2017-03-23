package ClientAndServer;


/**
* ClientAndServer/RelayHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Relay.idl
* Wednesday, 22 March 2017 14:05:02 o'clock GMT
*/

abstract public class RelayHelper
{
  private static String  _id = "IDL:ClientAndServer/Relay:1.0";

  public static void insert (org.omg.CORBA.Any a, ClientAndServer.Relay that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static ClientAndServer.Relay extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (ClientAndServer.RelayHelper.id (), "Relay");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static ClientAndServer.Relay read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_RelayStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, ClientAndServer.Relay value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static ClientAndServer.Relay narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof ClientAndServer.Relay)
      return (ClientAndServer.Relay)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      ClientAndServer._RelayStub stub = new ClientAndServer._RelayStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static ClientAndServer.Relay unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof ClientAndServer.Relay)
      return (ClientAndServer.Relay)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      ClientAndServer._RelayStub stub = new ClientAndServer._RelayStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}