package com.synchrosinteractive.boggle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dictionary {
	public static final byte WORD = 1 << 0;
	public static final byte PREFIX = 1 << 1;
	public static final byte NONE = 1 << 2;
	
	private List<String> words;
	
	public Dictionary() {
		List<String> words = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("assets/DICT.TXT"));
			String word = "";
			while((word = br.readLine()) != null) {
				words.add(word);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.words = words;
	}
	
	public Dictionary(List<String> words) {
		this.words = words;
	}
	
	public byte search(String str) {
		List<String> words = this.words;
		int min = 0;
		int max = words.size() - 1;
		int mid = 0;
		int cmpRes = 0;
		while (true) {
			mid = (min + max) / 2;
			cmpRes = str.compareTo(words.get(mid));
			if (cmpRes == 0) {
				byte result = WORD;
				if (words.size() - 1 > mid && words.get(mid + 1).startsWith(str)) {
					result |= PREFIX;
				}
				return result;
			} else if (min == max) {
				if (words.size() - 1 > mid && words.get(mid + 1).startsWith(str)) {
					return PREFIX;
				} else {
					return NONE;
				}
			} else if (cmpRes > 0) {
				min = mid + 1 > max ? mid : mid + 1;
			} else {
				max = mid - 1 < min ? mid : mid - 1;
			}
		}
	}
	
	public static String resultToString(byte result) {
		StringBuilder sb = new StringBuilder();
		if ((result & WORD) > 0) sb.append("WORD,");
		if ((result & PREFIX) > 0) sb.append("PREFIX,");
		if ((result & NONE) > 0) sb.append("NONE,");
		if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Dictionary dict = new Dictionary();
		String str = null;
		
		str = "poteena";
		assert (dict.search(str) & WORD) == 0;
		assert (dict.search(str) & PREFIX) == 0;
		assert (dict.search(str) & NONE) > 0;
		
		str = "u";
		assert (dict.search(str) & PREFIX) > 0;
		assert (dict.search(str) & WORD) == 0;
		assert (dict.search(str) & NONE) == 0;
		
		str = "ab";
		assert (dict.search(str) & PREFIX) > 0;
		assert (dict.search(str) & WORD) > 0;
		assert (dict.search(str) & NONE) == 0;
		
		str = "abc";
		assert (dict.search(str) & PREFIX) == 0;
		assert (dict.search(str) & WORD) == 0;
		assert (dict.search(str) & NONE) > 0;
		
		System.out.println("Assertion tests passed.");
		
		System.out.println("Performance test...");
		long start = (new Date()).getTime();
		for (int i = 1; i <= 1000000; i++) {
			dict.search("ab");
			dict.search("abc");
			dict.search("abcd");
			dict.search("zy");
			dict.search("zoroastrianism");
		}
		long end = (new Date()).getTime();
		System.out.println("5,000,000 searches completed in "
				+ ((float) (end - start) / 1000) + " seconds. That's "
				+ ((float) (end - start) / 5000000) + " millis per search.");
	}
}
