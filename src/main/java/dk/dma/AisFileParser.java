package dk.dma;

import java.awt.EventQueue;

import dk.dma.gui.MainGui;

public class AisFileParser {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainGui();
			}
		});
	}

}
