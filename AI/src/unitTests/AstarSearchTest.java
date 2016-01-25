package unitTests;

import static org.junit.Assert.*;
import rubiks.*;
import search.AstarSearch;

import org.junit.Test;

public class AstarSearchTest {

	@Test
	public void searchTest() {
		RubiksCube cube = new RubiksCube(2);
		cube.perturb(5);
		AstarSearch AstarSearch = new AstarSearch();
		RubiksCube searchResult = (RubiksCube)AstarSearch.search(cube, new RubiksCube(cube.getSize()));
		assertTrue(searchResult.isSolved());
	}

	@Test
	public void searchTest2() {
		RubiksCube cube = new RubiksCube(3);
		cube.perturb(5);
		AstarSearch AstarSearch = new AstarSearch();
		RubiksCube searchResult = (RubiksCube)AstarSearch.search(cube, new RubiksCube(cube.getSize()));
		assertTrue(searchResult.isSolved());
	}
	
}
