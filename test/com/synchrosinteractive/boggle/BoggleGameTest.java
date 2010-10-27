package com.synchrosinteractive.boggle;


import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoggleGameTest {
	
	private BoggleGame boggle;

	@Before
	public void setUp() throws Exception {
		boggle = new BoggleGame();
		boggle.setBoard(new String[][] {
				{ "a", "o", "i", "a" },
				{ "d", "o", "t", "k" },
				{ "h", "i", "n", "s" },
				{ "s", "o", "d", "w" } });
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void pathListToCellArray() {
		List<int[][]> paths = new ArrayList<int[][]>();
		paths.add(new int[][] {{0,0}, {0,1}});
		paths.add(new int[][] {{0,0}, {1,0}});
		int[][] cells = BoggleGame.pathListToCellArray(paths);
		assertEquals(3, cells.length);
	}

	@Test
	public void getPaths() {
		assertEquals(2, boggle.findPaths("a").size());
		assertEquals(3, boggle.findPaths("o").size());
		assertEquals(1, boggle.findPaths("tk").size());
	}
}
