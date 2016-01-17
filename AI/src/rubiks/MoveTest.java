package rubiks;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class MoveTest {

	@Test
	public void moveXAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveYAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveYAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		assertTrue(cube.isSolved());
	}

	@Test
	public void moveZAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveZAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		assertTrue(cube.isSolved());
	}

	@Test//change
	public void moveXYAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXZAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveYZAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXYZAxisSize3Test() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXYZAxisSize2Test() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo moves
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXYAxisTest2() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo moves
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		assertTrue(!cube.isSolved());
	}
	
	@Test
	public void moveErrorTest() { //attempt to move middle square
		RubiksCube cube = new RubiksCube(3);
		boolean flag = false;
		try {
			Move.move(cube, cube.getSize(), 1, Axis.X, Direction.CW);
		} catch(IllegalMoveException e) {
			flag = true;
		}
		assertTrue(flag);
	}
	
	@Test
	public void moveXYZAxisSize10Test() {
		RubiksCube cube = new RubiksCube(10);
		//do and undo moves
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		assertTrue(cube.isSolved());
	}
}
