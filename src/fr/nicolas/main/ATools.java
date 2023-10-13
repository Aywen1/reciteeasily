package fr.nicolas.main;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.nicolas.main.frames.AFrameCategory;
import fr.nicolas.main.frames.AFrameEnd;
import fr.nicolas.main.frames.AFrameImage;
import fr.nicolas.main.frames.AFrameListe;
import fr.nicolas.main.frames.AFrameMenuNavig;

public class ATools {

	public static String[] matiereList = { "Histoire", "Géographie", "Emc", "Phys-Chim", "Svt", "Maths",
			"Anglais", "Espagnol" };
	public static ArrayList<String> elements;
	public static int nombrePartiesTotal;
	public static int nombrePartiesNow;
	private static ArrayList<ArrayList<String>> listFalseItems = new ArrayList<ArrayList<String>>();
	private static String categoryNow;
	private static int nombreRestart;
	public static String nameFiche;
	public static AFrameMenuNavig menuNavig;

	public static String clearText(String text) {
		return (text.replace("[T]", "").replace("[D]", "").replace("[M]", "").replace("[DA]", "").replace("[P]", "")
				.replace("[E]", "").replace("[L]", "").replace("[C]", "").replace("[I]", ""));
	}

	public static ArrayList<String> readFile(String matiere, String nameFiche) {
		String line;
		ArrayList<String> newElements = new ArrayList<String>();
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader("ReciteEasily/Fiches/" + matiere + "/" + nameFiche + "/" + nameFiche + ".txt"));
			try {
				while ((line = bufferedReader.readLine()) != null) {
					newElements.add(line);
				}
				bufferedReader.close();
			} catch (IOException e) {
			}
		} catch (FileNotFoundException e) {
		}

		return newElements;
	}

	public static void nextFrame(Point loc) {
		nextFrameSuite(loc);
	}

	public static void nextFrame(Point loc, ArrayList<String> falseItems) {
		listFalseItems.add(falseItems);

		nextFrameSuite(loc);
	}

	private static void nextFrameSuite(Point loc) {
		if (elements.size() > 0) {

			int i = 0;

			nombrePartiesNow = 0;
			while (elements.get(i) != null) {
				if (elements.get(i).startsWith("[C]") || elements.get(i).startsWith("[L]")
						|| elements.get(i).startsWith("[I]")) {
					nombrePartiesNow++;
				}
				if (i == elements.size() - 1) {
					break;
				}
				i++;
			}

			if (elements.get(0).startsWith("[C]")) {
				ArrayList<String> items = new ArrayList<String>();
				String name = clearText(elements.get(0));
				elements.remove(0);

				i = 0;
				while (elements.get(i) != null) {
					if (elements.get(i).startsWith("[C]")) {
						break;
					} else if (elements.get(i).startsWith("[L]")) {
						items.add(clearText(elements.get(i)));
					}
					if (i == elements.size() - 1) {
						break;
					}
					i++;
				}

				categoryNow = name;

				new AFrameCategory(loc, name, items);
				return;
			}

			if (elements.get(0).startsWith("[L]")) {
				ArrayList<String> items = new ArrayList<String>();
				String name = clearText(elements.get(0));
				elements.remove(0);

				while (elements.size() > 0) {
					if (elements.get(0).startsWith("[E]")) {
						items.add(clearText(elements.get(0)));
						elements.remove(0);
					} else {
						break;
					}
				}
				new AFrameListe(loc, name, categoryNow, items);
				return;
			}

			if (elements.get(0).startsWith("[I]")) {
				String name = clearText(elements.get(0));
				elements.remove(0);
				new AFrameImage(loc, name);
			}

		} else {
			if (listFalseItems.size() > 0) {
				String name = listFalseItems.get(0).get(0);
				listFalseItems.get(0).remove(0);
				String category = listFalseItems.get(0).get(0);
				listFalseItems.get(0).remove(0);
				new AFrameListe(loc, "[x] " + name, category, listFalseItems.get(0));
				listFalseItems.remove(0);
				nombreRestart++;
			} else {
				new AFrameEnd(loc, nombreRestart);
			}
		}
	}

}
