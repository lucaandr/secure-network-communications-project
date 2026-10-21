package lsrs1.crypto;

 

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import lsrs1.misc.Utils;

 






public class GenSecKey 
{
	private String algName; 
	
	public GenSecKey(String algName)
	{
		this.algName = algName;
	}
	
	 






	public SecretKey genKey(int keyLen) throws Exception
	{
		KeyGenerator keygen = KeyGenerator.getInstance(algName.split("/")[0]);
		keygen.init(keyLen);
		SecretKey sk = keygen.generateKey();
		Utils.debug("[genKey] key["+keyLen+"]: { "+Utils.toHex(sk.getEncoded())+" }");
		return sk;
	}
	
	public String keyToStr(SecretKey sk) throws Exception 
	{
		return Utils.toHex(sk.getEncoded());
	}
}
