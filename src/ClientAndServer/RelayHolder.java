package ClientAndServer;

/**
* ClientAndServer/RelayHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Relay.idl
* Thursday, 23 March 2017 14:27:48 o'clock GMT
*/

public final class RelayHolder implements org.omg.CORBA.portable.Streamable
{
  public ClientAndServer.Relay value = null;

  public RelayHolder ()
  {
  }

  public RelayHolder (ClientAndServer.Relay initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ClientAndServer.RelayHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ClientAndServer.RelayHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ClientAndServer.RelayHelper.type ();
  }

}
