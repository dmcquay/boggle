package com.synchrosinteractive.boggle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class BoggleGame {
	private static final int BOARD_SIZE = 4; 
	private String[][] dice;
	private String[][] board;
	private Dictionary dict;
	private List<String> words;
	
	public BoggleGame() {
		this.dice = DieFactory.getDice();
		this.dict = new Dictionary();
		this.scramble();
	}
	
	public String[][] getBoard() {
		return this.board;
	}
	
	private List<String> getWords() {
		if (this.words == null) {
			this.words = this.solve();
		}
		return this.words;
	}
	
	public void scramble() {
		assert this.dice != null : "this.dice should never be null";
		this.words = null; //solved words from last board are now invalid
		List<String[]> dice = new ArrayList<String[]>();
		for (int i = 0; i < this.dice.length; i++) {
			dice.add(this.dice[i].clone());
		}
		String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
		Random rand = new Random();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				String die[] = dice.remove(rand.nextInt(dice.size()));
				board[row][col] = die[rand.nextInt(die.length)];
			}
		}
		this.board = board;
	}
	
	public boolean isValidWord(String word) {
		if (this.board == null) return false;
		return this.getWords().contains(word.toLowerCase());
	}
	
	public List<String> solve() {
		return this.solve(this.dict);
	}
	
	public List<String> solve(Dictionary dict) {
		List<String> words = new ArrayList<String>();
		assert this.board != null : "this.board should not be null";
		int maxWordLength = this.board.length * this.board[0].length;
		int[][] chain = new int[maxWordLength][2];
		for (int row = 0; row < this.board.length; row++) {
			for (int col = 0; col < this.board[0].length; col++) {
				chain[0][0] = row;
				chain[0][1] = col;
				checkChain(chain, 1, words, dict);
			}
		}
		return words;
	}
	
	public List<int[][]> find(String str) {
		List<int[][]> path = new ArrayList<int[][]>();
		return path;
	}
	
	public void checkChain(int[][] chain, int chainLength, List<String> words) {
		this.checkChain(chain, chainLength, words, this.dict);
	}
	
	public void checkChain(int[][] chain, int chainLength, List<String> words, Dictionary dict) {
		int row = chain[chainLength - 1][0];
		int col = chain[chainLength - 1][1];
		
		// return if invalid cell
		if (row < 0 || row >= this.board.length || col < 0 || col >= this.board[0].length) {
			return;
		}
		
		// return if duplicate of any previous cell in the chain
		for (int i = 0; i < chainLength - 1; i++) {
			if (chain[i][0] == row && chain[i][1] == col) {
				return;
			}
		}
		
		// build a string from the chain and check if it is a WORD, PREFIX or NONE
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chainLength; i++) {
			sb.append(this.board[chain[i][0]][chain[i][1]]);
		}
		String chkStr = sb.toString();
		int type = dict.search(chkStr);
		
		// if this is a WORD, add to the list and return
		if ((type & Dictionary.WORD) > 0) {
			words.add(chkStr);
		}
		
		// if this is no even a subset of any word, return
		if ((type & Dictionary.NONE) > 0) {
			return;
		}
		
		// for all cells around this cell, call checkChain
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1; c <= col + 1; c++) {
				chain[chainLength][0] = r;
				chain[chainLength][1] = c;
				checkChain(chain, chainLength + 1, words);
			}
		}
	}
	
	public String toString() {
		String ret = "";
		String[][] board = this.board;
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				System.out.print(board[row][col] + " ");
			}
			System.out.println();
		}
		return ret;
	}
	
	public static void main(String[] args) {
		System.out.println("Running performance test...");
		int n = 1000;
		BoggleGame boggle = new BoggleGame();
		long start = (new Date()).getTime();
		for (int i = 1; i <= n; i++) {
			boggle.scramble();
			boggle.solve();
		}
		long end = (new Date()).getTime();
		System.out.println(n + " boards created and solved in "
				+ ((float) (end - start) / 1000) + " seconds. That's "
				+ ((float) (end - start) / n) + " millis per board.");
	}
}
