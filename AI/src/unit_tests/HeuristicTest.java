package unit_tests;

import static org.junit.Assert.*;
import rubiks.*;
import org.junit.Test;

public class HeuristicTest {

	@Test
	public void heuristicTest() {
		RubiksCube cube = new RubiksCube(3);
		Move move = new Move(0, Axis.X, Direction.CW);
		Move move2 = new Move(0, Axis.Y, Direction.CW);
		//assert that the heuristic never overestimates moves left to solve
		assertTrue(cube.h() == 0);
		move.apply(cube);
		assertTrue(cube.h() <= 1);
		move.apply(cube);
		assertTrue(cube.h() <= 2);
		move2.apply(cube);
		assertTrue(cube.h() <= 3);
	}

}
