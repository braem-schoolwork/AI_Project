package unitTests;

import static org.junit.Assert.*;
import rubiks.*;
import org.junit.Test;

public class HeuristicTest {

	@Test
	public void heuristicXTest() {
		RubiksCube cube = new RubiksCube(3);
		Move move = new Move(0, Axis.X, Direction.CW);
		Move move2 = new Move(0, Axis.Y, Direction.CW);
		System.out.println(cube.h());
		move.apply(cube);
		System.out.println(cube.h());
		move.apply(cube);
		System.out.println(cube.h());
		move2.apply(cube);
		System.out.println(cube.h());
	}
	
	@Test
	public void heuristicYTest() {
		RubiksCube cube = new RubiksCube(3);
		Move move = new Move(1, Axis.Y, Direction.CW);
		move.apply(cube);

	}
	
	@Test
	public void heuristicZTest() {
		RubiksCube cube = new RubiksCube(3);
		Move move = new Move(1, Axis.Z, Direction.CCW);
		move.apply(cube);

	}

}
