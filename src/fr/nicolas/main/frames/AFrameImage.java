package fr.nicolas.main.frames;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.nicolas.main.ATools;
import fr.nicolas.main.components.ABackground;
import fr.nicolas.main.components.AButtonImg;
import fr.nicolas.main.components.ABackground.ABgType;
import fr.nicolas.main.panel.APanelTop;

public class AFrameImage extends JFrame {

	private JPanel base = new JPanel();
	private APanelTop panelTop;
	private ABackground bg = new ABackground(ABgType.Summary);
	private AButtonImg buttonSuivant = new AButtonImg("Suivant");
	private AButtonImg buttonMenu = new AButtonImg("Menu");

	public AFrameImage(Point loc, String name) {
		this.setTitle("ReciteEasily - Image (" + (ATools.nombrePartiesTotal - ATools.nombrePartiesNow + 1) + "/"
				+ ATools.nombrePartiesTotal + ")");
		this.setSize(1050, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(base);
		this.setLocation(loc);
		this.setIconImage(new ImageIcon(getClass().getResource("/img/icone.png")).getImage());

		init(name);

		this.setVisible(true);
	}

	private void init(String name) {
		AButtonImg img = new AButtonImg("ReciteEasily/Fiches/" + ATools.nameFiche, name);

		base.setLayout(new BorderLayout());
		panelTop = new APanelTop(ATools.clearText(name));

		base.add(bg);
		bg.add(panelTop);
		bg.add(buttonSuivant);
		bg.add(buttonMenu);
		bg.add(img);

		panelTop.setBounds(1, 1, 1200, 90);
		buttonSuivant.setBounds(800, 494, 208, 43);
		buttonMenu.setBounds(720, 501, 37, 30);

		int width = img.getImg().getWidth(null), height = img.getImg().getHeight(null);
		if (width > 700) {
			width = 700;
		}
		if (height > 450) {
			height = 450;
		}
		img.setBounds(35, 105, width, height);

		img.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AFrameImageFullScreen(name, img.getImg());
			}
		});

		buttonSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ATools.nextFrame(getLocation());
			}
		});

		buttonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
	}

}