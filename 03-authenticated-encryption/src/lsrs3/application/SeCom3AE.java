package lsrs3.application;

 

import javax.crypto.SecretKey;

import lsrs3.comm.Communicator;
import lsrs3.comm.DataMsg;
import lsrs3.crypto.AuthEncEtM;
import lsrs3.crypto.Ciphertext;
import lsrs3.crypto.KeyManager;
import lsrs3.misc.Utils;

 







public class SeCom3AE implements AppUserIntf
{
	private String locId; 
	private Communicator comm;
	
	private AuthEncEtM authEnc;
	private KeyManager km; 
	
	public SeCom3AE(String locId, Communicator comm) throws Exception
	{
		this.locId = locId;
		this.comm = comm;
		 
		comm.setParty(locId, this); 
		km = new KeyManager(locId);
	}

	public void setAuthEncName(String secAuthName, String secEncName) throws Exception
	{
		authEnc = new AuthEncEtM(); 
		authEnc.setEncName(secEncName);
		authEnc.setMacName(secAuthName);
	}
	
	 




	public void setSecretKey(String remId, String use, SecretKey secKey) throws Exception
	{
		km.setKey(remId + "_secret_key_" + use, secKey);
	}
	
	public void sendData(String dstId, byte[] ptxt) throws Exception
	{
		authEnc.setKeyEnc(km.getSecretKey(dstId + "_secret_key_enc"));
		authEnc.setKeyMac(km.getSecretKey(dstId + "_secret_key_mac"));
		Ciphertext ctxt = authEnc.encrypt(ptxt);
		Utils.debug("[Participant: "+ locId + "]: sends message to "+dstId+"\n  "+Utils.toHexExt(ptxt));
		comm.sendDataMsg(new DataMsg(locId, dstId, ctxt));
	}

	public byte[] recvData(DataMsg dataMsg) throws Exception
	{
		authEnc.setKeyEnc(km.getSecretKey(dataMsg.getSrc() + "_secret_key_enc"));
		authEnc.setKeyMac(km.getSecretKey(dataMsg.getSrc() + "_secret_key_mac"));
		byte[] ptxt = authEnc.decrypt(dataMsg.getData());
		Utils.debug("[Participant] "+locId+": rcvd message from "+dataMsg.getSrc()+ 
				". Accepted\n  "+Utils.toHexExt(ptxt)); 
		return ptxt;
	}
		
	public String getId()
	{
		return locId;
	}

}
