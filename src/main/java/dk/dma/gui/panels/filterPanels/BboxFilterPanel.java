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
import dk.dma.ais.filter.LocationFilter;
import dk.dma.enav.model.geometry.Area;
import dk.dma.enav.model.geometry.BoundingBox;
import dk.dma.enav.model.geometry.CoordinateSystem;
import dk.dma.enav.model.geometry.Position;

public class BboxFilterPanel extends JPanel implements FilterPanel {
	private JTextField topLeftLatitude;
	private JTextField topLeftLongitude;
	private JTextField bottomRightLatitude;
	private JTextField bottomRightLongitude;

	/**
	 * Create the panel.
	 */
	public BboxFilterPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblTopLeftDecimal = new JLabel("Top Left Latitude");
		GridBagConstraints gbc_lblTopLeftDecimal = new GridBagConstraints();
		gbc_lblTopLeftDecimal.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTopLeftDecimal.insets = new Insets(0, 0, 5, 0);
		gbc_lblTopLeftDecimal.gridx = 0;
		gbc_lblTopLeftDecimal.gridy = 0;
		add(lblTopLeftDecimal, gbc_lblTopLeftDecimal);

		topLeftLatitude = new JTextField();
		GridBagConstraints gbc_topLeftLatitude = new GridBagConstraints();
		gbc_topLeftLatitude.insets = new Insets(0, 0, 5, 0);
		gbc_topLeftLatitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_topLeftLatitude.gridx = 0;
		gbc_topLeftLatitude.gridy = 1;
		add(topLeftLatitude, gbc_topLeftLatitude);
		topLeftLatitude.setColumns(10);

		JLabel lblTopLeftDecimal_1 = new JLabel("Top Left Longitude");
		GridBagConstraints gbc_lblTopLeftDecimal_1 = new GridBagConstraints();
		gbc_lblTopLeftDecimal_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTopLeftDecimal_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblTopLeftDecimal_1.gridx = 0;
		gbc_lblTopLeftDecimal_1.gridy = 2;
		add(lblTopLeftDecimal_1, gbc_lblTopLeftDecimal_1);

		topLeftLongitude = new JTextField();
		GridBagConstraints gbc_topLeftLongitude = new GridBagConstraints();
		gbc_topLeftLongitude.insets = new Insets(0, 0, 5, 0);
		gbc_topLeftLongitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_topLeftLongitude.gridx = 0;
		gbc_topLeftLongitude.gridy = 3;
		add(topLeftLongitude, gbc_topLeftLongitude);
		topLeftLongitude.setColumns(10);

		JLabel lblBottomRightDecimal = new JLabel("Bottom Right Latitude");
		GridBagConstraints gbc_lblBottomRightDecimal = new GridBagConstraints();
		gbc_lblBottomRightDecimal.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblBottomRightDecimal.insets = new Insets(0, 0, 5, 0);
		gbc_lblBottomRightDecimal.gridx = 0;
		gbc_lblBottomRightDecimal.gridy = 4;
		add(lblBottomRightDecimal, gbc_lblBottomRightDecimal);

		bottomRightLatitude = new JTextField();
		GridBagConstraints gbc_bottomRightLatitude = new GridBagConstraints();
		gbc_bottomRightLatitude.insets = new Insets(0, 0, 5, 0);
		gbc_bottomRightLatitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_bottomRightLatitude.gridx = 0;
		gbc_bottomRightLatitude.gridy = 5;
		add(bottomRightLatitude, gbc_bottomRightLatitude);
		bottomRightLatitude.setColumns(10);

		JLabel lblBottomRightDecimal_1 = new JLabel("Bottom Right Longitude");
		GridBagConstraints gbc_lblBottomRightDecimal_1 = new GridBagConstraints();
		gbc_lblBottomRightDecimal_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblBottomRightDecimal_1.anchor = GridBagConstraints.WEST;
		gbc_lblBottomRightDecimal_1.gridx = 0;
		gbc_lblBottomRightDecimal_1.gridy = 6;
		add(lblBottomRightDecimal_1, gbc_lblBottomRightDecimal_1);

		bottomRightLongitude = new JTextField();
		GridBagConstraints gbc_bottomRightLongitude = new GridBagConstraints();
		gbc_bottomRightLongitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_bottomRightLongitude.gridx = 0;
		gbc_bottomRightLongitude.gridy = 7;
		add(bottomRightLongitude, gbc_bottomRightLongitude);
		bottomRightLongitude.setColumns(10);
	}

	@Override
	public AisFilter getFilter() throws Exception {
		double topLeftLatitudeDouble = Double.parseDouble(topLeftLatitude.getText());
		double topLeftLongitudeDouble = Double.parseDouble(topLeftLongitude.getText());
		double bottomRightLatitudeDouble = Double.parseDouble(bottomRightLatitude.getText());
		double bottomRightLongitudeDouble = Double.parseDouble(bottomRightLongitude.getText());

		// ExpressionFilter filter = new ExpressionFilter("m.pos within
		// bbox(37.9058, -122.4310, 37.9185, -122.4149)");
		ExpressionFilter filter = new ExpressionFilter("m.pos within bbox(" + topLeftLatitudeDouble + ","
				+ topLeftLongitudeDouble + "," + bottomRightLatitudeDouble + "," + bottomRightLongitudeDouble + ")");

		AisFilter aisFilter = new AisFilter(filter, "BBox Filter", FilterTypes.Geographic);

		return aisFilter;

		// 37.9058
		// -122.4310
		// 37.9185
		// -122.4149

	}

	private Area getGeometry() {
		try {
			Position pos1 = Position.create(Double.parseDouble(topLeftLatitude.getText()),
					Double.parseDouble(topLeftLongitude.getText()));
			Position pos2 = Position.create(Double.parseDouble(bottomRightLatitude.getText()),
					Double.parseDouble(bottomRightLongitude.getText()));
			return BoundingBox.create(pos1, pos2, CoordinateSystem.GEODETIC);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
