package fr.nicolas.main.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class ALabel extends JLabel {

	public enum ALabelType {
		Bold, Italic
	}

	public ALabel(String text, int size) {
		super(text);
		setForeground(Color.WHITE);
		setFont(new Font("Calibri", Font.PLAIN, size));
	}

	public ALabel(String text, int size, ALabelType type) {
		super(text);
		setForeground(Color.WHITE);
		if (type == ALabelType.Bold) {
			setFont(new Font("Calibri", Font.BOLD, size));
		} else if (type == ALabelType.Italic) {
			setFont(new Font("Calibri", Font.ITALIC, size));
		}
	}

	public void setText(String text, int size) {
		setText(text);
		setFont(new Font("Calibri", Font.PLAIN, size));
	}

}
