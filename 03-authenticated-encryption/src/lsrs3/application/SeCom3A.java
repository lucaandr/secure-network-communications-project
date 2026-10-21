package lsrs3.application;

 

import javax.crypto.SecretKey;

import lsrs3.comm.Communicator;
import lsrs3.comm.DataMsg;
import lsrs3.crypto.AuthSec;
import lsrs3.crypto.Ciphertext;
import lsrs3.crypto.KeyManager;
import lsrs3.misc.Utils;

 







public class SeCom3A implements AppUserIntf
{
	private String locId; 
	private Communicator comm;
	
	private AuthSec authSec;	
	private KeyManager km;
	
	public SeCom3A(String locId, Communicator comm) throws Exception
	{
		this.locId = locId;
		this.comm = comm;
		 
		comm.setParty(locId, this); 
		km = new KeyManager(locId);
	}
	
	public void setAuthSecName(String authSecName) throws Exception
	{
		authSec = new AuthSec(authSecName); 
	}
	
	 




	public void setSecretKey(String remId, String use, SecretKey secKey) throws Exception
	{
		km.setKey(remId + "_secret_key_" + use, secKey);
	}
	
	public void sendData(String dstId, byte[] ptxt) throws Exception
	{
		authSec.setKey(km.getSecretKey(dstId + "_secret_key_mac"));
		byte[] mac = authSec.genMac(ptxt);
		Ciphertext ctxt = new Ciphertext(null, ptxt, mac);
		Utils.debug("[Participant: "+ locId + "]: sends message to "+dstId+"\n  "+Utils.toHexExt(ptxt));
		comm.sendDataMsg(new DataMsg(locId, dstId, ctxt));
	}

	public byte[] recvData(DataMsg dataMsg) throws Exception
	{
		authSec.setKey(km.getSecretKey(dataMsg.getSrc() + "_secret_key_mac"));
		byte[] ptxt = dataMsg.getData().getText();
		boolean accept = authSec.verMac(ptxt, dataMsg.getData().getMac());
		Utils.debug("[Participant] "+locId+": rcvd message from "+dataMsg.getSrc()+
			". "+ ((accept)? "Accepted." : "Rejected") + "\n  "+Utils.toHexExt(ptxt));
		if (accept) 
			return ptxt;
		else 
			throw new Exception("[AppUserMac] Data authentication failed");
	}
	
	public String getId()
	{
		return locId;
	}

}
