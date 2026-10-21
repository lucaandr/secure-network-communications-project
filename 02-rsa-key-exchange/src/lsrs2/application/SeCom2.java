package lsrs2.application;

 

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lsrs2.comm.*;
import lsrs2.crypto.*;
import lsrs2.misc.Utils;

 







public class SeCom2 implements AppUserIntf
{
	private String locId; 
	private Communicator comm;

	private String encPubName; 
	private EncSec encSec;
	private KeyManager km; 
	
	public SeCom2(String locId, Communicator comm) throws Exception
	{
		this.locId = locId;
		this.comm = comm;
		 
		comm.setParty(locId, this); 
		km = new KeyManager(locId);
	}
	
	public void setEncPubName(String encPubName)
	{
		this.encPubName = encPubName;
	}

	public void setEncSecName(String encSecName) throws Exception
	{
		encSec = new EncSec(encSecName); 
	}
	
	 



	public void genKeyPair(int keyLen) throws Exception
	{
		 
		GenKeyPair genKeyPair = new GenKeyPair(encPubName.split("/")[0]);
		KeyPair locKeyPair = genKeyPair.genKeys(keyLen); 
		km.setKey(locId + "_private_key", locKeyPair.getPrivate());
		km.setKey(locId + "_public_key", locKeyPair.getPublic());
	}

	 


	public PublicKey getPubKey() throws Exception
	{
		return km.getPublicKey(locId + "_public_key"); 
	}

	 



	public void recvPubKey(String remId, PublicKey pubKey) throws Exception
	{
		km.setKey(remId + "_public_key", pubKey);
	}

	 


	public void sendKeyExch(String remId, int keyLen) throws Exception
	{
		GenSecKey gen = new GenSecKey(encSec.getEncName());
		SecretKey sessionKey = gen.genKey(keyLen);
		km.setKey(remId + "_secret_key", sessionKey);
		EncPub encPub = new EncPub(encPubName);
		encPub.setPubKey(km.getPublicKey(remId + "_public_key"));
		byte[] encryptedKey = encPub.encrypt(sessionKey.getEncoded());
		comm.sendKeyExchMsg(new KeyExchMsg(locId, remId, encryptedKey));

	}

	 


	public void recvKeyExch(KeyExchMsg msg) throws Exception
	{
		EncPub encPub = new EncPub(encPubName);
		encPub.setPrivKey(km.getPrivateKey(locId + "_private_key"));
		byte[] keyBytes = encPub.decrypt(msg.getData());
		km.setKey(msg.getSrc() + "_secret_key", new SecretKeySpec(keyBytes, encSec.getEncName().split("/")[0]));

	}
	
	 


	public void sendData(String dstId, byte[] ptxt) throws Exception
	{
		encSec.setKey(km.getSecretKey(dstId + "_secret_key"));
		Ciphertext ctxt = encSec.encrypt(ptxt);
		Utils.debug("[Participant: "+ locId + "]: sends message to "+dstId+"\n  "+Utils.toHexExt(ptxt));
		comm.sendDataMsg(new DataMsg(locId, dstId, ctxt));
	}

	 


	public byte[] recvData(DataMsg dataMsg) throws Exception
	{
		encSec.setKey(km.getSecretKey(dataMsg.getSrc() + "_secret_key"));
		byte[] ptxt = encSec.decrypt(dataMsg.getData());
		Utils.debug("[Participant: "+locId+"]: rcvd message from "+dataMsg.getSrc()+"\n  "+Utils.toHexExt(ptxt));
		return ptxt;
	}
	
	public String getId()
	{
		return locId;
	}

}
