package dk.dma.gui.panels.filterPanels;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import dk.dma.ais.AisFilter;
import dk.dma.ais.filter.ExpressionFilter;

public class CustomFilterPanel extends JPanel implements FilterPanel {

	private static final long serialVersionUID = 1L;
	JTextArea textArea;

	public CustomFilterPanel() {
		setLayout(new GridLayout(0, 1, 0, 0));

		textArea = new JTextArea();
		add(textArea);
	}

	@Override
	public AisFilter getFilter() {
		System.out.println("Text is");
		System.out.println(textArea.getText());
		ExpressionFilter filter = new ExpressionFilter(textArea.getText());

		AisFilter aisFilter = new AisFilter(filter, "Custom Expression " + textArea.getText());

		return aisFilter;

	}

}
