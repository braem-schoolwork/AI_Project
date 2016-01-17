package rubiks;

import static org.junit.Assert.*;

import org.junit.Test;

public class BFSearchTest {

	@Test
	public void searchTest1() {
		RubiksCube cube = new RubiksCube(2);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		assertTrue(BFSearch.search(cube).isSolved());
	}
	
	@Test
	public void searchTest2() {
		RubiksCube cube = new RubiksCube(3);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		assertTrue(BFSearch.search(cube).isSolved());
	}
	
	@Test
	public void searchTest3() {
		RubiksCube cube = new RubiksCube(4);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		assertTrue(BFSearch.search(cube).isSolved());
	}
}
