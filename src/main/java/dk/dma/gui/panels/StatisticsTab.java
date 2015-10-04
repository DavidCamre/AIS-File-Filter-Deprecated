package dk.dma.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

import dk.dma.gui.AisFileParser;

public class StatisticsTab extends JPanel {

	private static final long serialVersionUID = 1L;
	JLabel lblNoFileSelected;

	AisFileParser aisFileParser;

	public StatisticsTab(AisFileParser aisFileParser) {
		setBorder(new TitledBorder(null, "File Statistics", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		this.aisFileParser = aisFileParser;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblNoFileSelected = new JLabel("No File Loaded");
		GridBagConstraints gbc_lblNoFileSelected = new GridBagConstraints();
		gbc_lblNoFileSelected.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNoFileSelected.anchor = GridBagConstraints.NORTH;
		gbc_lblNoFileSelected.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoFileSelected.gridx = 0;
		gbc_lblNoFileSelected.gridy = 0;
		add(lblNoFileSelected, gbc_lblNoFileSelected);

		JButton btnLoadStatistics = new JButton("Load Statistics");
		btnLoadStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aisFileParser.loadStatistics();
			}
		});
		GridBagConstraints gbc_btnLoadStatistics = new GridBagConstraints();
		gbc_btnLoadStatistics.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnLoadStatistics.insets = new Insets(0, 0, 5, 0);
		gbc_btnLoadStatistics.gridx = 1;
		gbc_btnLoadStatistics.gridy = 0;
		add(btnLoadStatistics, gbc_btnLoadStatistics);
	}

	public void setStatistics(String statistics) {
		lblNoFileSelected.setText(statistics);
		lblNoFileSelected.repaint();
		this.repaint();
	}

}
