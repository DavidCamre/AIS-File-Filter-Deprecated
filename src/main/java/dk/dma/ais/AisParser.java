package dk.dma.ais;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import dk.dma.ais.binary.SixbitException;
import dk.dma.ais.filter.ExpressionFilter;
import dk.dma.ais.filter.IPacketFilter;
import dk.dma.ais.message.AisBinaryMessage;
import dk.dma.ais.message.AisMessageException;
import dk.dma.ais.message.binary.AisApplicationMessage;
import dk.dma.ais.packet.AisPacket;
import dk.dma.ais.reader.AisReader;
import dk.dma.ais.reader.AisReaders;
import dk.dma.ais.sentence.Vdm;
import dk.dma.enav.util.function.Consumer;

public class AisParser implements Consumer<AisPacket> {
	private final List<IPacketFilter> filters = new CopyOnWriteArrayList<>();

	private Date startTime;
	private Date timeEnd;
	private PrintStream out;

	public AisParser(String inputPath, ArrayList<AisFilter> inputFilters, String output)
			throws InterruptedException, IOException {

		out = new PrintStream(output);

		AisReader reader = AisReaders.createReaderFromFile(inputPath);

		for (int i = 0; i < inputFilters.size(); i++) {
			if (inputFilters.get(i).getName().equals("Time Filter")) {
				timeEnd = inputFilters.get(i).getTimeEnd();
				startTime = inputFilters.get(i).getTimeStart();
			} else {
				filters.add(inputFilters.get(i).getExpressionFilter());
			}

		}

		reader.registerPacketHandler(this);

		reader.start();
		reader.join();

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
		out.print(aisPackage.getStringMessage() + "\r\n");

	}

}
