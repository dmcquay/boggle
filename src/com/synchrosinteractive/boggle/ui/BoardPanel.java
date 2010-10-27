package com.synchrosinteractive.boggle.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import com.synchrosinteractive.boggle.*;
import javax.swing.*;

public class BoardPanel extends JPanel {

	private String[][] letters;
	private Color letterColor;
	private Color highlightedLetterColor;
	private Font letterFont;
	private Font highlightedLetterFont;
	private FontMetrics letterFontMetrics;
	private FontMetrics highlightedLetterFontMetrics;
	private int[][] highlightedCells;

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

		letterColor = Color.GRAY;
		letterFont = new Font("Arial", Font.PLAIN, 48);
		letterFontMetrics = getFontMetrics(letterFont);
		highlightedLetterColor = Color.BLACK;
		highlightedLetterFont = new Font("Arial", Font.BOLD, 48);
		highlightedLetterFontMetrics = getFontMetrics(highlightedLetterFont);
	}

	public void setHighlightedCells(int[][] cells) {
		this.highlightedCells = cells;
	}

	public int[][] getHighlightedCells() {
		if (this.highlightedCells == null) {
			this.highlightedCells = new int[][] {};
		}
		return this.highlightedCells;
	}

	public void setLetters(String[][] letters) {
		this.letters = letters;
	}

	private boolean isHighlightedCell(int col, int row) {
		for (int[] cell : getHighlightedCells()) {
			if (cell[0] == col && cell[1] == row) {
				return true;
			}
		}
		return false;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int width = getSize().width;
		int height = getSize().height;

		// make the background white
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);

		// draw the grid
		g2d.setColor(Color.BLACK);
		g2d.drawLine(width / 2, 0, width / 2, height);
		g2d.drawLine(width / 4, 0, width / 4, height);
		g2d.drawLine((width / 4) * 3, 0, (width / 4) * 3, height);
		g2d.drawLine(0, height / 2, width, height / 2);
		g2d.drawLine(0, height / 4, width, height / 4);
		g2d.drawLine(0, (height / 4) * 3, width, (height / 4) * 3);

		// draw the letters
		for (int col = 0; col < letters.length; col++) {
			for (int row = 0; row < letters[0].length; row++) {
				Color color = null;
				Font font = null;
				FontMetrics fontMetrics = null;
				if (isHighlightedCell(col, row)) {
					color = highlightedLetterColor;
					font = highlightedLetterFont;
					fontMetrics = highlightedLetterFontMetrics;
				} else {
					color = letterColor;
					font = letterFont;
					fontMetrics = letterFontMetrics;
				}
				int letterHeight = fontMetrics.getHeight();
				int letterWidth = fontMetrics.charsWidth(letters[col][row]
						.toCharArray(), 0, letters[col][row].length());
				g2d.setFont(font);
				g2d.setColor(color);
				g2d.drawString(letters[col][row], ((width / 4) * col)
						+ (width / 8) - (letterWidth / 2), ((height / 4) * row)
						+ (height / 8) + (letterHeight / 4));
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final BoggleGame boggle = new BoggleGame();
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final BoardPanel bp = new BoardPanel(boggle.getBoard());
		//bp.setHighlightedCells(new int[][] {{0,0}, {0,1}, {0,2}, {1,2}});
		
		final JTextField inpt = new JTextField();
		Dimension inptSize = new Dimension(400, 25);
		inpt.setPreferredSize(inptSize);
		inpt.setSize(inptSize);
		inpt.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent evt) {}
			@Override
			public void keyReleased(KeyEvent evt) {
				List<int[][]> paths = boggle.findPaths(inpt.getText());
				int[][] cells = BoggleGame.pathListToCellArray(paths);
				System.out.println("# paths: " + (paths == null ? 0 : paths.size()));
				bp.setHighlightedCells(cells);
				bp.paintComponent(bp.getGraphics());
			}
			@Override
			public void keyTyped(KeyEvent evt) {}
		});
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(bp);
		p.add(inpt);
		f.add(p);
		
		f.pack();
		f.setVisible(true);
	}

}
