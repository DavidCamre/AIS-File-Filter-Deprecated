package dk.dma.ais;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import dk.dma.ais.binary.SixbitException;
import dk.dma.ais.filter.IPacketFilter;
import dk.dma.ais.message.AisMessage;
import dk.dma.ais.message.AisMessage21;
import dk.dma.ais.message.AisMessageException;
import dk.dma.ais.message.AisPositionMessage;
import dk.dma.ais.message.AisStaticCommon;
import dk.dma.ais.packet.AisPacket;
import dk.dma.ais.reader.AisReader;
import dk.dma.ais.reader.AisReaders;
import dk.dma.enav.util.function.Consumer;

public class AisStatistics implements Consumer<AisPacket> {
	private final List<IPacketFilter> filters = new CopyOnWriteArrayList<>();

	private int messageCounter = 0;
	private HashMapCounter<Integer> messageTypes = new HashMapCounter<>();
	private HashMapCounter<Integer> mmsiCounter = new HashMapCounter<>();

	private AisReader reader;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new AisStatistics("ais-data.dat");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getStatisticsData() {

		String statisticsData = "<html>Statistics on file:<br>";
		statisticsData = statisticsData + "Total Messages: " + messageCounter + "<br>";
		for (Entry<Integer, Integer> messageType : messageTypes.entrySet()) {
			statisticsData = statisticsData + "Message Type: " + messageType.getKey() + " counts: "
					+ messageType.getValue() + "<br>";
		}

		System.out.println("Unique MMSI: " + mmsiCounter.size());
		statisticsData = statisticsData + "Unique MMSI: " + mmsiCounter.size();

		statisticsData = statisticsData + "<html>";
		return statisticsData;
	}

	public boolean start() {
		try {
			reader.start();
			reader.join();

			System.out.println("Returning true");

			return true;
		} catch (Exception e) {
		}

		return false;
	}

	public AisStatistics(String path) throws IOException, InterruptedException {
		reader = AisReaders.createReaderFromFile(path);
		reader.registerPacketHandler(this);

	}

	public void done() {

	}

	@Override
	public void accept(AisPacket aisPackage) {
		messageCounter++;

		AisMessage aisMessage;
		try {
			aisMessage = aisPackage.getAisMessage();

			if (aisMessage != null) {
				// System.out.println("message mmsi " + aisMessage.getUserId());
				int mmsi = aisMessage.getUserId();
				mmsiCounter.add(mmsi);

				// Handle AtoN message
				if (aisMessage instanceof AisMessage21) {
					AisMessage21 msg21 = (AisMessage21) aisMessage;
					// System.out.println("AtoN name: " + msg21.getName());

				}
				// Handle position messages 1,2 and 3 (class A) by using their
				// shared
				// parent
				if (aisMessage instanceof AisPositionMessage) {
					AisPositionMessage posMessage = (AisPositionMessage) aisMessage;
					// System.out.println("speed over ground: " +
					// posMessage.getSog());

				}

				// Handle static reports for both class A and B vessels (msg 5 +
				// 24)
				if (aisMessage instanceof AisStaticCommon) {
					AisStaticCommon staticMessage = (AisStaticCommon) aisMessage;
					// System.out.println("vessel name: " +
					// staticMessage.getName());
				}
			}
		} catch (AisMessageException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SixbitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (aisPackage.getAisMessage() != null) {
				Integer messageId = aisPackage.getAisMessage().getMsgId();
				// System.out.println("ID: " + messageId);
				messageTypes.add(aisPackage.getAisMessage().getMsgId());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		// System.out.println(aisPackage.getTags().getSourceType());

		// for (IPacketFilter filter : filters) {
		// if (filter.rejectedByFilter(aisPackage)) {
		// System.out.println("Rejecting");
		// return;
		// }
		// }

		// try {
		// System.out.println(aisPackage.getAisMessage().reassemble());
		// } catch (AisMessageException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SixbitException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

}
