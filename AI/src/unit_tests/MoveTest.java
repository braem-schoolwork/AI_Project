package unit_tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import rubiks.*;

//TODO Prove for n=2 & n=3 cube, various moves generate expected cube
public class MoveTest {

	@Test
	public void size2MoveTest() {
		RubiksCube cube = new RubiksCube(2);
		Move move1 = new Move(0, Axis.X, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.Y, Direction.CCW);
		move2.apply(cube);
		Move move3 = new Move(1, Axis.Z, Direction.CCW);
		move3.apply(cube);
		byte[][][] expectedCube = { {{'W', 'G'}, {'R', 'O'}},
				{{'O', 'B'}, {'O', 'R'}},
				{{'Y', 'Y'}, {'G', 'W'}},
				{{'R', 'W'}, {'B', 'Y'}},
				{{'W', 'O'}, {'B', 'B'}},
				{{'R', 'G'}, {'Y', 'G'}} };
		assertTrue(Arrays.deepEquals(expectedCube, cube.getCube()));
	}
	
	@Test
	public void size3MoveTest() {
		RubiksCube cube = new RubiksCube(3);
		Move move1 = new Move(0, Axis.X, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(2, Axis.Y, Direction.CCW);
		move2.apply(cube);
		Move move3 = new Move(1, Axis.Z, Direction.CCW);
		move3.apply(cube);
		byte[][][] expectedCube = { {{'W', 'W', 'O'}, {'G', 'W', 'G'}, {'G', 'W', 'G'}}, 
				{{'Y', 'Y', 'R'}, {'B', 'Y', 'B'}, {'B', 'O', 'B'}},
				{{'O', 'Y', 'Y'}, {'O', 'G', 'G'}, {'O', 'Y', 'Y'}},
				{{'W', 'W', 'W'}, {'R', 'B', 'B'}, {'R', 'R', 'R'}},
				{{'B', 'B', 'B'}, {'W', 'O', 'O'}, {'W', 'O', 'O'}},
				{{'G', 'G', 'G'}, {'Y', 'R', 'R'}, {'Y', 'R', 'R'}} };
		assertTrue(Arrays.deepEquals(expectedCube, cube.getCube()));
	}
	
	@Test
	public void moveXAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move move1 = new Move(0, Axis.X, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.X, Direction.CCW);
		move2.apply(cube);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo a move
		Move move1 = new Move(0, Axis.X, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.X, Direction.CCW);
		move2.apply(cube);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveYAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move move1 = new Move(0, Axis.Y, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.Y, Direction.CCW);
		move2.apply(cube);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveYAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo a move
		Move move1 = new Move(0, Axis.Y, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.Y, Direction.CCW);
		move2.apply(cube);
		assertTrue(cube.isSolved());
	}

	@Test
	public void moveZAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move move1 = new Move(0, Axis.Z, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.Z, Direction.CCW);
		move2.apply(cube);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveZAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo a move
		Move move1 = new Move(0, Axis.Z, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.Z, Direction.CCW);
		move2.apply(cube);
		assertTrue(cube.isSolved());
	}

	@Test//change
	public void moveXYAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move move1 = new Move(0, Axis.Y, Direction.CCW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.X, Direction.CW);
		move2.apply(cube);
		Move move3 = new Move(0, Axis.X, Direction.CCW);
		move3.apply(cube);
		Move move4 = new Move(0, Axis.Y, Direction.CW);
		move4.apply(cube);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXZAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move move1 = new Move(0, Axis.Z, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.X, Direction.CW);
		move2.apply(cube);
		Move move3 = new Move(0, Axis.X, Direction.CCW);
		move3.apply(cube);
		Move move4 = new Move(0, Axis.Z, Direction.CCW);
		move4.apply(cube);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveYZAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move move1 = new Move(0, Axis.Y, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.Z, Direction.CW);
		move2.apply(cube);
		Move move3 = new Move(0, Axis.Z, Direction.CCW);
		move3.apply(cube);
		Move move4 = new Move(0, Axis.Y, Direction.CCW);
		move4.apply(cube);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXYZAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move move1 = new Move(0, Axis.X, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.Z, Direction.CW);
		move2.apply(cube);
		Move move3 = new Move(0, Axis.Y, Direction.CW);
		move3.apply(cube);
		Move move4 = new Move(0, Axis.Y, Direction.CCW);
		move4.apply(cube);
		Move move5 = new Move(0, Axis.Z, Direction.CCW);
		move5.apply(cube);
		Move move6 = new Move(0, Axis.X, Direction.CCW);
		move6.apply(cube);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXYZAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo moves
		Move move1 = new Move(0, Axis.X, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.Z, Direction.CW);
		move2.apply(cube);
		Move move3 = new Move(0, Axis.Y, Direction.CW);
		move3.apply(cube);
		Move move4 = new Move(0, Axis.Y, Direction.CCW);
		move4.apply(cube);
		Move move5 = new Move(0, Axis.Z, Direction.CCW);
		move5.apply(cube);
		Move move6 = new Move(0, Axis.X, Direction.CCW);
		move6.apply(cube);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXYAxisTest2() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move move1 = new Move(0, Axis.X, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.Y, Direction.CW);
		move2.apply(cube);
		Move move3 = new Move(0, Axis.X, Direction.CCW);
		move3.apply(cube);
		Move move4 = new Move(0, Axis.Y, Direction.CCW);
		move4.apply(cube);
		assertTrue(!cube.isSolved());
	}
	
	@Test
	public void moveXYZAxisSize10Test() {
		RubiksCube cube = new RubiksCube(10);
		//do and undo moves
		Move move1 = new Move(0, Axis.X, Direction.CW);
		move1.apply(cube);
		Move move2 = new Move(0, Axis.Z, Direction.CW);
		move2.apply(cube);
		Move move3 = new Move(0, Axis.Y, Direction.CW);
		move3.apply(cube);
		Move move4 = new Move(0, Axis.Y, Direction.CCW);
		move4.apply(cube);
		Move move5 = new Move(0, Axis.Z, Direction.CCW);
		move5.apply(cube);
		Move move6 = new Move(0, Axis.X, Direction.CCW);
		move6.apply(cube);
		assertTrue(cube.isSolved());
	}
}
