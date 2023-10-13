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
import fr.nicolas.main.components.ABackground.ABgType;
import fr.nicolas.main.panel.APanelTop;

public class AFrameSummary extends JFrame {

	private JPanel base = new JPanel();
	private APanelTop panelTop;
	private ABackground bg = new ABackground(ABgType.Summary);
	private String nameFiche;
	private AButtonImg buttonValider = new AButtonImg("Valider");
	private AButtonImg buttonMenu = new AButtonImg("Menu");
	private ALabel labelTitle;
	private ALabel labelDescription;
	private ALabel labelMatiere;
	private ALabel labelDate;
	private ALabel labelParties;
	private int parties;
	private ArrayList<String> elements;

	public AFrameSummary(String matiere, String nameFiche, Point loc) {
		this.nameFiche = nameFiche;
		this.setTitle("ReciteEasily - Sommaire");
		this.setSize(1050, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(base);
		this.setLocation(loc);
		this.setIconImage(new ImageIcon(getClass().getResource("/img/icone.png")).getImage());

		elements = ATools.readFile(matiere, nameFiche);
		build(elements);
		init();

		this.setVisible(true);
	}

	private void init() {
		base.setLayout(new BorderLayout());

		panelTop = new APanelTop(nameFiche);

		base.add(bg);
		bg.add(panelTop);
		bg.add(buttonValider);
		bg.add(buttonMenu);
		bg.add(labelTitle);
		bg.add(labelDescription);
		bg.add(labelParties);
		bg.add(labelMatiere);
		bg.add(labelDate);

		panelTop.setBounds(1, 1, 1200, 90);
		buttonValider.setBounds(800, 494, 208, 43);
		buttonMenu.setBounds(720, 501, 37, 30);
		labelTitle.setBounds(45, 110, 800, 40);
		labelDescription.setBounds(40, 145, 950, 30);
		labelMatiere.setBounds(55, 180, 800, 30);
		labelDate.setBounds(55, 210, 800, 30);
		labelParties.setBounds(55, 240, 800, 30);

		buttonValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				for (int i = 0; i < 5; i++) {
					elements.remove(0);
				}
				ATools.nameFiche = nameFiche;
				ATools.elements = elements;
				ATools.nombrePartiesTotal = parties;
				ATools.nextFrame(getLocation());
			}
		});
		
		buttonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

	}

	private void build(ArrayList<String> elements) {
		parties = Integer.parseInt(ATools.clearText(elements.get(4)));
		labelTitle = new ALabel(nameFiche, 35);
		labelDescription = new ALabel(ATools.clearText(elements.get(1)), 20);
		labelMatiere = new ALabel("Matière: " + ATools.clearText(elements.get(2)), 20);
		labelDate = new ALabel("Date: " + ATools.clearText(elements.get(3)), 20);
		labelParties = new ALabel("Parties: " + ATools.clearText(elements.get(4)), 20);
	}

}
