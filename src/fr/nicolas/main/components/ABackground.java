package fr.nicolas.main.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ABackground extends JPanel {

	private Image bg;
	private boolean color = false;

	public enum ABgType {
		NewFiche, Summary, Color, End
	}

	public ABackground() {
		this.setLayout(null);
		bg = new ImageIcon(getClass().getResource("/img/bgDefault.png")).getImage();
	}

	public ABackground(ABgType type) {
		this.setLayout(null);
		if (type == ABgType.NewFiche) {
			bg = new ImageIcon(getClass().getResource("/img/bgNewFiche.png")).getImage();
		} else if (type == ABgType.Summary) {
			bg = new ImageIcon(getClass().getResource("/img/bgSummary.png")).getImage();
		} else if (type == ABgType.Color) {
			color = true;
		} else if (type == ABgType.End) {
			bg = new ImageIcon(getClass().getResource("/img/imgEnd.png")).getImage();
		}
	}

	public void paintComponent(Graphics g) {
		if (!color) {
			g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
		} else {
			g.setColor(new Color(22, 22, 22));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.WHITE);
			g.drawLine(0, 0, 0, this.getHeight());
		}
	}

}
