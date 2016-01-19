package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import rubiks.*;

import search.BFSearch;

public class BFSearchTest {

	@Test
	public void searchTest1() {
		RubiksCube cube = new RubiksCube(2);
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		move2.apply();
		Move move3 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		move3.apply();
		BFSearch bfSearch = new BFSearch();
		RubiksCube searchResult = (RubiksCube)bfSearch.search(cube);
		assertTrue(bfSearch.getPath().size() == 4);
		assertTrue(searchResult.isSolved());
	}
	
	@Test
	public void searchTest2() {
		RubiksCube cube = new RubiksCube(3);
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		move2.apply();
		Move move3 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		move3.apply();
		BFSearch bfSearch = new BFSearch();
		RubiksCube searchResult = (RubiksCube)bfSearch.search(cube);
		assertTrue(bfSearch.getPath().size() == 4);
		assertTrue(searchResult.isSolved());
	}
	
	@Test
	public void searchTest3() {
		RubiksCube cube = new RubiksCube(4);
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		move2.apply();
		BFSearch bfSearch = new BFSearch();
		RubiksCube searchResult = (RubiksCube)bfSearch.search(cube);
		assertTrue(bfSearch.getPath().size() == 4);
		assertTrue(searchResult.isSolved());
	}
}
