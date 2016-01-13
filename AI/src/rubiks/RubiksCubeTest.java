package rubiks;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class RubiksCubeTest {

	@Test
	public void RubiksCubeConstructorTest() {
		RubiksCube rubiksCube = new RubiksCube(2);
		String cube1 = rubiksCube.getCube();
		String cube2 = "GGGGRRRRWWWWYYYYBBBBOOOO";
		assertTrue(cube1.equals(cube2));
	}
	
	@Test
	public void isSolvedTest() {
		RubiksCube rubiksCube = new RubiksCube(2);
		assertTrue(rubiksCube.isSolved());
	}

	@Test
	public void isSolvedTest2() {
		RubiksCube rubiksCube = new RubiksCube(3);
		assertTrue(rubiksCube.isSolved());
	}
}
