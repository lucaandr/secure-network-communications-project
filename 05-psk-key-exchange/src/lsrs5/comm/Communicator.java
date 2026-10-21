package lsrs5.comm;

 

import java.util.HashMap;
import java.util.Map;

import lsrs5.application.AppUserIntf;
import lsrs5.comm.AuthMsg;
import lsrs5.misc.Utils;

 






public class Communicator 
{
	private boolean isAdversary = false;  
	
	protected Map<String, AppUserIntf> parties; 
	
	public Communicator()
	{
		parties = new HashMap<>();
	}
	
	public void setParty(String name, AppUserIntf party) throws Exception
	{
		parties.put(name, party);
	}

	public AppUserIntf getParty(String name)
	{
		return parties.get(name);
	}

	public void sendDataMsg(DataMsg msg) throws Exception 
	{
		Utils.debug("[Comm] "+msg.toHexStr());
		AppUserIntf d = parties.get(msg.getDst());
		d.recvData(msg); 
	}
	
	public void sendKeyExchMsg(KeyExchMsg msg) throws Exception 
	{
		Utils.debug("[Comm] "+msg.toHexStr());
		AppUserIntf d = parties.get(msg.getDst());
		d.recvKeyExch(msg); 
	}
	
	public void sendAuthMsg(AuthMsg msg) throws Exception 
	{
		Utils.debug("[Comm] "+msg.toHexStr());
		AppUserIntf d = parties.get(msg.getDst());
		d.recvAuth(msg); 
	}

	public void sendFailMsg(FailMsg msg) throws Exception 
	{
		Utils.debug("[Comm] "+msg.toHexStr());
		AppUserIntf d = parties.get(msg.getDst());
		d.recvFail(msg); 
	}
	
	public boolean isAdversary()
	{
		return isAdversary; 
	}

}
