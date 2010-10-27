package com.synchrosinteractive.boggle;


import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoggleGameTest {

	@Before
	public void setUp() throws Exception {
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
		Assert.assertEquals(3, cells.length);
	}

}
