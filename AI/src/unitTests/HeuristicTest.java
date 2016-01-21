package unitTests;

import static org.junit.Assert.*;
import rubiks.*;
import org.junit.Test;

public class HeuristicTest {

	@Test
	public void heuristicXTest() {
		RubiksCube cube = new RubiksCube(3);
		Move move = new Move(0, Axis.X, Direction.CCW);
		move.apply(cube);
		assertTrue(cube.h() == 1);
		move.apply(cube);
		assertTrue(cube.h() == 2);
	}
	
	@Test
	public void heuristicYTest() {
		RubiksCube cube = new RubiksCube(3);
		Move move = new Move(2, Axis.Y, Direction.CW);
		move.apply(cube);
		assertTrue(cube.h() == 1);
		move.apply(cube);
		assertTrue(cube.h() == 2);
	}
	
	@Test
	public void heuristicZTest() {
		RubiksCube cube = new RubiksCube(3);
		Move move = new Move(2, Axis.Z, Direction.CCW);
		move.apply(cube);
		assertTrue(cube.h() == 1);
		move.apply(cube);
		assertTrue(cube.h() == 2);
	}

}
