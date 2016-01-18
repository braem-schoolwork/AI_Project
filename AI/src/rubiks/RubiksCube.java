package rubiks;

import java.util.Arrays;

//Directions: CW and CCW
//X, Y, Z axis
//0..S-1 slices per axis of rotation
//which means numMovesPossible = #axis * #directions * #slices;


/*
 * one Face for size==2 cube
 * ______________________
 * | [0, 0]  |  [0, 1]  |
 * |_________|__________|
 * | [1, 0]  |  [1, 1]  |
 * |_________|__________|
 * 
 * have 6 of these representing the faces
 * the coordinates represent the cubies
 * 
 */

//TODO use the list of moves to create random moves (by index)
//remove/add moves to ensure the depth of moves properly increases runtime complexity

public class RubiksCube implements Searchable
{
	//for searching
	private RubiksCube parent; //parent 'node'
	private Move moveAppliedToParent; //move applied to parent node to get this
	
	private final int size; //size=2 when dealing 2x2x2 cube & vice versa
	//representations of cube
	private char[][][] cube;
	private final int numMovesPossible; //cap on the amount of moves possible
	
	public RubiksCube(int size) {
		if(size < 2)
			throw new IllegalSizeException("Size for Rubiks Cube is less than 2");
		else
			this.size = size;
		cube = createSolvedCube(size);
		//when size is odd, we can't move middle slices, which decreases
		//the amount of moves possible. More precisely, decreases it by
		//2 * #numAxis == 6
		if(size%2 == 1)
			numMovesPossible = 3*2*size - 2*3;
		else
			numMovesPossible = 3*2*size;
	}
	//copy constructor
	public RubiksCube(RubiksCube rubiksCube) {
		this.parent = rubiksCube;
		this.size = rubiksCube.getSize();
		char[][][] cube = rubiksCube.getCube();
		char[][][] newCube = new char[6][size][size];
		//deep copy
		for(int i=0; i<cube.length; i++)
			for(int j=0; j<cube[i].length; j++)
				for(int k=0; k<cube[j].length; k++)
					newCube[i][j][k] = cube[i][j][k];
		this.cube = newCube;
		this.numMovesPossible = rubiksCube.getNumMovesPossible();
	}
	
	public char[][][] getCube() {
		return cube;
	}
	public int getSize() {
		return size;
	}
	public int getNumMovesPossible() {
		return numMovesPossible;
	} 
	public RubiksCube getParent() {
		return parent;
	}
	public Move getMoveAppliedToParent() {
		return moveAppliedToParent;
	}
	public void setCube(char[][][] cube) {
		this.cube = cube;
	}
	public void setMoveAppliedToParent(Move move) {
		this.moveAppliedToParent = move;
	}

	private static char[][][] createSolvedCube(int size) {
		char[][][] cubeStr = new char[6][size][size];
		for(int i=0; i<cubeStr.length; i++) {
			char color;
			switch(i) {
			case 0: color = 'G'; break;
			case 1: color = 'R'; break;
			case 2: color = 'W'; break;
			case 3: color = 'Y'; break;
			case 4: color = 'B'; break;
			case 5: color = 'O'; break;
			default: color = ' '; //never gets here
			}
			for(int j=0; j<cubeStr[i].length; j++)
				for(int k=0; k<cubeStr[i][j].length; k++)
					cubeStr[i][j][k] = color;
		}
		return cubeStr;
	}
	
	private static boolean isSolved(char[][][] cube) {
		for(int i=0; i<cube.length; i++) {
			char color = cube[i][0][0];
			for(int j=1; j<cube[i].length; j++)
				for(int k=1; k<cube[i][j].length; k++)
					if(color != cube[i][j][k])
						return false;
		}
		return true;
	}
	
	@Override
	public Searchable[] genChildren() {
		//2x2 and 3x3 have same number of moves (12)
		RubiksCube[] allChildren = new RubiksCube[numMovesPossible];
		Move[] moveList = this.genAllMoves();
		for(int i=0; i<moveList.length; i++) {
			Move move = moveList[i];
			RubiksCube cubeCopy = new RubiksCube(this);
			move.setCube(cubeCopy);
			move.apply();
			allChildren[i] = move.getCube();
			allChildren[i].setMoveAppliedToParent(move);
		}
		return allChildren;
	}
	
	public Move[] genAllMoves() {
		Move[] moveList = new Move[numMovesPossible];
		int ctr = 0;
		for(Axis axis : Axis.values())
			for(Direction dir : Direction.values())
				for(int i=0; i<size; i++)
					if(size%2 == 1 && i%size == size/2) { //if odd sized rubiks cube
						Move move = new Move((i%size)+1, axis, dir);
						//add to array
						moveList[ctr] = move;
						ctr++;
						i++;
					}
					else { //even sized
						Move move = new Move(i%size, axis, dir);
						//add to array
						moveList[ctr] = move;
						ctr++;
					}
		return moveList;
	}
	
	@Override
	public boolean equals(Searchable cube) {
		if(!(cube instanceof RubiksCube))
			return false;
		
		RubiksCube copy = (RubiksCube)cube;
		if(Arrays.deepEquals(this.cube, copy.getCube()))
			return true;
		else
			return false;
	}
	
	@Override
	public boolean isSolved() {
		return isSolved(this.cube);
	}
	
	@Override
	public String toString() {
		return Arrays.deepToString(cube);
	}

}
