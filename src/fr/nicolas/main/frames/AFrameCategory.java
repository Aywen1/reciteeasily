package fr.nicolas.main.frames;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.nicolas.main.ATools;
import fr.nicolas.main.components.ABackground;
import fr.nicolas.main.components.AButtonImg;
import fr.nicolas.main.components.ALabel;
import fr.nicolas.main.components.ATextArea;
import fr.nicolas.main.components.ABackground.ABgType;
import fr.nicolas.main.panel.APanelTop;

public class AFrameCategory extends JFrame {

	private JPanel base = new JPanel();
	private APanelTop panelTop;
	private ABackground bg = new ABackground(ABgType.Summary);
	private AButtonImg buttonSuivant = new AButtonImg("Suivant");
	private AButtonImg buttonMenu = new AButtonImg("Menu");
	private ALabel labelCategory;

	public AFrameCategory(Point loc, String name, ArrayList<String> items) {
		this.setTitle("ReciteEasily - Catégorie (" + (ATools.nombrePartiesTotal - ATools.nombrePartiesNow + 1) + "/"
				+ ATools.nombrePartiesTotal + ")");
		this.setSize(1050, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(base);
		this.setLocation(loc);
		this.setIconImage(new ImageIcon(getClass().getResource("/img/icone.png")).getImage());

		build(items);
		init(name);

		this.setVisible(true);
	}

	private void init(String name) {
		base.setLayout(new BorderLayout());

		panelTop = new APanelTop(ATools.clearText(name));
		labelCategory = new ALabel(ATools.nameFiche.toUpperCase(), 18);

		base.add(bg);
		bg.add(panelTop);
		bg.add(buttonSuivant);
		bg.add(buttonMenu);
		bg.add(labelCategory);

		panelTop.setBounds(1, 1, 1200, 90);
		buttonSuivant.setBounds(800, 494, 208, 43);
		buttonMenu.setBounds(720, 501, 37, 30);
		labelCategory.setBounds(28, 548, 900, 20);

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

	private void build(ArrayList<String> elements) {
		int locY = 105;
		for (int i = 0; i < elements.size(); i++) {
			ABackground bg = new ABackground(ABgType.Color);
			ATextArea textArea = new ATextArea(elements.get(i), 24);

			this.add(textArea);
			this.add(bg);

			textArea.setBounds(45, locY, 1005, 35);
			bg.setBounds(35, locY, 995, 33);

			locY += 45;
		}
	}

}
