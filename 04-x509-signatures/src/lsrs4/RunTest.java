package lsrs4;

 

import lsrs4.application.SeCom4;
import lsrs4.attacks.Adv1;
import lsrs4.comm.Communicator;

 













public class RunTest
{
	static final String msg1 = "Dear Bob, send $100 to Eve.\n Thanks, Alice";
	static final String msg2 = "Done.\n Your faithful banker Bob";
	static final String msg3 = "Bob, do you really trust Alice?\n Your suspicious Sam";
	static final String msg4 = "I trust Alice, but I'm not sure about our crypto!\nBob";

	static String pubAuthName = "SHA256WITHRSA";  
	static String algName = "RSA";
	 

	static String aliceKSFile = "keys/alice.pkcs12";
	static String bobKSFile = "keys/bob.pkcs12";
	static String samKSFile = "keys/sam.pkcs12";
	static String alicePKCFile = "keys/alice_pkc_ca.cer";
	static String bobPKCFile = "keys/bob_pkc_ca.cer";
	static String samPKCFile = "keys/sam_pkc_ca.cer";
	static String ksPwd = "password";  

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
		SeCom4 alice = new SeCom4("alice", comm);
		SeCom4 bob = new SeCom4("bob", comm);

		 
		 
		 
		alice.setAuthPub(pubAuthName);
		bob.setAuthPub(pubAuthName);
		 
		alice.setCertificate(alicePKCFile);
		bob.setCertificate(bobPKCFile);
		 
		alice.setKeyStore(aliceKSFile, ksPwd);
		bob.setKeyStore(bobKSFile, ksPwd);

		 
		 
		alice.sendData("bob", msg1.getBytes());
		bob.sendData("alice", msg2.getBytes());
	}

	 



	public static void test2() throws Exception
	{
		 
		 

		 
		 
		 

		 

		 

		 
		 

	}
}
