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
import fr.nicolas.main.components.ALabel;
import fr.nicolas.main.components.ABackground.ABgType;
import fr.nicolas.main.panel.APanelTop;

public class AFrameEnd extends JFrame {

	private JPanel base = new JPanel();
	private APanelTop panelTop;
	private ABackground bg = new ABackground(ABgType.Summary);
	private AButtonImg buttonTerminer = new AButtonImg("Terminer");
	private AButtonImg buttonMenu = new AButtonImg("Menu");
	private ALabel labelText = new ALabel("Felicitations, vous avez terminé cette fiche !", 32);
	private ALabel labelRestart;
	private ABackground imgEnd = new ABackground(ABgType.End);

	public AFrameEnd(Point loc, int nombreRestart) {
		this.setTitle("ReciteEasily - Termine");
		this.setSize(1050, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(base);
		this.setLocation(loc);
		this.setIconImage(new ImageIcon(getClass().getResource("/img/icone.png")).getImage());

		init(nombreRestart);

		this.setVisible(true);
	}

	private void init(int nombreRestart) {
		base.setLayout(new BorderLayout());

		panelTop = new APanelTop(ATools.clearText(ATools.nameFiche));

		if (nombreRestart != 0) {
			labelRestart = new ALabel("Vous avez recommencé " + nombreRestart + " fois une liste.", 24);
		} else {
			labelRestart = new ALabel("Vous n'avez fait aucune erreur.", 18);
		}

		base.add(bg);
		bg.add(panelTop);
		bg.add(buttonTerminer);
		bg.add(buttonMenu);
		bg.add(labelText);
		bg.add(labelRestart);
		bg.add(imgEnd);

		panelTop.setBounds(1, 1, 1200, 90);
		buttonTerminer.setBounds(800, 494, 208, 43);
		buttonMenu.setBounds(720, 501, 37, 30);
		labelText.setBounds(45, 110, 800, 30);
		labelRestart.setBounds(60, 140, 800, 30);
		imgEnd.setBounds(43, 470, 80, 80);

		buttonTerminer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AFrameMenu();
				dispose();
			}
		});
		
		buttonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

	}

}
