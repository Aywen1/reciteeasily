package fr.nicolas.main.frames;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.nicolas.main.ATools;
import fr.nicolas.main.components.ABackground;
import fr.nicolas.main.components.AButtonImg;
import fr.nicolas.main.components.ALabel;
import fr.nicolas.main.components.ABackground.ABgType;
import fr.nicolas.main.components.ALabel.ALabelType;
import fr.nicolas.main.panel.APanelTop;

public class AFrameNewFiche extends JFrame {

	private JPanel base = new JPanel();
	private APanelTop panelTop = new APanelTop("Sauvegarder la fiche");
	private ABackground bg = new ABackground(ABgType.NewFiche);
	private AButtonImg buttonValider = new AButtonImg("Valider");
	private ALabel labelTitle;
	private ALabel labelDescription;
	private ALabel labelParties;
	private ALabel labelMatiere = new ALabel("Matiere", 25, ALabelType.Bold);
	private JComboBox<String> comboboxMatiere = new JComboBox<String>(ATools.matiereList);
	private ALabel labelDate = new ALabel("Date", 25, ALabelType.Bold);
	private JTextField textfieldDate = new JTextField();
	private BufferedReader bufferedReader;
	private String line;
	private ArrayList<String> elements = new ArrayList<String>();
	private ArrayList<String> images = new ArrayList<String>();
	private String filePath;

	public AFrameNewFiche(File file, Point loc) {
		this.setTitle("ReciteEasily - Nouvelle fiche");
		this.setSize(1050, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(base);
		this.setLocation(loc);
		this.setIconImage(new ImageIcon(getClass().getResource("/img/icone.png")).getImage());

		filePath = file.getAbsolutePath();

		try {
			bufferedReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
		}

		analyze();
		init();

		this.setVisible(true);
	}

	private void init() {
		base.setLayout(new BorderLayout());

		base.add(bg);
		bg.add(panelTop);
		bg.add(buttonValider);
		bg.add(labelTitle);
		bg.add(labelDescription);
		bg.add(labelParties);
		bg.add(labelMatiere);
		bg.add(comboboxMatiere);
		bg.add(labelDate);
		bg.add(textfieldDate);

		panelTop.setBounds(1, 1, 1200, 90);
		labelTitle.setBounds(45, 110, 800, 40);
		labelDescription.setBounds(40, 145, 950, 30);
		labelParties.setBounds(40, 168, 100, 30);
		buttonValider.setBounds(800, 494, 208, 43);
		labelMatiere.setBounds(858, 320, 100, 30);
		comboboxMatiere.setBounds(804, 355, 200, 30);
		labelDate.setBounds(878, 400, 100, 30);
		textfieldDate.setBounds(804, 435, 200, 30);

		buttonValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filePath = filePath.replace(labelTitle.getText() + ".html", "");

				String newFilePath = "ReciteEasily/Fiches/" + comboboxMatiere.getSelectedItem().toString() + "/"
						+ labelTitle.getText() + "/";
				File folder = new File(newFilePath);
				if (folder.exists()) {
					int confirm = JOptionPane.showConfirmDialog(null,
							"Une fiche existe déjà à ce nom, voulez-vous la remplacer ?", "Fiche déjà existante",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (confirm == JOptionPane.OK_OPTION) {
						File[] files = folder.listFiles();
						for (int i = 0; i < files.length; i++) {
							files[i].delete();
						}
						folder.delete();
					} else {
						new AFrameMenu();
						dispose();
						return;
					}
				}
				folder.mkdir();

				try {
					BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(
							newFilePath + labelTitle.getText() + ".txt")));

					bufferedWriter.write("[T]" + labelTitle.getText() + "\n");
					bufferedWriter.write("[D]" + labelDescription.getText() + "\n");
					bufferedWriter.write("[M]" + comboboxMatiere.getSelectedItem().toString() + "\n");
					bufferedWriter.write("[DA]" + textfieldDate.getText() + "\n");
					bufferedWriter.write("[P]" + labelParties.getText().replace("Parties: ", "") + "\n");

					for (int i = 0; i < elements.size(); i++) {
						bufferedWriter.write(elements.get(i) + "\n");
					}

					bufferedWriter.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				for (int i = 0; i < images.size(); i++) {
					try {
						Files.copy(Paths.get(filePath + images.get(i)),
								Paths.get(newFilePath + images.get(i)));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				int confirm = JOptionPane.showConfirmDialog(null,
						"Voulez-vous supprimer les fichiers utilisés pour la création de la fiche ?",
						"Supprimer les fichiers", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (confirm == JOptionPane.OK_OPTION) {
					new File(filePath + labelTitle.getText() + ".html").delete();
					for (int i = 0; i < images.size(); i++) {
						new File(filePath + images.get(i)).delete();
					}
				}

				dispose();
				new AFrameSummary(comboboxMatiere.getSelectedItem().toString(),labelTitle.getText(), getLocation());
			}
		});
	}

	private void analyze() {
		String title = "";
		String description = "";

		String result = "";

		try {
			while ((line = bufferedReader.readLine()) != null) {
				// IDENTIFIER LE TITRE + DESCRIPTION
				if (title == "") {
					title = search("<FONT SIZE=6><B>", "</B>");
				}
				if (description == "") {
					description = search("<FONT SIZE=3>", "</FONT>");
				}

				// IDENTIFIER LES ELEMENTS
				if ((result = search("<FONT SIZE=4>", "</FONT>")) != "") {
					elements.add("[E]" + clearText(result));
				}

				// IDENTIFIER LES LISTES
				if ((result = search("<FONT COLOR=\"#ffffff\"><FONT FACE=\"Calibri\"><FONT SIZE=5><B>",
						"</B>")) != "") {
					elements.add("[L]" + clearText(result));
				}

				// IDENTIFIER LES CATEGORIES
				if ((result = search("<FONT COLOR=\"#eeeeee\">", "</B>")) != "") {
					elements.add("[C]" + clearText(result));
				}

				// IDENTIFIER LES IMAGES
				if ((result = search("<P><IMG SRC=\"", "\" NAME=")) != "") {
					result = result.replace("%20", " ").replace("%C3%A9", "é").replace("%C3%A7", "ç")
							.replace("%C3%A8", "è").replace("%C3%A0", "à").replace("%C3%B9", "ç")
							.replace("<IMG SRC=\"", "");

					for (int i = 0; i < result.length(); i++) {
						if (result.charAt(i) == '"') {
							result = result.substring(0, i);
							break;
						}
					}

					result = clearText(result);

					images.add(result);
					elements.add("[I]" + result);
				}

			}

			int nombreParties = 0;
			int i = 0;
			while (elements.get(i) != null) {
				if (elements.get(i).startsWith("[C]") || elements.get(i).startsWith("[L]")
						|| elements.get(i).startsWith("[I]")) {
					nombreParties++;
				}
				if (i == elements.size() - 1) {
					break;
				}
				i++;
			}

			labelTitle = new ALabel(title, 35);
			if (description.equals("")) {
				labelDescription = new ALabel("...", 20);
			} else {
				labelDescription = new ALabel(description, 20);
			}
			labelParties = new ALabel("Parties: " + nombreParties, 22);

			bufferedReader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private String search(String baliseDebut, String baliseFin) {
		String result = "";
		if (line.contains(baliseDebut)) {
			result += line;
			if (!line.contains(baliseFin)) {
				do {
					try {
						result += " " + (line = bufferedReader.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}
				} while (!line.contains(baliseFin));
			}
			return (clearText(result));
		}

		return ("");
	}

	private String clearText(String text) {
		String textCleared = text;

		for (int i = 0; i < textCleared.length() - 1; i++) {
			if (textCleared.charAt(i) == ' ') {
				if (textCleared.charAt(i + 1) == ' ') {
					String line1, line2;

					line1 = textCleared.substring(0, i);
					line2 = textCleared.substring(i + 1, textCleared.length());

					textCleared = line1 + line2;

					i--;
				}
			}
		}

		return (textCleared.replace("<P>", "").replace("<FONT COLOR=\"#ffffff\">", "")
				.replace("<FONT COLOR=\"#eeeeee\">", "").replace("<FONT FACE=\"Calibri\">", "")
				.replace("<FONT SIZE=6>", "").replace("<FONT SIZE=5>", "").replace("<FONT SIZE=4>", "")
				.replace("<FONT SIZE=3>", "").replace("<B>", "").replace("</B>", "").replace("</FONT>", "")
				.replace("</P>", "").replace("&eacute;", "é").replace("&egrave;", "è").replace("<LI>", "")
				.replace("	", "").replace("<U>", "").replace("</U>", "").replace("&ndash;", "-")
				.replace("&agrave;", "à").replace("&nbsp;", " ").replace("<I>", "").replace("</I>", "")
				.replace("&gt;", ">").replace("&rarr;", "->").replace("&acirc;", "â").replace("&ecirc;", "ê")
				.replace("&icirc;", "î").replace("&ocirc;", "ô").replace("&hellip;", "...").replace("&laquo;", "\"")
				.replace("&raquo;", "\"").replace("<FONT FACE=\"Calibri Light\">", "").replace("&iuml;", "ï")
				.replace("&euml;", "ë").replace("&sup2;", "²").replace("&ccedil;", "ç").replace("  ", " ")
				.replace("&ucirc;", "û").replace("&rsquo;", "'").replace("&Eacute;", "É").replace("&oelig;", "œ"));
	}

}
