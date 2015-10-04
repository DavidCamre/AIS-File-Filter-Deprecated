package dk.dma.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import dk.dma.gui.AisFileParser;

public class FileTab extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel selectedFileLbl;

	private AisFileParser aisFileParser;
	private JLabel lblNoOutputFile;
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
		gridBagLayout.rowHeights = new int[] { 23, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		selectedFileLbl = new JLabel("No File Selected");
		GridBagConstraints gbc_selectedFileLbl = new GridBagConstraints();
		gbc_selectedFileLbl.insets = new Insets(0, 0, 5, 5);
		gbc_selectedFileLbl.anchor = GridBagConstraints.WEST;
		gbc_selectedFileLbl.gridx = 0;
		gbc_selectedFileLbl.gridy = 0;
		add(selectedFileLbl, gbc_selectedFileLbl);

		JButton btnSelectFile = new JButton("Select File");
		GridBagConstraints gbc_btnSelectFile = new GridBagConstraints();
		gbc_btnSelectFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSelectFile.anchor = GridBagConstraints.NORTH;
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

		lblNoOutputFile = new JLabel("No Output File Selected");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 2;
		add(lblNoOutputFile, gbc_label);

		JButton button = new JButton("Select Output");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser;
				if (lastPath == null) {
					chooser = new JFileChooser();

				} else {
					chooser = new JFileChooser(lastPath);
				}

				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					lblNoOutputFile.setText(chooser.getSelectedFile().getAbsolutePath());
					aisFileParser.setOutputPath(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.gridx = 1;
		gbc_button.gridy = 2;
		add(button, gbc_button);

	}

}
