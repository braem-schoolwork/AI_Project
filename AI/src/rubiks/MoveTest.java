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
		
		
		RubiksCube cube1 = new RubiksCube(2);
		char[][][] cube2 = cube1.getCube();
		cube2[0][0][0] = 'Y';
		cube2[0][0][1] = 'R';
		cube2[0][1][0] = 'W';
		cube2[0][1][1] = 'O';

		System.out.println(Arrays.deepToString(cube2));
		Move.rotate(cube2[0], Direction.CW);
		System.out.println(Arrays.deepToString(cube2));
		assertTrue(cube.isSolved());
	}

}
