package lsrs1.attacks;

 

import lsrs1.application.SeCom1;
import lsrs1.comm.Communicator;
import lsrs1.comm.DataMsg;
import lsrs1.crypto.Ciphertext;
import lsrs1.misc.Utils;

 











public class Adv1 extends Communicator
{
	 
	 
	private int index = 16; 
	
	public void sendDataMsg(DataMsg msg) throws Exception
	{
		Utils.debug("[Comm] "+msg.toHexStr());
		SeCom1 d = parties.get(msg.getDst());
		if (msg.getSrc().equals("alice") && msg.getDst().equals("bob")) {
			byte[] data = msg.getData().getText();
			byte[] param = msg.getData().getParam(); 
			 
			byte[] fakeData = new byte[data.length];
			System.arraycopy(data, 0, fakeData, 0, data.length);
			fakeData[index] ^= 8; 
			Ciphertext fakeCtxt = new Ciphertext(param, fakeData); 
			DataMsg fakeDataMsg = new DataMsg(msg.getSrc(), msg.getDst(), fakeCtxt);
			Utils.debug("[Adv1] Forged "+fakeDataMsg.toHexStr());
			d.recvData(fakeDataMsg); 
		}
		else 
		{
			d.recvData(msg); 
		}
	}
}
