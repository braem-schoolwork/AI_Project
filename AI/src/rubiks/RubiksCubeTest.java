package rubiks;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class RubiksCubeTest {

	@Test
	public void RubiksCubeConstructorTest() {
		//GGGGRRRRWWWWYYYYBBBBOOOO
		RubiksCube rubiksCube = new RubiksCube(2);
		char[][][] cube1 = rubiksCube.getCube();
		char[][][] cube2 = { {{'G', 'G'}, {'G', 'G'}}, {{'R', 'R'}, {'R', 'R'}}, {{'W', 'W'}, {'W', 'W'}},
				{{'Y', 'Y'}, {'Y', 'Y'}}, {{'B', 'B'}, {'B', 'B'}}, {{'O', 'O'}, {'O', 'O'}} };
		for(char[][] arr2: cube1)
			for(char[] arr1: arr2)
				for(char val: arr1)
					System.out.print(val);
		assertTrue(Arrays.deepEquals(cube1, cube2));
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
	
	@Test
	public void isSolvedTest3() {
		char[][][] cube = { {{'R', 'R'}, {'R', 'R'}}, {{'G', 'G'}, {'G', 'G'}}, {{'B', 'B'}, {'B', 'B'}},
				{{'Y', 'Y'}, {'Y', 'Y'}}, {{'W', 'W'}, {'W', 'W'}}, {{'O', 'O'}, {'O', 'O'}} };
		assertTrue(RubiksCube.isSolved(cube));
	}
}
