package rubiks;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class MoveTest {

	@Test
	public void moveXAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveYAxisTest() {
		System.out.println();
		System.out.println();
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		assertTrue(cube.isSolved());
	}

	@Test
	public void moveZAxisTest() {
		System.out.println();
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
									//shit
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		assertTrue(cube.isSolved());
	}

	@Test//change
	public void moveXYAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		System.out.println(Arrays.deepToString(cube.getCube()));
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		System.out.println(Arrays.deepToString(cube.getCube()));
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		System.out.println(Arrays.deepToString(cube.getCube()));
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		System.out.println(Arrays.deepToString(cube.getCube()));
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		System.out.println(Arrays.deepToString(cube.getCube()));
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXZAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveYZAxisTest() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXYZAxisTest() {
		RubiksCube cube = new RubiksCube(2);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Z, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		assertTrue(cube.isSolved());
	}
	
	@Test
	public void moveXYAxisTest2() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		Move.move(cube, cube.getSize(), 0, Axis.Y, Direction.CCW);
		assertTrue(!cube.isSolved());
	}
}
