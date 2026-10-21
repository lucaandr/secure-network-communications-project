package lsrs1.crypto;

 

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

 





public class EncSec 
{
	private String encName; 
	private Cipher cipher; 
	private SecretKey sk;
	
	 




	public EncSec(String encName) throws Exception
	{
		this.encName = encName;
		cipher = Cipher.getInstance(encName);  
	}

	 






	public Ciphertext encrypt( byte [] ptxt) throws Exception
	{ 
		if (sk == null) throw new IllegalStateException("Encryption key has not been set");
		byte[] iv = new byte[0];
		if (encName.toUpperCase().contains("/CBC/") || encName.toUpperCase().contains("/CTR/")) {
			iv = new byte[cipher.getBlockSize()];
			new java.security.SecureRandom().nextBytes(iv);
			cipher.init(Cipher.ENCRYPT_MODE, sk, new IvParameterSpec(iv));
		} else {
			cipher.init(Cipher.ENCRYPT_MODE, sk);
		}
		return new Ciphertext(iv, cipher.doFinal(ptxt));
	}	
	
	 






	public byte [] decrypt(Ciphertext ctxt) throws Exception
	{
		if (sk == null) throw new IllegalStateException("Encryption key has not been set");
		if (encName.toUpperCase().contains("/CBC/") || encName.toUpperCase().contains("/CTR/"))
			cipher.init(Cipher.DECRYPT_MODE, sk, new IvParameterSpec(ctxt.getParam()));
		else
			cipher.init(Cipher.DECRYPT_MODE, sk);
		return cipher.doFinal(ctxt.getText());
	}
	
	
	public String getEncName()
	{
		return encName;
	}

	public void setEncName(String encName)
	{
		this.encName = encName;
	}

	public SecretKey getKey()
	{
		return sk;
	}
	
	public void setKey(SecretKey sk)
	{
		this.sk = sk;
	}
	
	public void setKeyBytes(byte[] keyBytes)
	{
		this.sk = new SecretKeySpec(keyBytes, encName.split("/")[0]);
	}

}
