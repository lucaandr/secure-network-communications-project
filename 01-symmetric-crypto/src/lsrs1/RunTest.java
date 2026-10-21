package lsrs1;

 

import javax.crypto.SecretKey;

import lsrs1.application.SeCom1;
import lsrs1.comm.Communicator;
import lsrs1.crypto.GenSecKey;
import lsrs1.misc.Utils;
import lsrs1.attacks.Adv1;

 



public class RunTest 
{
	static final String msg1 = "Dear Bob, send $100 to Eve.\n Thanks, Alice"; 
	static final String msg2 = "Done.\n Your faithful banker Bob"; 
	static final String msg3 = "Bob, do you really trust Alice?\n Your suspicious Sam"; 
	static final String msg4 = "I trust Alice, but I'm not sure about our crypto!\nBob"; 

	static String encSecName = "AES/CBC/PKCS5Padding";  
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
		SeCom1 alice = new SeCom1("alice", comm); 
		SeCom1 bob = new SeCom1("bob", comm); 

		 
		Utils.debug(">> Preliminary setup (set algorithms, secret keys)");
		 
		alice.setEncSecName(encSecName); 
		bob.setEncSecName(encSecName); 
		 
		GenSecKey genSecKey = new GenSecKey(encSecName);
		SecretKey sk = genSecKey.genKey(secKeyLen); 
		 
		alice.setSecretKey("bob", sk);
		bob.setSecretKey("alice", sk);
		
		 
		Utils.debug(">> System ready for communications");
		 
		alice.sendData("bob", msg1.getBytes());
		bob.sendData("alice", msg2.getBytes());
	}

	 




	public static void test2() throws Exception
	{
		 

		 
		
		 
	}
}
