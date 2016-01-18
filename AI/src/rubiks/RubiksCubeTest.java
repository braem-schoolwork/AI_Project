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
	public void genAllMovesTest() {
		RubiksCube rubiksCube = new RubiksCube(2);
		Move[] moves1 = rubiksCube.genAllMoves();
		Move[] moves2 = new Move[12];
		moves2[0] = new Move(0, Axis.X, Direction.CW);
		moves2[1] = new Move(1, Axis.X, Direction.CW);
		moves2[2] = new Move(0, Axis.X, Direction.CCW);
		moves2[3] = new Move(1, Axis.X, Direction.CCW);
		moves2[4] = new Move(0, Axis.Y, Direction.CW);
		moves2[5] = new Move(1, Axis.Y, Direction.CW);
		moves2[6] = new Move(0, Axis.Y, Direction.CCW);
		moves2[7] = new Move(1, Axis.Y, Direction.CCW);
		moves2[8] = new Move(0, Axis.Z, Direction.CW);
		moves2[9] = new Move(1, Axis.Z, Direction.CW);
		moves2[10] = new Move(0, Axis.Z, Direction.CCW);
		moves2[11] = new Move(1, Axis.Z, Direction.CCW);
		for(int i=0; i<moves1.length; i++) {
			assertTrue(moves1[i].equals(moves2[i]));
		}
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
