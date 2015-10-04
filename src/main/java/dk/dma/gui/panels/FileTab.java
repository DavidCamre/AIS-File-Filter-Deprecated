package dk.dma.gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dk.dma.ais.AisStatistics;
import dk.dma.gui.AisFileParser;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;

public class FileTab extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel selectedFileLbl;

	private AisFileParser aisFileParser;
	private String lastPath = null;

	/**
	 * Create the panel.
	 * 
	 * @param aisFileParser
	 */
	public FileTab(AisFileParser aisFileParser) {
		setBorder(new TitledBorder(null, "File Selector", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		this.aisFileParser = aisFileParser;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 94, 94, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblSelectAisData = new JLabel("Select AIS Data File");
		GridBagConstraints gbc_lblSelectAisData = new GridBagConstraints();
		gbc_lblSelectAisData.anchor = GridBagConstraints.WEST;
		gbc_lblSelectAisData.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectAisData.gridx = 0;
		gbc_lblSelectAisData.gridy = 0;
		add(lblSelectAisData, gbc_lblSelectAisData);

		JButton btnSelectFile = new JButton("Select File");
		GridBagConstraints gbc_btnSelectFile = new GridBagConstraints();
		gbc_btnSelectFile.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSelectFile.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelectFile.gridx = 1;
		gbc_btnSelectFile.gridy = 0;
		add(btnSelectFile, gbc_btnSelectFile);
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser;
				if (lastPath == null) {
					chooser = new JFileChooser();

				} else {
					chooser = new JFileChooser(lastPath);
				}

				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					selectedFileLbl.setText(chooser.getSelectedFile().getAbsolutePath());
					lastPath = chooser.getSelectedFile().getName();
					aisFileParser.setFilePath(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});

		selectedFileLbl = new JLabel("No File Selected");
		GridBagConstraints gbc_selectedFileLbl = new GridBagConstraints();
		gbc_selectedFileLbl.insets = new Insets(0, 0, 0, 5);
		gbc_selectedFileLbl.anchor = GridBagConstraints.WEST;
		gbc_selectedFileLbl.gridx = 0;
		gbc_selectedFileLbl.gridy = 1;
		add(selectedFileLbl, gbc_selectedFileLbl);

	}

}
