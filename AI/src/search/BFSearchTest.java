package search;

import static org.junit.Assert.*;

import org.junit.Test;

import rubiks.*;

public class BFSearchTest {

	@Test
	public void searchTest1() {
		RubiksCube cube = new RubiksCube(2);
		cube.perturb(2);
		BFSearch bfSearch = new BFSearch();
		RubiksCube searchResult = (RubiksCube)bfSearch.search(cube, new RubiksCube(cube.getSize()));
		assertTrue(searchResult.isSolved());
	}
	
	@Test
	public void searchTest2() {
		RubiksCube cube = new RubiksCube(3);
		cube.perturb(1);
		BFSearch bfSearch = new BFSearch();
		RubiksCube searchResult = (RubiksCube)bfSearch.search(cube, new RubiksCube(cube.getSize()));
		assertTrue(searchResult.isSolved());
	}
	
}
