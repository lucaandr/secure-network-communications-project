package lsrs1.application;

 

import javax.crypto.SecretKey;

import lsrs1.comm.Communicator;
import lsrs1.comm.DataMsg;
import lsrs1.crypto.KeyManager;
import lsrs1.crypto.GenSecKey;
import lsrs1.crypto.EncSec;
import lsrs1.crypto.Ciphertext;
import lsrs1.misc.Utils;

 







public class SeCom1
{
	private String locId; 
	private Communicator comm;

	private EncSec encSec;
	private KeyManager km; 
	
	public SeCom1(String locId, Communicator comm) throws Exception
	{
		this.locId = locId;
		this.comm = comm;
		 
		comm.setParty(locId, this); 
		km = new KeyManager(locId);
	}

	 


	public void setEncSecName(String encSecName) throws Exception
	{
		encSec = new EncSec(encSecName); 
	}

	 



	public void setSecretKey(String remId, SecretKey secKey) throws Exception
	{
		km.setKey(remId + "_secret_key", secKey);
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
