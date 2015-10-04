package dk.dma.gui.panels.filterPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import dk.dma.ais.AisFilter;
import dk.dma.ais.filter.ExpressionFilter;

public class TimeFilterPanelPanel extends JPanel implements FilterPanel {

	private static final long serialVersionUID = 1L;
	private JSpinner timeStartSpinner;
	private JSpinner dateStartSpinner;
	private JSpinner timeEndSpinner;
	private JSpinner dateEndSpinner;

	/**
	 * Create the panel.
	 */
	public TimeFilterPanelPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 80, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblSelectStartTime = new JLabel("Time Start");
		GridBagConstraints gbc_lblSelectStartTime = new GridBagConstraints();
		gbc_lblSelectStartTime.anchor = GridBagConstraints.WEST;
		gbc_lblSelectStartTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectStartTime.gridx = 0;
		gbc_lblSelectStartTime.gridy = 0;
		add(lblSelectStartTime, gbc_lblSelectStartTime);

		timeStartSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeStartSpinner, "HH:mm:ss ");
		timeStartSpinner.setEditor(timeEditor);
		timeStartSpinner.setValue(new Date());

		GridBagConstraints gbc_timeSpinner = new GridBagConstraints();
		gbc_timeSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_timeSpinner.anchor = GridBagConstraints.NORTH;
		gbc_timeSpinner.gridx = 1;
		gbc_timeSpinner.gridy = 0;
		add(timeStartSpinner, gbc_timeSpinner);

		JLabel lblSelectEndTime = new JLabel("Date Start");
		GridBagConstraints gbc_lblSelectEndTime = new GridBagConstraints();
		gbc_lblSelectEndTime.anchor = GridBagConstraints.WEST;
		gbc_lblSelectEndTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectEndTime.gridx = 0;
		gbc_lblSelectEndTime.gridy = 1;
		add(lblSelectEndTime, gbc_lblSelectEndTime);

		dateStartSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor dateStartEditor = new JSpinner.DateEditor(dateStartSpinner, "dd-MM-yyyy");
		dateStartSpinner.setEditor(dateStartEditor);
		dateStartSpinner.setValue(new Date());

		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.NORTH;
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 1;
		add(dateStartSpinner, gbc_spinner);

		JLabel lblTimeEnd = new JLabel("Time End");
		GridBagConstraints gbc_lblTimeEnd = new GridBagConstraints();
		gbc_lblTimeEnd.anchor = GridBagConstraints.WEST;
		gbc_lblTimeEnd.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeEnd.gridx = 0;
		gbc_lblTimeEnd.gridy = 2;
		add(lblTimeEnd, gbc_lblTimeEnd);

		timeEndSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEndEditor = new JSpinner.DateEditor(timeEndSpinner, "HH:mm:ss");
		timeEndSpinner.setEditor(timeEndEditor);
		timeEndSpinner.setValue(new Date());

		GridBagConstraints gbc_spinnerTimeEnd = new GridBagConstraints();
		gbc_spinnerTimeEnd.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerTimeEnd.anchor = GridBagConstraints.NORTH;
		gbc_spinnerTimeEnd.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerTimeEnd.gridx = 1;
		gbc_spinnerTimeEnd.gridy = 2;
		add(timeEndSpinner, gbc_spinnerTimeEnd);

		JLabel lblDateEnd = new JLabel("Date End");
		GridBagConstraints gbc_lblDateEnd = new GridBagConstraints();
		gbc_lblDateEnd.anchor = GridBagConstraints.WEST;
		gbc_lblDateEnd.insets = new Insets(0, 0, 0, 5);
		gbc_lblDateEnd.gridx = 0;
		gbc_lblDateEnd.gridy = 3;
		add(lblDateEnd, gbc_lblDateEnd);

		dateEndSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor dateEndEditor = new JSpinner.DateEditor(dateEndSpinner, "dd-MM-yyyy");
		dateEndSpinner.setEditor(dateEndEditor);
		dateEndSpinner.setValue(new Date());

		GridBagConstraints gbc_spinnerDateEnd = new GridBagConstraints();
		gbc_spinnerDateEnd.anchor = GridBagConstraints.NORTH;
		gbc_spinnerDateEnd.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerDateEnd.gridx = 1;
		gbc_spinnerDateEnd.gridy = 3;
		add(dateEndSpinner, gbc_spinnerDateEnd);
	}

	@SuppressWarnings("deprecation")
	@Override
	public AisFilter getFilter() throws Exception {
		Date timeStart = (Date) timeStartSpinner.getValue();
		Date dateStart = (Date) dateStartSpinner.getValue();
		Date timeEnd = (Date) timeEndSpinner.getValue();
		Date dateEnd = (Date) dateEndSpinner.getValue();

		dateStart.setHours(timeStart.getHours());
		dateStart.setMinutes(timeStart.getMinutes());
		dateStart.setSeconds(timeStart.getSeconds());

		dateEnd.setHours(timeEnd.getHours());
		dateEnd.setMinutes(timeEnd.getMinutes());
		dateEnd.setSeconds(timeEnd.getSeconds());

		AisFilter aisFilter = new AisFilter(dateStart, dateEnd, "Time Filter");
		return aisFilter;
	}

}
