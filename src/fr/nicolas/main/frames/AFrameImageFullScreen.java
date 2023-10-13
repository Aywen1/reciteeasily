package fr.nicolas.main.frames;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import fr.nicolas.main.panel.APanelImg;

public class AFrameImageFullScreen extends JFrame {

	private APanelImg panelImg;
	
	public AFrameImageFullScreen(String name, Image img) {
		panelImg = new APanelImg(img);

		this.setTitle("ReciteEasily - Image (" + name + ")");
		this.setSize(1050, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(panelImg);
		this.setIconImage(new ImageIcon(getClass().getResource("/img/icone.png")).getImage());

		this.setVisible(true);
	}

}