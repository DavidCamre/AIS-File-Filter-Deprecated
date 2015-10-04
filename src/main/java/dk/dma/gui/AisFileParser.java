package dk.dma.gui;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import dk.dma.ais.AisFilter;
import dk.dma.ais.AisParser;
import dk.dma.gui.panels.AnalyseTab;
import dk.dma.gui.panels.FileTab;
import dk.dma.gui.panels.FilterTab;

public class AisFileParser {

	private JFrame frmAisFileFilter;

	private JTabbedPane tabbedPane;
	private FileTab fileTab;
	private AnalyseTab statisticsTab;
	private FilterTab filterTab;

	private String filePath;
	private String outputPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					AisFileParser window = new AisFileParser();
					window.frmAisFileFilter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadStatistics() {
		if (filePath != null) {
			try {

				AisParser aisParser = new AisParser(filePath, new ArrayList<>(), null);
				boolean statisticsAvailable = aisParser.start();
				if (statisticsAvailable) {
					statisticsTab.setStatistics(aisParser.getStatisticsData());
					frmAisFileFilter.repaint();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Failed to load file");
			}
		}
	}

	public void applyFilters(ArrayList<AisFilter> inputFilters) {
		System.out.println("Apply Filter");
		if (filePath != null && outputPath != null) {
			System.out.println("Not null");
			try {
				AisParser aisParser = new AisParser(filePath, inputFilters, outputPath);
				boolean statisticsAvailable = aisParser.start();
				if (statisticsAvailable) {
					statisticsTab.setStatistics(aisParser.getStatisticsData());
					frmAisFileFilter.repaint();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else {
			System.out.println("Something is null");
		}
	}

	/**
	 * Create the application.
	 */
	public AisFileParser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAisFileFilter = new JFrame();
		frmAisFileFilter.setTitle("AIS File Filter");
		frmAisFileFilter.setBounds(100, 100, 500, 450);
		frmAisFileFilter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmAisFileFilter.getContentPane().add(tabbedPane);

		fileTab = new FileTab(this);
		tabbedPane.addTab("File Select", null, fileTab, null);

		statisticsTab = new AnalyseTab(this);
		tabbedPane.addTab("Analyse File", null, statisticsTab, null);

		filterTab = new FilterTab(this);
		tabbedPane.addTab("Create Filters", null, filterTab, null);

	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setOutputPath(String filePath) {
		this.outputPath = filePath;
	}

}
