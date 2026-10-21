package lsrs3.comm;

import java.util.HashMap;
import java.util.Map;

import lsrs3.application.AppUserIntf;
import lsrs3.misc.Utils;

 






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
	
	public boolean isAdversary()
	{
		return isAdversary; 
	}

}
