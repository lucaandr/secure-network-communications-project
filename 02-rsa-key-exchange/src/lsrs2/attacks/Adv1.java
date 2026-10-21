package lsrs2.attacks;

 

import lsrs2.application.AppUserIntf;
import lsrs2.comm.Communicator;
import lsrs2.comm.DataMsg;
import lsrs2.misc.Utils;

 











public class Adv1 extends Communicator
{
	 
	 
	private int index = 16; 
	
	public void sendDataMsg(DataMsg msg) throws Exception
	{
		Utils.debug("[Comm] "+msg.toHexStr());
		AppUserIntf d = parties.get(msg.getDst());
		if (msg instanceof DataMsg) {
			if (msg.getSrc().equals("alice") && msg.getDst().equals("bob")) {
				byte[] data = msg.getData().getText();
				 
				byte[] fakeData = new byte[data.length];
				System.arraycopy(data, 0, fakeData, 0, data.length);
				fakeData[index] ^= 8; 
				msg.getData().setText(fakeData);
				Utils.debug("[Adv1] Forged "+msg.toHexStr());
				d.recvData(msg); 
			}
		}
		d.recvData(msg); 
	}
}
