package fr.nicolas.main.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class AButtonImg extends JButton {

	private Image img;

	public AButtonImg(String name) {
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);

		changeType(name);
	}

	public AButtonImg(String path, String name) {
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);

		try {
			img = ImageIO.read(new File(path + "/" + name));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	public void changeType(String name) {
		img = new ImageIcon(getClass().getResource("/img/button" + name + ".png")).getImage();

		repaint();
	}

	public Image getImg() {
		return img;
	}

}
