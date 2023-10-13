package fr.nicolas.main.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class AButtonMatiere extends JButton {

	private ALabel label;
	private boolean isSelected = false;

	public AButtonMatiere(String name) {
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);

		label = new ALabel(name, 16);
		add(label);
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(22, 22, 22));
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(new Color(65, 120, 35));
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

		if (isSelected) {
			g.setColor(new Color(65, 120, 35));
			g.drawLine(1, 26, 109, 26);
		}
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		repaint();
	}

}
