package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import rubiks.*;

import search.BFSearch;

public class BFSearchTest {

	@Test
	public void searchTest1() {
		RubiksCube cube = new RubiksCube(2);
		cube.perturb(3);
		BFSearch bfSearch = new BFSearch();
		long startTime = System.nanoTime();
		RubiksCube searchResult = (RubiksCube)bfSearch.search(cube, RubiksCube.createSolvedRubiksCube(cube.getSize()));
		long endTime = System.nanoTime();
		assertTrue(bfSearch.getPath().size() == 4);
		assertTrue(searchResult.isSolved());
		long duration = (endTime - startTime)/1000000;
		System.out.println(duration);
	}
	
	@Test
	public void searchTest2() {
		RubiksCube cube = new RubiksCube(3);
		cube.perturb(3);
		BFSearch bfSearch = new BFSearch();
		double startTime = System.nanoTime();
		RubiksCube searchResult = (RubiksCube)bfSearch.search(cube, RubiksCube.createSolvedRubiksCube(cube.getSize()));
		double endTime = System.nanoTime();
		assertTrue(bfSearch.getPath().size() == 4);
		assertTrue(searchResult.isSolved());
		double duration = (endTime - startTime)/1000000000;
		System.out.println(duration);
	}
	
	@Test
	public void searchTest3() {
		RubiksCube cube = new RubiksCube(4);
		cube.perturb(2);
		BFSearch bfSearch = new BFSearch();
		RubiksCube searchResult = (RubiksCube)bfSearch.search(cube, RubiksCube.createSolvedRubiksCube(cube.getSize()));
		assertTrue(bfSearch.getPath().size() <= 3);
		assertTrue(searchResult.isSolved());
	}
}
