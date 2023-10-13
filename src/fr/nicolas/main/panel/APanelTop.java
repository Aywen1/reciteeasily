package fr.nicolas.main.panel;

import java.awt.Graphics;

import javax.swing.JPanel;

import fr.nicolas.main.Main;
import fr.nicolas.main.components.ALabel;

public class APanelTop extends JPanel {

	private ALabel labelTitle = new ALabel("ReciteEasily", 50);
	private ALabel labelVersion = new ALabel("v" + Main.version, 20);

	public APanelTop() {
		init();
	}

	public APanelTop(String text) {
		labelTitle.setText(text, 35);
		labelVersion.setText("");

		init();
	}

	private void init() {
		this.setLayout(null);

		add(labelTitle);
		add(labelVersion);

		labelTitle.setBounds(35, 20, 1000, 40);
		labelVersion.setBounds(280, 40, 1000, 40);
	}

	public void paintComponent(Graphics g) {

	}
}
