package unitTests;

import static org.junit.Assert.*;
import rubiks.*;

import org.junit.Test;

public class RubiksCubeTest {
	
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
	public void genAllMovesTest() {
		RubiksCube rubiksCube = new RubiksCube(2);
		Move[] moves1 = rubiksCube.getMoveSet();
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
		RubiksCube[] children = (RubiksCube[]) cube.genChildren();
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
		RubiksCube[] children = (RubiksCube[]) cube.genChildren();
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
	public void heuristicTest() {
		RubiksCube cube = new RubiksCube(3);
		Move move = new Move(0, Axis.Y, Direction.CW);
		move.apply(cube);
		assertTrue(cube.h() == 1);
		move.apply(cube);
		assertTrue(cube.h() == 2);
		move.apply(cube);
		assertTrue(cube.h() == 1);
	}
}
