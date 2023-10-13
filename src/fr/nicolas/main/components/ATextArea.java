package fr.nicolas.main.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

public class ATextArea extends JTextArea {

	public ATextArea(String text) {
		super(text);
		setFont(new Font("Calibri", Font.PLAIN, 18));
		initSuite();
	}

	public ATextArea(String text, int size) {
		super(text);
		setFont(new Font("Calibri", Font.PLAIN, size));
		initSuite();
	}

	private void initSuite() {
		setLineWrap(true);
		setWrapStyleWord(true);
		setEditable(false);
		setOpaque(false);

		setForeground(Color.WHITE);
	}

}
