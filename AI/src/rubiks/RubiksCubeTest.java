package rubiks;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class RubiksCubeTest {

	@Test
	public void RubiksCubeConstructorTest() {
		RubiksCube rubiksCube = new RubiksCube(2);
		char[][][] cube1 = rubiksCube.getCube();
		char[][][] cube2 = { {{'G', 'G'}, {'G', 'G'}}, {{'R', 'R'}, {'R', 'R'}}, {{'W', 'W'}, {'W', 'W'}},
				{{'Y', 'Y'}, {'Y', 'Y'}}, {{'B', 'B'}, {'B', 'B'}}, {{'O', 'O'}, {'O', 'O'}} };
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
		RubiksCube rubiksCube = new RubiksCube(2);
		char[][][] cube = { {{'R', 'R'}, {'R', 'R'}}, {{'G', 'G'}, {'G', 'G'}}, {{'B', 'B'}, {'B', 'B'}},
				{{'Y', 'Y'}, {'Y', 'Y'}}, {{'W', 'W'}, {'W', 'W'}}, {{'O', 'O'}, {'O', 'O'}} };
		rubiksCube.setCube(cube);
		assertTrue(rubiksCube.isSolved());
	}
	
	@Test
	public void genChildrenTest() {
		//make sure no 2 children are the same
		//proves all moves are unique
		RubiksCube cube = new RubiksCube(2);
		Searchable[] children = cube.genChildren();
		assertTrue(children.length == 12);
		boolean flag = true;
		for(int i=0; i<children.length; i++)
			for(int j=0; j<children.length; j++)
				if(children[i].equals(children[j]) && i!=j) {
					flag = false;
					break;
				}
		assertTrue(flag);
	}
	
	@Test
	public void genChildrenTest2() {
		//make sure no 2 children are the same
		//proves all moves are unique
		RubiksCube cube = new RubiksCube(3);
		Searchable[] children = cube.genChildren();
		assertTrue(children.length == 12);
		boolean flag = true;
		for(int i=0; i<children.length; i++)
			for(int j=0; j<children.length; j++)
				if(children[i].equals(children[j]) && i!=j) {
					flag = false;
					break;
				}
		assertTrue(flag);
	}
}
