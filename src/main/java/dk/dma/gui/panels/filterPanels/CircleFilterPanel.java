package dk.dma.gui.panels.filterPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dk.dma.ais.AisFilter;
import dk.dma.ais.FilterTypes;
import dk.dma.ais.filter.ExpressionFilter;

public class CircleFilterPanel extends JPanel implements FilterPanel {
	private JTextField centerLatitude;
	private JTextField centerLongitude;
	private JTextField radiusTextField;

	/**
	 * Create the panel.
	 */
	public CircleFilterPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblCenterDecimalLatitude = new JLabel("Center Decimal Latitude");
		GridBagConstraints gbc_lblCenterDecimalLatitude = new GridBagConstraints();
		gbc_lblCenterDecimalLatitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCenterDecimalLatitude.insets = new Insets(0, 0, 5, 0);
		gbc_lblCenterDecimalLatitude.gridx = 0;
		gbc_lblCenterDecimalLatitude.gridy = 0;
		add(lblCenterDecimalLatitude, gbc_lblCenterDecimalLatitude);

		centerLatitude = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		add(centerLatitude, gbc_textField);
		centerLatitude.setColumns(10);

		JLabel lblCenterDecimalLongitude = new JLabel("Center Decimal Longitude");
		GridBagConstraints gbc_lblCenterDecimalLongitude = new GridBagConstraints();
		gbc_lblCenterDecimalLongitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCenterDecimalLongitude.insets = new Insets(0, 0, 5, 0);
		gbc_lblCenterDecimalLongitude.gridx = 0;
		gbc_lblCenterDecimalLongitude.gridy = 2;
		add(lblCenterDecimalLongitude, gbc_lblCenterDecimalLongitude);

		centerLongitude = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 3;
		add(centerLongitude, gbc_textField_1);
		centerLongitude.setColumns(10);

		JLabel lblRadiusInKm = new JLabel("Radius in KM");
		GridBagConstraints gbc_lblRadiusInKm = new GridBagConstraints();
		gbc_lblRadiusInKm.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRadiusInKm.insets = new Insets(0, 0, 5, 0);
		gbc_lblRadiusInKm.gridx = 0;
		gbc_lblRadiusInKm.gridy = 4;
		add(lblRadiusInKm, gbc_lblRadiusInKm);

		radiusTextField = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 0;
		gbc_textField_2.gridy = 5;
		add(radiusTextField, gbc_textField_2);
		radiusTextField.setColumns(10);

	}

	@Override
	public AisFilter getFilter() throws Exception {
		double centerLatitudeDouble = Double.parseDouble(centerLatitude.getText());
		double centerLongitudeDouble = Double.parseDouble(centerLongitude.getText());
		double radius = Double.parseDouble(radiusTextField.getText());
		ExpressionFilter filter = new ExpressionFilter(
				"m.pos within circle(" + centerLatitudeDouble + "," + centerLongitudeDouble + "," + radius + ")");

		AisFilter aisFilter = new AisFilter(filter, "Circle Filter", FilterTypes.Geographic);

		return aisFilter;
	}
}
