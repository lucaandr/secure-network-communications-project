package lsrs2;

 

import lsrs2.application.SeCom2;
import lsrs2.attacks.Adv1;
import lsrs2.comm.Communicator;

 



public class RunTest 
{
	static final String msg1 = "Dear Bob, send $100 to Eve.\n Thanks, Alice"; 
	static final String msg2 = "Done.\n Your faithful banker Bob"; 
	static final String msg3 = "Bob, do you really trust Alice?\n Your suspicious Sam"; 
	static final String msg4 = "I trust Alice, but I'm not sure about our crypto!\nBob"; 

	static String encSecName = "AES/CTR/NoPadding";  
	static int secKeyLen = 128; 
	static String encPubName = "RSA/ECB/OAEPWithSHA1AndMGF1Padding"; 
	static int pubKeyLen = 1024; 

	public static void main(String[] args) 
	{
		try {
			test1();  
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	 





	public static void test1() throws Exception
	{
		 
		 
		Communicator comm = new Communicator();  
		SeCom2 alice = new SeCom2("alice", comm); 
		SeCom2 bob = new SeCom2("bob", comm); 

		 
		 
		alice.setEncPubName(encPubName); 
		bob.setEncPubName(encPubName); 
		 
		bob.genKeyPair(pubKeyLen);
		 
		alice.recvPubKey("bob", bob.getPubKey());
		
		 
		 
		alice.setEncSecName(encSecName); 
		bob.setEncSecName(encSecName); 
		 
		alice.sendKeyExch("bob", secKeyLen); 
		 
		alice.sendData("bob", msg1.getBytes());
		bob.sendData("alice", msg2.getBytes());
	}

	 





	public static void test2() throws Exception
	{
		 
		
		 
		
		 

	}

}
