package dk.dma.ais;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
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
import dk.dma.ais.sentence.Vdm;
import dk.dma.enav.model.geometry.Position;
import dk.dma.enav.util.function.Consumer;

public class AisParser implements Consumer<AisPacket> {
	private final List<IPacketFilter> filters = new CopyOnWriteArrayList<>();

	private Date startTime;
	private Date timeEnd;
	private PrintStream out;
	private AisReader reader;

	private int messageCounter = 0;
	private HashMapCounter<Integer> messageTypes = new HashMapCounter<>();
	private HashMapCounter<Integer> mmsiCounter = new HashMapCounter<>();

	private Date latestTimestamp = new Date(0);
	private Date earliestTimestamp = new Date(new Date().getTime() * 2);

	private double lowestLatitude = Double.MAX_VALUE;
	private double lowestLongitude = Double.MAX_VALUE;
	private double highestLatitude = Double.MIN_VALUE;
	private double highestLongitude = Double.MIN_VALUE;

	public AisParser(String inputPath, ArrayList<AisFilter> inputFilters, String output)
			throws InterruptedException, IOException {

		if (output != null) {
			out = new PrintStream(output);
		}

		reader = AisReaders.createReaderFromFile(inputPath);

		for (int i = 0; i < inputFilters.size(); i++) {
			if (inputFilters.get(i).getName().equals("Time Filter")) {
				timeEnd = inputFilters.get(i).getTimeEnd();
				startTime = inputFilters.get(i).getTimeStart();
			} else {
				filters.add(inputFilters.get(i).getExpressionFilter());
			}

		}

		reader.registerPacketHandler(this);

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

	@Override
	public void accept(AisPacket aisPackage) {

		for (IPacketFilter filter : filters) {
			if (filter.rejectedByFilter(aisPackage)) {
				// try {
				// System.out.println("Rejecting Filter for message id " +
				// aisPackage.getAisMessage().getMsgId());
				// } catch (AisMessageException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (SixbitException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				return;
			}
		}

		Vdm vdm = aisPackage.getVdm();
		if (vdm == null) {
			return;
		}

		// Maybe check for start date
		Date timestamp = vdm.getTimestamp();
		if (startTime != null && timestamp != null) {
			if (timestamp.before(startTime)) {
				return;
			}
		}

		// Maybe check for end date
		if (timeEnd != null && timestamp != null) {
			if (timestamp.after(timeEnd)) {
				return;
			}
		}

		if (out != null) {
			out.print(aisPackage.getStringMessage() + "\r\n");
		}

		analysePackaget(aisPackage);
	}

	/**
	 * Analyser part
	 */
	private void analysePackaget(AisPacket aisPackage) {

		messageCounter++;

		if (aisPackage.getTimestamp().before(earliestTimestamp)) {
			earliestTimestamp = aisPackage.getTimestamp();
		}

		if (aisPackage.getTimestamp().after(latestTimestamp)) {
			latestTimestamp = aisPackage.getTimestamp();
		}

		aisPackage.getTimestamp();

		AisMessage aisMessage;
		try {
			aisMessage = aisPackage.getAisMessage();

			if (aisMessage != null) {
				int mmsi = aisMessage.getUserId();
				mmsiCounter.add(mmsi);

				if (aisMessage.getValidPosition() != null) {
					System.out.println("Position is " + aisMessage.getValidPosition());
					if (aisMessage.getValidPosition().getLatitude() > highestLatitude) {
						highestLatitude = aisMessage.getValidPosition().getLatitude();
					}

					if (aisMessage.getValidPosition().getLatitude() < lowestLatitude) {
						lowestLatitude = aisMessage.getValidPosition().getLatitude();
					}

					if (aisMessage.getValidPosition().getLongitude() > highestLongitude) {
						highestLongitude = aisMessage.getValidPosition().getLongitude();
					}

					if (aisMessage.getValidPosition().getLongitude() < lowestLongitude) {
						lowestLongitude = aisMessage.getValidPosition().getLongitude();
					}
				}

				// Handle AtoN message
				if (aisMessage instanceof AisMessage21) {
					AisMessage21 msg21 = (AisMessage21) aisMessage;

				}
				// Handle position messages 1,2 and 3 (class A) by using their
				// shared
				// parent
				if (aisMessage instanceof AisPositionMessage) {
					AisPositionMessage posMessage = (AisPositionMessage) aisMessage;
				}

				// Handle static reports for both class A and B vessels (msg 5 +
				// 24)
				if (aisMessage instanceof AisStaticCommon) {
					AisStaticCommon staticMessage = (AisStaticCommon) aisMessage;
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
				messageTypes.add(aisPackage.getAisMessage().getMsgId());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		// Statistics... Må gerne også indeholde extremes for geografiske
		// koordinater samt. D
		// Samme gælder tidsmæssigt.
	}

	public String getStatisticsData() {

		Position topLeft = Position.create(highestLatitude, lowestLongitude);
		Position bottomRight = Position.create(lowestLatitude, highestLongitude);

		String statisticsData = "<html>Statistics on file:<br>";
		statisticsData = statisticsData + "Total Messages: " + messageCounter + "<br>";
		for (Entry<Integer, Integer> messageType : messageTypes.entrySet()) {
			statisticsData = statisticsData + "Message Type: " + messageType.getKey() + " counts: "
					+ messageType.getValue() + "<br>";
		}

		statisticsData = statisticsData + "Unique MMSI: " + mmsiCounter.size() + "<br>";

		statisticsData = statisticsData + "Date Range from: " + earliestTimestamp + "<br>";
		statisticsData = statisticsData + "To: " + latestTimestamp + "<br>";

		statisticsData = statisticsData + "<hr>";
		statisticsData = statisticsData + "Bounding Box: <br>" + topLeft + " <br> " + bottomRight + "<br>";

		statisticsData = statisticsData + "<html>";
		return statisticsData;
	}

}
