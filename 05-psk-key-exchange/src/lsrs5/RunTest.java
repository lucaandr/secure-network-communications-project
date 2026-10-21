package lsrs5;

 

import javax.crypto.SecretKey;

import lsrs5.application.SeCom5;
import lsrs5.attacks.Adv1;
import lsrs5.comm.Communicator;
import lsrs5.crypto.GenSecKey;
import lsrs5.crypto.KeyManagerKS;

 













public class RunTest 
{
	static final String msg1 = "Dear Bob, send $100 to Eve.\n Thanks, Alice"; 
	static final String msg2 = "Done.\n Your faithful banker Bob"; 
	static final String msg3 = "Bob, do you really trust Alice?\n Your suspicious Sam"; 
	static final String msg4 = "I trust Alice, but I'm not sure about our crypto!\nBob"; 

	static String encSecName = "AES/CTR/NoPadding";  
	static String authSecName = "HMACSHA256"; 
	static String keyAlgName = "AES"; 
	static int secKeyLen = 128; 
	
	static String aliceKSFile = "keys/alice.jceks"; 
	static String bobKSFile = "keys/bob.jceks"; 
	static String samKSFile = "keys/sam.jceks"; 
	static String ksPwd = "password";  
	static KeyManagerKS kma; 
	static KeyManagerKS kmb; 
	static KeyManagerKS kms;
	
	public static void main(String[] args) 
	{
		try {
			test2(false);  
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	 




	public static void test1(boolean withAdv) throws Exception
	{
		 
		 
		 
		Communicator comm = (withAdv)? new Adv1(10) : new Communicator(); 
		SeCom5 alice = new SeCom5("alice", comm); 
		SeCom5 bob = new SeCom5("bob", comm); 
		
		 
		 
		kma = new KeyManagerKS(aliceKSFile, ksPwd); 
		kmb = new KeyManagerKS(bobKSFile, ksPwd); 
		kma.createKeyStore(); 
		kmb.createKeyStore(); 
		setAuthSecKeysAB(); 
		
		 
		 
		alice.setKeyStore(aliceKSFile, ksPwd); 
		bob.setKeyStore(bobKSFile, ksPwd); 
		 
		alice.setAuthEnc(authSecName, encSecName, secKeyLen); 
		bob.setAuthEnc(authSecName, encSecName, secKeyLen); 

		 
		alice.setKeyExch(authSecName, secKeyLen);
		bob.setKeyExch(authSecName, secKeyLen);
		alice.doKeyExch("bob");
		
		 
		 
		alice.sendData("bob", msg1.getBytes());
		bob.sendData("alice", msg2.getBytes());
	}

	 





	public static void test2(boolean withAdv) throws Exception
	{
		 
		 
		
		 
		 
		
		 
		 

		 
		 

		 

		 
		 

	}
	
	 



	public static void setAuthSecKeysAB() throws Exception
	{
		 
		GenSecKey genSecKey = new GenSecKey(keyAlgName);
		SecretKey sk1 = genSecKey.genKey(secKeyLen);  
		SecretKey sk2 = genSecKey.genKey(secKeyLen);  
		 
		kma.setSecretKey("bob_ake", sk1);
		kma.setSecretKey("bob_kdf", sk2);
		kmb.setSecretKey("alice_ake", sk1);
		kmb.setSecretKey("alice_kdf", sk2);
		kma.saveKeyStore();
		kmb.saveKeyStore();
	}

	 



	public static void setAuthSecKeysSB() throws Exception
	{
		 
		GenSecKey genSecKey = new GenSecKey(keyAlgName);
		SecretKey sk1 = genSecKey.genKey(secKeyLen);  
		SecretKey sk2 = genSecKey.genKey(secKeyLen);  
		 
		kms.setSecretKey("bob_ake", sk1);
		kms.setSecretKey("bob_kdf", sk2);
		kmb.setSecretKey("sam_ake", sk1);
		kmb.setSecretKey("sam_kdf", sk2);
		kms.saveKeyStore();
		kmb.saveKeyStore();
	}

}
