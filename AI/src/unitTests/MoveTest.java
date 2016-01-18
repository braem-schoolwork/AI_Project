package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import rubiks.*;

public class MoveTest {

	@Test
	public void moveXAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		move2.apply();
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo a move
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		move2.apply();
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveYAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		move2.apply();
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveYAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo a move
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		move2.apply();
		assertTrue(cube.isSolved());
	}

	@Test
	public void moveZAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		move2.apply();
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveZAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo a move
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		move2.apply();
		assertTrue(cube.isSolved());
	}

	@Test//change
	public void moveXYAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		move2.apply();
		Move move3 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		move3.apply();
		Move move4 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		move4.apply();
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXZAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		move2.apply();
		Move move3 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		move3.apply();
		Move move4 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		move4.apply();
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveYZAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		move2.apply();
		Move move3 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		move3.apply();
		Move move4 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		move4.apply();
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXYZAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		move2.apply();
		Move move3 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		move3.apply();
		Move move4 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		move4.apply();
		Move move5 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		move5.apply();
		Move move6 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		move6.apply();
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXYZAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo moves
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		move2.apply();
		Move move3 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		move3.apply();
		Move move4 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		move4.apply();
		Move move5 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		move5.apply();
		Move move6 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		move6.apply();
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXYAxisTest2() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		move2.apply();
		Move move3 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		move3.apply();
		Move move4 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		move4.apply();
		assertTrue(!cube.isSolved());
	}
	
	@Test
	public void moveErrorTest() { //attempt to move middle square
		RubiksCube cube = new RubiksCube(3);
		boolean flag = false;
		try {
			Move move = new Move(cube, cube.getSize(), 1, Axis.X, Direction.CW);
			move.apply();
		} catch(IllegalMoveException e) {
			flag = true;
		}
		assertTrue(flag);
	}
	
	@Test
	public void moveXYZAxisSize10Test() {
		RubiksCube cube = new RubiksCube(10);
		//do and undo moves
		Move move1 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		move1.apply();
		Move move2 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		move2.apply();
		Move move3 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		move3.apply();
		Move move4 = new Move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		move4.apply();
		Move move5 = new Move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		move5.apply();
		Move move6 = new Move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		move6.apply();
		assertTrue(cube.isSolved());
	}
}
