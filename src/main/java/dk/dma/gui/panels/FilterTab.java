package dk.dma.gui.panels;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import dk.dma.ais.AisFilter;
import dk.dma.ais.FilterTypes;
import dk.dma.ais.filter.ExpressionFilter;
import dk.dma.gui.AisFileParser;
import dk.dma.gui.panels.filterPanels.BboxFilterPanel;
import dk.dma.gui.panels.filterPanels.CircleFilterPanel;
import dk.dma.gui.panels.filterPanels.CustomFilterPanel;
import dk.dma.gui.panels.filterPanels.MessageTypeFilterPanel;
import dk.dma.gui.panels.filterPanels.TimeFilterPanelPanel;

public class FilterTab extends JPanel implements ActionListener {
	private JComboBox<String> comboBox;

	private BboxFilterPanel bboxFilterPanel = new BboxFilterPanel();
	private CircleFilterPanel circleFilterPanel = new CircleFilterPanel();
	private CustomFilterPanel customFilterPanel = new CustomFilterPanel();
	private MessageTypeFilterPanel messageTypeFilterPanel = new MessageTypeFilterPanel();
	private TimeFilterPanelPanel timeFilterPanel = new TimeFilterPanelPanel();

	private JPanel cardLayoutFilters;
	private JList<AisFilter> list;
	private DefaultListModel<AisFilter> listModel;

	private int selectedFilter = 0;

	public FilterTab(AisFileParser aisFileParser) {
		setBorder(new TitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 200, 300, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblCurrentFilters = new JLabel("Current Filters");
		GridBagConstraints gbc_lblCurrentFilters = new GridBagConstraints();
		gbc_lblCurrentFilters.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentFilters.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentFilters.gridx = 0;
		gbc_lblCurrentFilters.gridy = 0;
		add(lblCurrentFilters, gbc_lblCurrentFilters);

		JLabel lblSelectFilterTo = new JLabel("Select Filter to Add");
		GridBagConstraints gbc_lblSelectFilterTo = new GridBagConstraints();
		gbc_lblSelectFilterTo.anchor = GridBagConstraints.WEST;
		gbc_lblSelectFilterTo.insets = new Insets(0, 0, 5, 0);
		gbc_lblSelectFilterTo.gridx = 1;
		gbc_lblSelectFilterTo.gridy = 0;
		add(lblSelectFilterTo, gbc_lblSelectFilterTo);

		// list = new JList(new Vector<AisFilter>(new ArrayList<AisFilter>()));

		listModel = new DefaultListModel<AisFilter>();

		list = new JList<AisFilter>(listModel);

		list.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (renderer instanceof JLabel && value instanceof AisFilter) {
					((JLabel) renderer).setText(((AisFilter) value).getName());
				}
				return renderer;
			}
		});

		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		add(list, gbc_list);

		JPanel createFilterPanel = new JPanel();
		GridBagConstraints gbc_createFilterPanel = new GridBagConstraints();
		gbc_createFilterPanel.insets = new Insets(0, 0, 5, 0);
		gbc_createFilterPanel.fill = GridBagConstraints.BOTH;
		gbc_createFilterPanel.gridx = 1;
		gbc_createFilterPanel.gridy = 1;
		add(createFilterPanel, gbc_createFilterPanel);
		GridBagLayout gbl_createFilterPanel = new GridBagLayout();
		gbl_createFilterPanel.columnWidths = new int[] { 0, 0 };
		gbl_createFilterPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_createFilterPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_createFilterPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		createFilterPanel.setLayout(gbl_createFilterPanel);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Time Filter", "Geographic Bbox",
				"Geographic Circle", "Message Types", "Custom Filter" }));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		createFilterPanel.add(comboBox, gbc_comboBox);
		comboBox.addActionListener(this);

		cardLayoutFilters = new JPanel();

		GridBagConstraints gbc_cardLayoutFilters = new GridBagConstraints();
		gbc_cardLayoutFilters.fill = GridBagConstraints.BOTH;
		gbc_cardLayoutFilters.gridx = 0;
		gbc_cardLayoutFilters.gridy = 1;
		createFilterPanel.add(cardLayoutFilters, gbc_cardLayoutFilters);

		cardLayoutFilters.setLayout(new CardLayout(0, 0));

		// 0 Time Filter
		// 1 Geography Area
		// 2 Geography Circle
		// 3 Message Types
		// 4 Custom
		cardLayoutFilters.add(timeFilterPanel, "0");
		cardLayoutFilters.add(bboxFilterPanel, "1");
		cardLayoutFilters.add(circleFilterPanel, "2");
		cardLayoutFilters.add(messageTypeFilterPanel, "3");
		cardLayoutFilters.add(customFilterPanel, "4");

		JButton btnRemoveSlectedFilter = new JButton("Remove Slected Filter");
		btnRemoveSlectedFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Remove Filter");
				listModel.remove(list.getSelectedIndex());
				// list.removeSelectionInterval(arg0, arg1);
			}
		});
		GridBagConstraints gbc_btnRemoveSlectedFilter = new GridBagConstraints();
		gbc_btnRemoveSlectedFilter.anchor = GridBagConstraints.WEST;
		gbc_btnRemoveSlectedFilter.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveSlectedFilter.gridx = 0;
		gbc_btnRemoveSlectedFilter.gridy = 2;
		add(btnRemoveSlectedFilter, gbc_btnRemoveSlectedFilter);

		JButton btnAddFilter = new JButton("Add Filter");
		btnAddFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Add Filter " + selectedFilter);
				// 0 Time Filter
				// 1 Geography Area
				// 2 Geography Circle
				// 3 Message Types
				// 4 Custom

				switch (selectedFilter) {
				case 0:
					try {
						listModel.addElement(timeFilterPanel.getFilter());
					} catch (Exception e2) {
						showErrorDialog("Time Filter");
					}

					break;
				case 1:
					try {
						listModel.addElement(bboxFilterPanel.getFilter());
					} catch (Exception e2) {
						showErrorDialog("BBox Filter");
					}

					break;
				case 2:
					try {
						listModel.addElement(circleFilterPanel.getFilter());
					} catch (Exception e2) {
						showErrorDialog("Circle Filter");
					}

					break;
				case 3:
					try {
						listModel.addElement(messageTypeFilterPanel.getFilter());
					} catch (Exception e2) {
						showErrorDialog("Message Type Filter");
					}
					break;
				case 4:
					try {
						listModel.addElement(customFilterPanel.getFilter());
					} catch (Exception e2) {
						showErrorDialog("Incorrect Grammar in expression");
					}
					break;
				}
			}
		});
		GridBagConstraints gbc_btnAddFilter = new GridBagConstraints();
		gbc_btnAddFilter.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddFilter.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddFilter.gridx = 1;
		gbc_btnAddFilter.gridy = 2;
		add(btnAddFilter, gbc_btnAddFilter);

		JPanel selectOutputPanel = new JPanel();
		GridBagConstraints gbc_selectOutputPanel = new GridBagConstraints();
		gbc_selectOutputPanel.anchor = GridBagConstraints.SOUTH;
		gbc_selectOutputPanel.insets = new Insets(0, 0, 0, 5);
		gbc_selectOutputPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectOutputPanel.gridx = 0;
		gbc_selectOutputPanel.gridy = 3;
		add(selectOutputPanel, gbc_selectOutputPanel);
		GridBagLayout gbl_selectOutputPanel = new GridBagLayout();
		gbl_selectOutputPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_selectOutputPanel.rowHeights = new int[] { 0, 0 };
		gbl_selectOutputPanel.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_selectOutputPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		selectOutputPanel.setLayout(gbl_selectOutputPanel);

		JButton btnApplyFilters = new JButton("Apply Filters");
		btnApplyFilters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<AisFilter> filters = new ArrayList<>();

				ArrayList<AisFilter> messageFilters = new ArrayList<>();

				for (int i = 0; i < listModel.size(); i++) {

					if (listModel.getElementAt(i).getName().contains("Message Type")) {
						messageFilters.add(listModel.getElementAt(i));
					} else {
						filters.add(listModel.getElementAt(i));
					}

				}

				if (messageFilters.size() <= 1) {
					System.out.println("Adding message filter!");
					filters.addAll(messageFilters);
				} else {

					// Combine them
					String expression = "m.id = " + messageFilters.get(0).getMessageId();
					for (int i = 1; i < messageFilters.size(); i++) {
						expression = expression + " | m.id = " + messageFilters.get(i).getMessageId();
					}

					ExpressionFilter filter = new ExpressionFilter(expression);

					AisFilter aisFilter = new AisFilter(filter, "Message Type Filter for " + expression,
							FilterTypes.MessageTypes);
					filters.add(aisFilter);
				}

				aisFileParser.applyFilters(filters);
			}
		});

		GridBagConstraints gbc_btnApplyFilters = new GridBagConstraints();
		gbc_btnApplyFilters.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnApplyFilters.gridx = 1;
		gbc_btnApplyFilters.gridy = 3;

		add(btnApplyFilters, gbc_btnApplyFilters);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	private void showErrorDialog(String text) {
		JOptionPane.showMessageDialog(this, "Error adding filter", text, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		// Index based
		// 0 Time Filter
		// 1 Geography Area
		// 2 Geography Circle
		// 3 Message Types
		// 4 Custom
		if (arg0.getSource() == comboBox) {
			System.out.println("Selected Index: " + comboBox.getSelectedIndex());
			selectedFilter = comboBox.getSelectedIndex();

			CardLayout cl = (CardLayout) (cardLayoutFilters.getLayout());
			cl.show(cardLayoutFilters, selectedFilter + "");
		}

	}
}
