package lsrs4.crypto;

 
 

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import lsrs4.misc.Utils;

 



public class GenKeyPair 
{
	String algName;
	
	public GenKeyPair(String algName) throws Exception
	{
		this.algName = algName;
	}

	public KeyPair genKeys(int len) throws Exception
	{
		KeyPairGenerator generator = KeyPairGenerator.getInstance(algName);
		generator.initialize(len, new SecureRandom());
		KeyPair pair = generator.generateKeyPair();
		 
		return pair;
	}
	
	public String rsaKeyToStr(KeyPair pair) throws Exception 
	{
		PrivateKey priv = pair.getPrivate();
		PublicKey pub = pair.getPublic();
		return rsaKeyToStr(pub, priv);
	}
	
	public String rsaKeyToStr(PublicKey pub, PrivateKey priv) throws Exception
	{
		KeyFactory keyFact = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec pubKeySpec =
			(RSAPublicKeySpec) keyFact.getKeySpec(pub, RSAPublicKeySpec.class);
		RSAPrivateKeySpec privKeySpec =
			(RSAPrivateKeySpec) keyFact.getKeySpec(priv, RSAPrivateKeySpec.class);

		byte[] n = pubKeySpec.getModulus().toByteArray();
		byte[] e = pubKeySpec.getPublicExponent().toByteArray();
		byte[] d = privKeySpec.getPrivateExponent().toByteArray();

		String ns = "\nn["+n.length+"] = {\n  " + Utils.toHex(n) + "}";
		String es = "\ne["+e.length+"] = {\n  " + Utils.toHex(e) + "}";
		String ds = "\nd["+d.length+"] = {\n  " + Utils.toHex(d) + "}";
		
		return ns+es+ds;
	}

}
