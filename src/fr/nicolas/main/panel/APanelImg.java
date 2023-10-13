package fr.nicolas.main.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class APanelImg extends JPanel {

	private Image img;

	public APanelImg(Image img) {
		this.img = img;
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(22, 22, 22));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (img.getHeight(null) > 950) {
			g.drawImage(img, 20, 20, img.getWidth(null) / 2, img.getHeight(null) / 2, null);
		} else {
			g.drawImage(img, 20, 20, null);
		}
	}

}
