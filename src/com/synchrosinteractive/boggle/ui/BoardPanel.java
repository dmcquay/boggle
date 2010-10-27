package com.synchrosinteractive.boggle.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import com.synchrosinteractive.boggle.*;
import javax.swing.*;

public class BoardPanel extends JPanel {
	
	private String[][] letters;
	private Font letterFont;
	private FontMetrics letterFontMetrics; 
	
	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;
	
	public BoardPanel(String[][] letters) {
		super();
		init();
		setLetters(letters);
	}
	
	private void init() {
		Dimension size = new Dimension(600, 600);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
		
		letterFont = new Font("Arial", Font.PLAIN, 48);
		letterFontMetrics = getFontMetrics(letterFont);
	}
	
	public void setLetters(String[][] letters) {
		this.letters = letters;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		int width = getSize().width;
		int height = getSize().height;
		
		//make the background white
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
		
		//draw the grid
		g2d.setColor(Color.BLACK);
		g2d.drawLine(width/2, 0, width/2, height);
		g2d.drawLine(width/4, 0, width/4, height);
		g2d.drawLine((width/4)*3, 0, (width/4)*3, height);
		g2d.drawLine(0, height/2, width, height/2);
		g2d.drawLine(0, height/4, width, height/4);
		g2d.drawLine(0, (height/4)*3, width, (height/4)*3);
		
		//draw the letters
		g2d.setFont(letterFont);
		for (int col = 0; col < letters.length; col++) {
			for (int row = 0; row < letters[0].length; row++) {
				int letterHeight = letterFontMetrics.getHeight();
				int letterWidth = letterFontMetrics.charsWidth(letters[col][row].toCharArray(), 0, letters[col][row].length());
				g2d.drawString(letters[col][row], ((width/4)*col)+(width/8)-(letterWidth/2), ((height/4)*row)+(height/8)+(letterHeight/4));
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BoggleGame boggle = new BoggleGame();
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new BoardPanel(boggle.getBoard());
		f.add(p);
		f.pack();
		f.setVisible(true);
	}

}
