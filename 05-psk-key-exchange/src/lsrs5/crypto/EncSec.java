package lsrs5.crypto;

 

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

	 








	public Ciphertext encrypt(byte[] ptxt) throws Exception
	{
		cipher.init(Cipher.ENCRYPT_MODE, sk);
		byte[] etxt = cipher.doFinal(ptxt); 
		byte[] param = cipher.getIV();
		 
		return new Ciphertext(param, etxt);
	}
	
	 







	public Ciphertext encrypt(byte[] param, byte[] ptxt) throws Exception
	{
		IvParameterSpec ivSpec = new IvParameterSpec(param);
		cipher.init(Cipher.ENCRYPT_MODE, sk, ivSpec);
		byte[] etxt = cipher.doFinal(ptxt); 
		 
		return new Ciphertext(param, etxt);
	}
	
	 







	public byte[] decrypt(byte[] param, byte[] ctxt) throws Exception
	{
		 
		IvParameterSpec ivSpec = new IvParameterSpec(param);
		cipher.init(Cipher.DECRYPT_MODE, sk, ivSpec);
		byte[] ptxt = cipher.doFinal(ctxt); 
		return ptxt;
	}

	public byte[] decrypt(Ciphertext ctxt) throws Exception
	{
		return decrypt(ctxt.getIV(), ctxt.getText()); 
	}

	public String getEncName()
	{
		return encName;
	}

	public void setEncName(String encName)
	{
		this.encName= encName;
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
