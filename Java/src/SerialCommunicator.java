import java.io.IOException;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;


public class SerialCommunicator implements MessageListener {
	public MoteIF mote;
	public PhoenixSource phoenix;
	public DataManager manager;
	public long hours_to_get;
	
	public SerialCommunicator() {
		phoenix = BuildSource.makePhoenix("serial@/dev/ttyUSB0:telos", PrintStreamMessenger.err);
		mote = new MoteIF(phoenix);
		mote.registerListener(new HarvestAnswerSerialMsg(), this);
	}
	
	public void sendPacket(short n) throws IOException {
		System.out.println("bout to send...");
		CloudCoverageSerialMsg msg = new CloudCoverageSerialMsg();
		msg.set_slot(n);
		System.out.println("really?");
		mote.send(0, msg);
		System.out.println("hue");
		//System.out.println("cent");
		
	}
	
	public synchronized void messageReceived(int to, Message message) {
		
		System.out.println("abcd");
		HarvestAnswerSerialMsg msg = (HarvestAnswerSerialMsg)message;
		System.out.println(msg);
		manager.new_packet(msg.get_result());
		--hours_to_get;
		if(hours_to_get > 0) {
			try {
				sendPacket((short)hours_to_get);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			manager.can_do_charts = true;
			manager.updateTime();
		}
	}

	public void getData(long hours_dif) {
		System.out.println("wtf m8");
		hours_to_get = hours_dif;
		if(hours_to_get > 0) {
			System.out.println("bin laden");
			try {
				sendPacket((short)hours_to_get);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			manager.can_do_charts = true;
		}
	}
}
