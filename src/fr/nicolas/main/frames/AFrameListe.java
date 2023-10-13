package fr.nicolas.main.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.nicolas.main.ATools;
import fr.nicolas.main.components.ABackground;
import fr.nicolas.main.components.AButtonImg;
import fr.nicolas.main.components.ALabel;
import fr.nicolas.main.components.ATextArea;
import fr.nicolas.main.components.ABackground.ABgType;
import fr.nicolas.main.panel.APanelTop;

public class AFrameListe extends JFrame {

	private JPanel base = new JPanel();
	private APanelTop panelTop;
	private ABackground bg = new ABackground(ABgType.Summary);
	private AButtonImg buttonSuivant = new AButtonImg("Suivant");
	private AButtonImg buttonMenu = new AButtonImg("Menu");
	private ArrayList<String> falseItems = new ArrayList<String>();
	private ALabel labelCategory;
	private JPanel panel = new JPanel();

	public AFrameListe(Point loc, String name, String category, ArrayList<String> elements) {
		this.setTitle("ReciteEasily - Liste (" + (ATools.nombrePartiesTotal - ATools.nombrePartiesNow + 1) + "/"
				+ ATools.nombrePartiesTotal + ")");
		this.setSize(1050, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(base);
		this.setLocation(loc);
		this.setIconImage(new ImageIcon(getClass().getResource("/img/icone.png")).getImage());

		build(elements);
		init(name, category);

		this.setVisible(true);
	}

	private void init(String name, String category) {
		base.setLayout(new BorderLayout());
		panelTop = new APanelTop(ATools.clearText(name));
		falseItems.add(ATools.clearText(name));
		falseItems.add(category);

		labelCategory = new ALabel(category, 18);

		JScrollPane scrollpanePanel = new JScrollPane(panel);
		scrollpanePanel.getViewport().setOpaque(false);
		scrollpanePanel.setOpaque(false);
		scrollpanePanel.setBorder(null);
		scrollpanePanel.getVerticalScrollBar().setUnitIncrement(10);

		base.add(bg);
		bg.add(panelTop);
		bg.add(buttonSuivant);
		bg.add(buttonMenu);
		bg.add(labelCategory);
		bg.add(scrollpanePanel);

		panelTop.setBounds(1, 1, 1200, 90);
		buttonSuivant.setBounds(800, 494, 208, 43);
		buttonMenu.setBounds(720, 501, 37, 30);
		labelCategory.setBounds(28, 548, 900, 20);
		scrollpanePanel.setBounds(35, 105, 992, 368);

		buttonSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				if (falseItems.size() > 2) {
					ATools.nextFrame(getLocation(), falseItems);
				} else {
					ATools.nextFrame(getLocation());
				}
			}
		});
		
		buttonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

	}

	private void build(ArrayList<String> elements) {
		panel.setLayout(null);
		panel.setOpaque(false);
		int locY = 0;
		for (int i = 0; i < elements.size(); i++) {
			ABackground bg = new ABackground(ABgType.Color);
			ATextArea textArea = new ATextArea(elements.get(i));
			JCheckBox checkBox = new JCheckBox();
			AButtonImg buttonImg = new AButtonImg("Show");

			textArea.setVisible(false);

			checkBox.setOpaque(false);
			checkBox.setVisible(false);

			bg.setVisible(false);

			panel.add(textArea);
			panel.add(checkBox);
			panel.add(buttonImg);
			panel.add(bg);

			panel.setPreferredSize(new Dimension(975, locY + 55));

			textArea.setBounds(35, locY, 940, 46);
			checkBox.setBounds(5, locY, 32, 32);
			buttonImg.setBounds(0, locY, 967, 48);
			bg.setBounds(0, locY, 967, 48);

			buttonImg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buttonImg.setVisible(false);

					textArea.setVisible(true);
					checkBox.setVisible(true);
					bg.setVisible(true);
				}
			});

			checkBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkBox.isSelected()) {
						falseItems.add(textArea.getText());
					} else {
						falseItems.remove(textArea.getText());
					}
				}
			});

			locY += 55;

		}
	}

}
