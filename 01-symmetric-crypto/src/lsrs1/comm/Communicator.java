package lsrs1.comm;

 

import java.util.HashMap;
import java.util.Map;

import lsrs1.application.SeCom1;
import lsrs1.misc.Utils;

 






public class Communicator 
{
	private boolean isAdversary = false;  
	
	protected Map<String, SeCom1> parties; 
	
	public Communicator()
	{
		parties = new HashMap<>();
	}
	
	public void setParty(String name, SeCom1 party) throws Exception
	{
		parties.put(name, party);
	}

	public SeCom1 getParty(String name)
	{
		return parties.get(name);
	}

	public void sendDataMsg(DataMsg msg) throws Exception 
	{
		Utils.debug("[Comm] "+msg.toHexStr());
		SeCom1 d = parties.get(msg.getDst());
		d.recvData(msg); 
	}
	
	public boolean isAdversary()
	{
		return isAdversary; 
	}

}
