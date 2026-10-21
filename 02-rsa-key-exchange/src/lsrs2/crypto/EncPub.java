package lsrs2.crypto;

 

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

 






public class EncPub 
{
	private String encName; 
	private Cipher cipher;
	private PublicKey pubKey;
	private PrivateKey privKey;

	 


	public EncPub(String encName) throws Exception
	{ 
		this.encName = encName;
		cipher = Cipher.getInstance(encName);  
		 
	}

	public byte[] encrypt(byte[] ptxt) throws Exception
	{
		if (pubKey == null) throw new IllegalStateException("Public key has not been set");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(ptxt);
	}

	public byte[] decrypt(byte[] ctxt) throws Exception
	{
		if (privKey == null) throw new IllegalStateException("Private key has not been set");
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		return cipher.doFinal(ctxt);
	}

	public String getEncName()
	{
		return encName;
	}

	public void setEncName(String encName)
	{
		this.encName = encName;
	}

	public PublicKey getPubKey()
	{
		return pubKey;
	}
	
	public void setPubKey(PublicKey pk)
	{
		this.pubKey = pk;
	}

	public PrivateKey getPrivKey()
	{
		return privKey;
	}
	
	public void setPrivKey(PrivateKey sk)
	{
		this.privKey = sk;
	}
}
