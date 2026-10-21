package lsrs2.crypto;

 

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.SecretKey;

import lsrs2.misc.Utils;

 






public class KeyManager 
{
	protected Map<String, Key> keyStore; 
	String userId; 
	
	public KeyManager(String locId)
	{
		keyStore = new TreeMap<>();  
		this.userId = locId; 
	}

	public void setKey(String name, Key key) throws Exception
	{
		keyStore.put(name, key);  
		Utils.debug("[KeyManager: " + userId + "] " + keyNamesToStr());
	}

	public Key getKey(String name) throws Exception
	{
		if (keyStore.containsKey(name))
			return keyStore.get(name);
		else
			throw new Exception("[KeyManager] Key not found: no entry");
	}

	public void remKey(String name) throws Exception
	{
		keyStore.remove(name); 
	}
	
	public PublicKey getPublicKey(String name) throws Exception
	{
		Key key = getKey(name);
		if (key instanceof PublicKey)
			return (PublicKey) key;
		else 
			throw new Exception("[KeyManager] Key not found: wrong type");
	}

	public PrivateKey getPrivateKey(String name) throws Exception
	{
		Key key = getKey(name);
		if (key instanceof PrivateKey)
			return (PrivateKey) key;
		else 
			throw new Exception("[KeyManager] Key not found: wrong type");
	}
	
	public SecretKey getSecretKey(String name) throws Exception
	{
		Key key = getKey(name);
		if (key instanceof SecretKey)
			return (SecretKey) key;
		else 
			throw new Exception("[KeyManager] Key not found: wrong type");
	}

	public String keyNamesToStr()
	{
		String s = "Key entries:";
		for (String name : keyStore.keySet())
		    s = s + " " + name;
		return s;
	}
}
