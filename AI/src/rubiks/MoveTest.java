package rubiks;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class MoveTest {

	@Test
	public void moveTest1() {
		RubiksCube cube = new RubiksCube(3);
		//do and undo a move
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CW);
		Move.move(cube, cube.getSize(), 0, Axis.X, Direction.CCW);
		assertTrue(cube.isSolved());
	}

}
