package ClientAndServer;

/**
* ClientAndServer/HelloWorldHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Relay.idl
* Thursday, 23 March 2017 14:27:48 o'clock GMT
*/

public final class HelloWorldHolder implements org.omg.CORBA.portable.Streamable
{
  public ClientAndServer.HelloWorld value = null;

  public HelloWorldHolder ()
  {
  }

  public HelloWorldHolder (ClientAndServer.HelloWorld initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ClientAndServer.HelloWorldHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ClientAndServer.HelloWorldHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ClientAndServer.HelloWorldHelper.type ();
  }

}
