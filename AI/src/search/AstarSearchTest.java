package search;

import static org.junit.Assert.*;
import rubiks.*;

import org.junit.Test;

public class AstarSearchTest {

	@Test
	public void searchTest() {
		RubiksCube 	cube 			= new RubiksCube(2);
		Perturber.perturb(5, cube);
		AstarSearch AstarSearch 	= new AstarSearch();
		RubiksCube 	searchResult 	= (RubiksCube)AstarSearch.search(cube, new RubiksCube(cube.getSize()));
		assertTrue(searchResult.isSolved());
	}

	@Test
	public void searchTest2() {
		RubiksCube 	cube 			= new RubiksCube(3);
		Perturber.perturb(5, cube);
		AstarSearch AstarSearch 	= new AstarSearch();
		RubiksCube 	searchResult 	= (RubiksCube)AstarSearch.search(cube, new RubiksCube(cube.getSize()));
		assertTrue(searchResult.isSolved());
	}
	
}
