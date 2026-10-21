package lsrs5.crypto;

 

import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lsrs5.comm.AuthMsg;
import lsrs5.comm.Communicator;
import lsrs5.comm.FailMsg;
import lsrs5.comm.KeyExchMsg;
import lsrs5.misc.Utils;

 













public class KeyExchPSKMac 
{
	private String locId; 
	private String remId; 
	private Communicator comm;

	private KeyManagerKS km; 
	private String keyAuthName;  
	private int nonceLen = 256;  
	private byte[] locNonce;  
	private byte[] remNonce; 
	private int state;  
	private boolean success; 

	private int keyLen;  
	private String keyAlgName = "AES";  
	private SecretKey keyEnc;  
	private SecretKey keyMac;  

	public KeyExchPSKMac(String locId, Communicator comm, String keyAuthName, int keyLen) 
	{ 
		this.comm = comm;
		this.locId = locId; 
		this.keyAuthName = keyAuthName;
		this.keyLen = keyLen;
		state = 0; 
		success = false; 
	}
	
	public void setKeyManager(KeyManagerKS km)
	{
		this.km = km;
	}

	 


	public void doKeyExch(String remId) throws Exception
	{
		this.remId = remId; 
		 
		if (state == 0)	{
			state = 1;      
			sendKeyExch();  
		}
	}
	
	 


	public void sendKeyExch() throws Exception
	{ 
		 
		Utils.debug("[KeyExch: "+locId+"] sendKeyExch, new state " + state);
		locNonce = new byte[nonceLen / 8];
		new SecureRandom().nextBytes(locNonce);
		comm.sendKeyExchMsg(new KeyExchMsg(locId, remId, locNonce));

	}

	 



	public void recvKeyExch(KeyExchMsg msg) throws Exception
	{
		Utils.debug("[KeyExch: "+locId+"] recvKeyExch in state " + state); 
		switch (state) {
		case 0:  
			remId = msg.getSrc();
			remNonce = msg.getNonce();
			state = 2;
			sendKeyExch();

			break;
		case 1:  
			if (!remId.equals(msg.getSrc())) { sendFail(msg.getSrc(), "Unexpected peer"); return; }
			remNonce = msg.getNonce();
			state = 3;
			sendAuth();

			break;
		case 4:  
			break;  
		case 5:  
			break;  
		default:  
			sendFail(msg.getSrc(), "[KeyExch: "+locId+"] Illegal state " + state);  
		}
	}

	 



	public void sendAuth() throws Exception
	{
		 
		Utils.debug("[KeyExch: "+locId+"] sendAuth, new state " + state);
		Mac mac = Mac.getInstance(keyAuthName);
		mac.init(km.getSecretKey(remId + "_ake"));
		byte[] auth = mac.doFinal(Utils.concatenate(locId.getBytes(java.nio.charset.StandardCharsets.UTF_8), locNonce, remNonce));
		comm.sendAuthMsg(new AuthMsg(locId, remId, auth));

	}
	
	 






	public void recvAuth(AuthMsg msg) throws Exception
	{
		Utils.debug("[KeyExch: "+locId+"] recvAuth in state " + state);
		if (!remId.equals(msg.getSrc())) {
			sendFail(msg.getSrc(), "[Key exchange] Illegal state"); 
			return;
		}
		switch(state) {  
		case 2:  
			 
			success = verAuth(msg); 
			if (success) { 
				Utils.debug("  Successful verification of " + remId + "'s authenticator (MAC)\n" 
						+ "  Successful authentication: remote user is " + remId);
				 
				genSessionKeys(); 
				state = 4;  
				sendAuth(); 
				Utils.debug("[KeyExch: "+locId+"] Responder. Auth handshake done, state " 
						+ state + ". Success");
				return;
			}
			else {  
				Utils.debug("  Failed authentication of " + remId); 
				sendFail(remId, "Responder: Key exchange failed");  
				return; 
			}
		case 3:  
			 
			success = verAuth(msg); 
			if (success) { 
				Utils.debug("  Successful verification of " + remId + "'s authenticator (MAC)\n" 
						+ "  Successful authentication: remote user is " + remId);
				 
				genSessionKeys(); 
				state = 4;  
				Utils.debug("[KeyExch: "+locId+"] Initiator. Auth handshake done, state " 
						+ state + ". Success");
				return;
			}
			else {  
				Utils.debug("  Failed authentication of " + remId); 
				sendFail(remId, "Initiator: Key exchange failed");  
				return; 
			}
		case 4:  
			return;  
		case 5:  
			return;  
		default:  
			sendFail(msg.getSrc(), "[KeyExch: "+locId+"] Illegal state " + state);  
			return;
		}
	}

	 


	private boolean verAuth(AuthMsg msg) throws Exception
	{
		Mac mac = Mac.getInstance(keyAuthName);
		mac.init(km.getSecretKey(remId + "_ake"));
		byte[] expected = mac.doFinal(Utils.concatenate(remId.getBytes(java.nio.charset.StandardCharsets.UTF_8), remNonce, locNonce));
		return java.security.MessageDigest.isEqual(expected, msg.getAuth());
	}
	
	 


	private void genSessionKeys() throws Exception
	{
		 
		byte[] keyBytes = (state == 3)? 
				genKeyBytes(locNonce, remNonce):  
				genKeyBytes(remNonce, locNonce);  
		byte[] keyEncBytes = new byte[keyLen/8];
		byte[] keyMacBytes = new byte[keyLen/8];
		Utils.split(keyBytes, keyEncBytes, keyMacBytes); 
		keyEnc = new SecretKeySpec(keyEncBytes, keyAlgName);
		keyMac = new SecretKeySpec(keyMacBytes, keyAlgName); 

		 
		 
		km.setSecretKey(remId + "_secret_key_enc", keyEnc);
		km.setSecretKey(remId + "_secret_key_mac", keyMac);
		
		Utils.debug("[KeyExch: "+locId+"] Session keys:\n"+
				" keyEnc["+ keyEncBytes.length +"]:\n  " + 
				Utils.toHex(keyEncBytes) + "\n" +
				" keyMac["+ keyMacBytes.length +"]:\n  " + 
				Utils.toHex(keyMacBytes));		
	}
	
	 






	private byte[] genKeyBytes(byte[] n1, byte[] n2) throws Exception
	{
		SecretKey kdfKey = km.getSecretKey(remId + "_kdf"); 
		byte[] input = Utils.concatenate(n1, n2);  
		Mac macAlg = Mac.getInstance("HMACSHA256");
		macAlg.init(kdfKey); 
		return macAlg.doFinal(input); 
	}

	 


	public void recvFail(FailMsg msg) throws Exception
	{
		state = 5;  
		success = false;
		Utils.debug("[KeyExch: "+ locId + "] recvFail, new state 5 (Fail). From " + 
				msg.getSrc() + ": " + msg.getReason());
		try { 
			km.delSecretKey(remId+"_secret_key_mac");
			km.delSecretKey(remId+"_secret_key_enc");
		} catch (Exception e) {}
	}
	
	private void sendFail(String dstId, String s) throws Exception
	{
		state = 5;  
		success = false;
		Utils.debug("[KeyExch: "+ locId + "] sendFail, new state 5 (Fail). To " + 
				dstId + ": " + s);
		FailMsg msg = new FailMsg(locId, dstId, s);
		comm.sendFailMsg(msg);
	}
	
	public SecretKey getKeyEnc()
	{
		return keyEnc;
	}

	public SecretKey getKeyMac()
	{
		return keyMac;
	}
	
	public boolean isSuccess()
	{
		 
		return (state == 4) && success; 
	}

	public boolean isFail()
	{
		return (state == 5); 
	}

	public boolean isFinished()
	{
		return (state == 4) || (state == 5); 
	}

}
