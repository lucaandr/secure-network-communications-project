package lsrs3;

 

import javax.crypto.SecretKey;

import lsrs3.application.*;
import lsrs3.attacks.*;
import lsrs3.comm.Communicator;
import lsrs3.crypto.GenSecKey;
import lsrs3.misc.Utils;


 



public class RunTest 
{
	static final String msg1 = "Dear Bob, send $100 to Eve.\n Thanks, Alice"; 
	static final String msg2 = "Done.\n Your faithful banker Bob"; 
	static final String msg3 = "Bob, do you really trust Alice?\n Your suspicious Sam"; 
	static final String msg4 = "I trust Alice, but I'm not sure about our crypto!\nBob"; 

	static String secEncName = "AES/CTR/NoPadding";  
	static String secAuthName = "HMACSHA256"; 
	static int secKeyLen = 128; 

	public static void main(String[] args) 
	{
		try {
			 
			test1(false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 




	public static void test1(boolean withAdv) throws Exception
	{
		 
		 
		Communicator comm = (withAdv)? (new Adv1()) : (new Communicator());  
		SeCom3A alice = new SeCom3A("alice", comm); 
		SeCom3A bob = new SeCom3A("bob", comm); 

		 
		Utils.debug(">> Preliminary setup (set algorithms, keys)");
		 
		alice.setAuthSecName(secAuthName); 
		bob.setAuthSecName(secAuthName); 
		 
		GenSecKey genSecKey = new GenSecKey(secEncName);
		SecretKey sk = genSecKey.genKey(secKeyLen); 
		 
		alice.setSecretKey("bob", "mac", sk);
		bob.setSecretKey("alice", "mac", sk);
		
		 
		Utils.debug(">> System ready for communications");
		 
		alice.sendData("bob", msg1.getBytes());
		bob.sendData("alice", msg2.getBytes());
	}
	
	 




	public static void test2(boolean withAdv) throws Exception
	{
		 
		 
		Communicator comm = (withAdv)? (new Adv1()) : (new Communicator());  
		SeCom3AE alice = new SeCom3AE("alice", comm); 
		SeCom3AE bob = new SeCom3AE("bob", comm); 
		
		 
		Utils.debug(">> Preliminary setup (set algorithms, keys)");
		 
		alice.setAuthEncName(secAuthName, secEncName); 
		bob.setAuthEncName(secAuthName, secEncName); 
		 
		GenSecKey genSecKey = new GenSecKey(secEncName);
		SecretKey keyEnc = genSecKey.genKey(secKeyLen); 
		SecretKey keyMac = genSecKey.genKey(secKeyLen); 
		 
		alice.setSecretKey("bob", "enc", keyEnc);
		alice.setSecretKey("bob", "mac", keyMac);
		bob.setSecretKey("alice", "enc", keyEnc);
		bob.setSecretKey("alice", "mac", keyMac);
		
		 
		Utils.debug(">> System ready for communications");
		 
		alice.sendData("bob", msg1.getBytes());
		bob.sendData("alice", msg2.getBytes());
	}

	 




	public static void test3() throws Exception
	{
		 
		
		 
		
		 

	}
}
