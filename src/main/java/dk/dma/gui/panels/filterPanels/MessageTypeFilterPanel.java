package dk.dma.gui.panels.filterPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dk.dma.ais.AisFilter;
import dk.dma.ais.FilterTypes;
import dk.dma.ais.filter.ExpressionFilter;

public class MessageTypeFilterPanel extends JPanel implements FilterPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboBox;

	public MessageTypeFilterPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblIncludeMessagesOf = new JLabel("Only include messages of type:");
		GridBagConstraints gbc_lblIncludeMessagesOf = new GridBagConstraints();
		gbc_lblIncludeMessagesOf.insets = new Insets(0, 0, 5, 0);
		gbc_lblIncludeMessagesOf.gridx = 0;
		gbc_lblIncludeMessagesOf.gridy = 0;
		add(lblIncludeMessagesOf, gbc_lblIncludeMessagesOf);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "MSGs 1-5 (IWRAP)", "Type 1", "Type 2",
				"Type 3", "Type 4", "Type 5", "Type 6", "Type 7", "Type 8", "Type 9", "Type 10", "Type 11", "Type 12",
				"Type 13", "Type 14", "Type 15", "Type 16", "Type 17", "Type 18", "Type 19", "Type 20", "Type 21",
				"Type 22", "Type 23", "Type 24", "Type 25", "Type 26", "Type 27" }));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 1;
		add(comboBox, gbc_comboBox);
	}

	@Override
	public AisFilter getFilter() {

		int messageFilter = comboBox.getSelectedIndex();
		AisFilter aisFilter;
		if (messageFilter == 0) {
			ExpressionFilter filter = new ExpressionFilter("m.id in 1..5)");
			aisFilter = new AisFilter(filter, "MSGs 1-5 (IWRAP)", messageFilter, FilterTypes.MessageTypes);
		} else {
			ExpressionFilter filter = new ExpressionFilter("m.id = " + messageFilter + ")");

			aisFilter = new AisFilter(filter, "Message Type Filter for " + messageFilter, messageFilter,
					FilterTypes.MessageTypes);
		}

		return aisFilter;

		// m.id = 5
	}

}
