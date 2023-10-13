package fr.nicolas.main.frames;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.nicolas.main.ATools;
import fr.nicolas.main.components.ABackground;
import fr.nicolas.main.components.AButtonImg;
import fr.nicolas.main.components.AButtonMatiere;
import fr.nicolas.main.components.ALabel;
import fr.nicolas.main.components.ATextArea;
import fr.nicolas.main.components.ABackground.ABgType;
import fr.nicolas.main.panel.APanelTop;

public class AFrameMenu extends JFrame {

	private JPanel base = new JPanel();
	private APanelTop panelTop = new APanelTop();
	private ABackground bg = new ABackground();
	private AButtonImg buttonOpenFile = new AButtonImg("OpenFile");
	private ALabel labelText = new ALabel("Fiches enregistrées", 34);
	private JFileChooser fileChooser = new JFileChooser();
	private JFrame frame = this;
	private JPanel panel = new JPanel();
	private String nameFiche;
	private String nameFolder;
	private JPanel fiches = new JPanel();
	private CardLayout cardLayout = new CardLayout();
	private ArrayList<AButtonMatiere> buttonMatiereList = new ArrayList<AButtonMatiere>();

	public AFrameMenu() {
		this.setTitle("ReciteEasily - Menu");
		this.setSize(1050, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(base);
		this.setIconImage(new ImageIcon(getClass().getResource("/img/icone.png")).getImage());

		init();
		build();

		this.setVisible(true);
	}

	private void init() {
		base.setLayout(new BorderLayout());
		fiches.setLayout(cardLayout);

		JScrollPane scrollpanePanel = new JScrollPane(panel);
		scrollpanePanel.getViewport().setOpaque(false);
		scrollpanePanel.setOpaque(false);
		scrollpanePanel.setBorder(null);
		scrollpanePanel.getVerticalScrollBar().setUnitIncrement(5);

		FileNameExtensionFilter extensionFileChooser = new FileNameExtensionFilter(".html", "html");
		fileChooser.setFileFilter(extensionFileChooser);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogTitle("Charger une fiche");

		panel.add(fiches);
		base.add(bg);
		bg.add(panelTop);
		bg.add(buttonOpenFile);
		bg.add(labelText);
		bg.add(scrollpanePanel);

		fiches.setBounds(0, 40, 985, 250);
		panelTop.setBounds(1, 1, 1200, 90);
		buttonOpenFile.setBounds(40, 110, 985, 80);
		labelText.setBounds(40, 210, 400, 40);
		scrollpanePanel.setBounds(40, 260, 985, 290);

		buttonOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fileChooserValue = fileChooser.showOpenDialog(frame);
				if (fileChooserValue == JFileChooser.APPROVE_OPTION) {
					new AFrameNewFiche(fileChooser.getSelectedFile(), getLocation());
					dispose();
				}
			}
		});
	}

	private void build() {
		panel.setLayout(null);
		panel.setOpaque(false);

		int locX = 0;

		File folder = new File("ReciteEasily/Fiches");
		String[] listFolders = folder.list();
		if (listFolders.length != 0) {
			for (int i = 0; i < listFolders.length; i++) {
				nameFolder = listFolders[i];

				AButtonMatiere buttonMatiere = new AButtonMatiere(nameFolder);
				JPanel matierePanel = new JPanel();
				matierePanel.setLayout(null);
				matierePanel.setBackground(new Color(30, 30, 30));
				buttonMatiere.setBounds(locX, 0, 110, 30);
				buttonMatiereList.add(buttonMatiere);
				if (i == 0) {
					buttonMatiere.setSelected(true);
				}

				panel.add(buttonMatiere);
				fiches.add(matierePanel, nameFolder);

				buttonMatiere.addActionListener(new ActionListener() {
					private String name = nameFolder;

					public void actionPerformed(ActionEvent e) {
						setMatiereSelected(buttonMatiere);
						cardLayout.show(fiches, name);
					}
				});

				locX += 120;

				// Charger les fiches
				int locY = 0;
				File file = new File("ReciteEasily/Fiches/" + nameFolder);
				String[] listFiles = file.list();
				if (listFiles.length != 0) {
					for (int i2 = 0; i2 < listFiles.length; i2++) {
						nameFiche = listFiles[i2].replace(".txt", "");

						ATextArea textArea = new ATextArea(getTextFile(ATools.readFile(nameFolder, nameFiche)), 22);
						AButtonImg buttonImgOk = new AButtonImg("Ok");
						AButtonImg buttonImgDelete = new AButtonImg("Delete");
						ABackground bg = new ABackground(ABgType.Color);

						matierePanel.add(textArea);
						matierePanel.add(buttonImgOk);
						matierePanel.add(buttonImgDelete);
						matierePanel.add(bg);

						textArea.setBounds(10, locY + 3, 904, 30);
						buttonImgOk.setBounds(948, locY + 4, 32, 24);
						buttonImgDelete.setBounds(908, locY + 4, 32, 24);
						bg.setBounds(0, locY, 985, 32);

						buttonImgOk.addActionListener(new ActionListener() {
							private String name = nameFiche;
							private String folderTxt = nameFolder;

							public void actionPerformed(ActionEvent e) {
								new AFrameSummary(folderTxt, name, getLocation());
								dispose();
							}
						});

						buttonImgDelete.addActionListener(new ActionListener() {
							private String name = nameFiche;
							private String folderTxt = nameFolder;

							public void actionPerformed(ActionEvent e) {
								int confirm = JOptionPane.showConfirmDialog(null,
										"Voulez-vous vraiment supprimer la fiche \"" + name + "\" ?",
										"Supprimer une fiche", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

								if (confirm == JOptionPane.OK_OPTION) {
									File folder = new File("ReciteEasily/Fiches/" + folderTxt + "/" + name);
									File[] files = folder.listFiles();
									for (int i = 0; i < files.length; i++) {
										files[i].delete();
									}
									folder.delete();

									dispose();
									new AFrameMenu();
								}
							}
						});

						locY += 40;
					}
				} else {
					ALabel label = new ALabel("Aucune fiche sauvegardée pour cette matière", 20);
					ABackground bg = new ABackground(ABgType.Color);
					matierePanel.add(label);
					matierePanel.add(bg);
					label.setBounds(15, 2, 500, 30);
					bg.setBounds(0, locY, 985, 32);
				}
			}
		}
	}

	private void setMatiereSelected(AButtonMatiere buttonMatiere) {
		for (int i = 0; i < buttonMatiereList.size(); i++) {
			buttonMatiereList.get(i).setSelected(false);
		}
		buttonMatiere.setSelected(true);
	}

	private String getTextFile(ArrayList<String> elements) {
		String date = ATools.clearText(elements.get(3));
		String textFile = "[" + ATools.clearText(elements.get(4)) + " parties]";
		if (date.length() > 0) {
			textFile += " (Pour le " + ATools.clearText(elements.get(3)) + ")";
		}
		textFile += " - " + ATools.clearText(elements.get(0));
		return textFile;
	}

}
