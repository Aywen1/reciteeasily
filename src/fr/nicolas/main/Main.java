package fr.nicolas.main;

import java.io.File;

import fr.nicolas.main.frames.AFrameMenu;

public class Main {

	public static String version = new String("1.0");

	public static void main(String[] args) {
		args = new String[] { "gala" };
		if (args.length > 0 && args[0].equals("gala")) {

			if (!(new File("ReciteEasily/Fiches").exists())) {
				new File("ReciteEasily/Fiches").mkdirs();
				for (int i = 0; i < ATools.matiereList.length; i++) {
					new File("ReciteEasily/Fiches/" + ATools.matiereList[i]).mkdirs();
				}
			}

			new AFrameMenu();

		} else {
			System.exit(0);
		}

	}

}
