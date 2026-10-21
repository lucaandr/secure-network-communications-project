package lsrs4.crypto;

 

import java.security.KeyStore;
import java.security.KeyStore.*;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;

import javax.crypto.SecretKey;

import lsrs4.misc.Utils;

 






public class KeyManagerKS
{
	private String ksFile;
	private String ksPwd;
	private KeyStore ks;

	public KeyManagerKS()
	{ }

	public KeyManagerKS(String ksFile, String ksPwd) throws Exception
	{
		initKeyStore(ksFile, ksPwd);
		saveKeyStore();
	}

	public void initKeyStore(String ksFile, String ksPwd) throws Exception
	{
		this.ksFile = ksFile;
		this.ksPwd = ksPwd;
		ks = KeyStore.getInstance("PKCS12");
	    java.io.FileInputStream fis = null;
	    try {
	        fis = new java.io.FileInputStream(ksFile);
	        ks.load(fis, ksPwd.toCharArray());
	    }
	    catch (Exception FileNotFoundException) {
	    	ks.load(null, ksPwd.toCharArray());
	    }
	    finally {
	        if (fis != null) {
	            fis.close();
	        }
	    }
	}

	public void saveKeyStore() throws Exception
	{
	    java.io.FileOutputStream fos = null;
	    try {
	        fos = new java.io.FileOutputStream(ksFile);
	        ks.store(fos, ksPwd.toCharArray());
	    } finally {
	        if (fos != null) {
	            fos.close();
	        }
	    }
	}

	public void setSecretKey(String alias, SecretKey key) throws Exception
	{
		setSecretKey(alias, key, ksPwd.toCharArray());
	}

	public void setSecretKey(String alias, SecretKey key, char[] pwd) throws Exception
	{
		ProtectionParameter protParam = new PasswordProtection(pwd);
		SecretKeyEntry skEntry = new SecretKeyEntry(key);
		ks.setEntry(alias, skEntry, protParam);
	}

	public void setPrivateKeyEntry(String alias, PrivateKey key,
			Certificate[] cert) throws Exception
	{
		setPrivateKeyEntry(alias, key, cert, ksPwd.toCharArray());
	}

	public void setPrivateKeyEntry(String alias, PrivateKey key,
			Certificate[] cert, char[] pwd) throws Exception
	{
		ProtectionParameter protParam = new PasswordProtection(pwd);
		PrivateKeyEntry skEntry = new PrivateKeyEntry(key, cert);
		ks.setEntry(alias, skEntry, protParam);
	}

	public void setCertificate(String alias, Certificate cert) throws Exception
	{
		ks.setCertificateEntry(alias, cert);
	}

	public SecretKey getSecretKey(String alias) throws Exception
	{
		return getSecretKey(alias, ksPwd.toCharArray());
	}

	public SecretKey getSecretKey(String alias, char[] pwd) throws Exception
	{
		ProtectionParameter protParam = new PasswordProtection(pwd);
		if (!ks.entryInstanceOf(alias, SecretKeyEntry.class)) {
			throw new Exception("Key Store: No secret key for alias " + alias);
        }
		SecretKeyEntry skEntry = (SecretKeyEntry) ks.getEntry(alias, protParam);
		return skEntry.getSecretKey();
	}

	public PrivateKeyEntry getPrivateKeyEntry(String alias) throws Exception
	{
		return getPrivateKeyEntry(alias, ksPwd.toCharArray());
	}

	public PrivateKeyEntry getPrivateKeyEntry(String alias, char[] pwd) throws Exception
	{
		ProtectionParameter protParam = new PasswordProtection(pwd);
		if (!ks.entryInstanceOf(alias, PrivateKeyEntry.class)) {
			throw new Exception("Key Store: No private key for alias " + alias);
        }
		PrivateKeyEntry pkEntry = (PrivateKeyEntry) ks.getEntry(alias, protParam);
		return pkEntry;
	}

	public PrivateKey getPrivateKey(String alias) throws Exception
	{
		PrivateKeyEntry pkEntry = getPrivateKeyEntry(alias, ksPwd.toCharArray());
		return pkEntry.getPrivateKey();
	}

	public Certificate getSelfCert(String alias) throws Exception
	{
		PrivateKeyEntry pkEntry = getPrivateKeyEntry(alias, ksPwd.toCharArray());
		return pkEntry.getCertificate();
	}

	public X509Certificate getCert(String alias) throws Exception
	{
		Certificate cert = ks.getCertificate(alias);
		if (!(cert instanceof X509Certificate)) {
			throw new Exception("Key Store: Wrong PKC entry for alias " + alias);
		}
		return (X509Certificate) cert;
	}

	public boolean isPrivateKeyEntry(String alias) throws Exception
	{
		return ks.entryInstanceOf(alias, PrivateKeyEntry.class);
	}

	public String getFileName()
	{
		return ksFile;
	}

     



    public X509Certificate getTrustedIssuerCert(X509Certificate cert) throws Exception
    {
       
      X509Certificate found = null;
      ArrayList<String> aliasList = Collections.list(ks.aliases());
      for (String alias : aliasList) {
    	   
    	  if (!ks.entryInstanceOf(alias, TrustedCertificateEntry.class)) {
              continue;
          }
    	  Certificate tc = ((TrustedCertificateEntry)
    			  ks.getEntry(alias, null)).getTrustedCertificate();
    	   
    	  if (!(tc instanceof X509Certificate)) {
              continue;
          }
    	  X509Certificate xtc = (X509Certificate) tc;
    	   
           
    	  if (!cert.getIssuerX500Principal().getName().
    			  equals(xtc.getSubjectX500Principal().getName())) {
              continue;
          }
          found = xtc;  
      }
      if (found == null) {
          throw new Exception("Key Store: No issuer PKC for " + cert);
      }
      return found;
    }

    public void list(String str) throws Exception
    {
    	Utils.debug(str + "Keystore summary: " + ksFile);
    	 
    	ArrayList<String> aliasList = Collections.list(ks.aliases());
    	for (String alias : aliasList) {
    		if (ks.entryInstanceOf(alias, PrivateKeyEntry.class)) {
    			Utils.debug(" Alias " + alias + ": private key");
    		}
    		if (ks.entryInstanceOf(alias, SecretKeyEntry.class)) {
    			Utils.debug(" Alias " + alias + ": secret key");
    		}
    		if (ks.entryInstanceOf(alias, TrustedCertificateEntry.class)) {
    			Utils.debug(" Alias " + alias + ": trusted certificate");
    		}
    	}
    }
}
