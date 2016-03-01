package rubiks;

import java.util.Arrays;

import search.Searchable;

/**
 * 
 * @author braem
 *
 * Class that represents an actual, physical rubik's cube
 * 
 * The Cube represented by a [6][size][size] character array
 * The Move Set for the cube is generated when the cube is made
 * 
 * Cannot make a Rubik's Cube unless you specify the size.
 * 
 * As for searching, The Rubik's Cube also keeps track of its parent,
 * the move applied to the parent to get this current cube, and the 
 * A* search g value.
 * 
 * 
 * NOTE: Specifying your own face colors will break the A* search heuristic
 * 
 */

public class RubiksCube implements Searchable
{
	private Move lastMoveApplied; //keeps track of the last move applied to this
	
	private final int size; //size=2 when dealing 2x2x2 cube & vice versa
	private byte[][][] cube; //representation of the cube [6][size][size]
	
	private Move[] moveSet; //move set for this cube
	
	//default color scheme
	private static byte defaultFace0Color = 'G';
	private static byte defaultFace1Color = 'B';
	private static byte defaultFace2Color = 'Y';
	private static byte defaultFace3Color = 'W';
	private static byte defaultFace4Color = 'O';
	private static byte defaultFace5Color = 'R';
	
	public RubiksCube(int size) {
		if(size < 2)
			throw new IllegalSizeException("Size for Rubiks Cube is less than 2");
		else
			this.size = size;
		cube = createSolvedCube(size);
		moveSet = genAllMoves(3*2*size); //generate move set for this cube
	}
	
	//copy constructor. only need to deep copy the array
	//everything else can be pointers
	public RubiksCube(RubiksCube rubiksCube) {
		this.size = rubiksCube.size;
		byte[][][] cube = rubiksCube.cube;
		byte[][][] newCube = new byte[6][size][size];
		//deep copy
		for(int i=0; i<cube.length; i++)
			for(int j=0; j<cube[i].length; j++)
				for(int k=0; k<cube[j].length; k++)
					newCube[i][j][k] = cube[i][j][k];
		this.cube = newCube;
		this.moveSet = rubiksCube.moveSet;
	}
	
	//getters
	public Move[] getMoveSet() {
		return moveSet;
	}
	public byte[][][] getCube() {
		return cube;
	}
	public int getSize() {
		return size;
	}
	
	/*
	 * Generate children implementation from Searchable
	 */
	@Override
	public Searchable[] genChildren() {
		RubiksCube[] allChildren = new RubiksCube[moveSet.length];
		Move[] moveList = this.moveSet;
		for(int i=0; i<moveList.length; i++) {
			Move move = moveList[i];
			RubiksCube cubeCopy = new RubiksCube(this);
			move.apply(cubeCopy);
			allChildren[i] = cubeCopy;
			allChildren[i].lastMoveApplied = move;
		}
		return allChildren;
	}
	
	/*
	 * Generates all moves for this rubiks cube
	 */
	private Move[] genAllMoves(int numMovesPossible) {
		Move[] moveList = new Move[numMovesPossible];
		int ctr = 0;
		for(Axis axis : Axis.values())
			for(Direction dir : Direction.values())
				for(int i=0; i<size; i++) {
					Move move = new Move(new MoveParams(i%size, axis, dir));
					//add to array
					moveList[ctr] = move;
					ctr++;
				}
		return moveList;
	}
	
	/*
	 * methods to create a solved rubiks cube
	 */
	private static byte[][][] createSolvedCube(int size) {
		byte[][][] cubeStr = new byte[6][size][size];
		for(int i=0; i<cubeStr.length; i++) {
			byte color;
			switch(i) {
			case 0: color = defaultFace0Color; break;
			case 1: color = defaultFace1Color; break;
			case 2: color = defaultFace2Color; break;
			case 3: color = defaultFace3Color; break;
			case 4: color = defaultFace4Color; break;
			case 5: color = defaultFace5Color; break;
			default: color = ' '; //never gets here
			}
			for(int j=0; j<cubeStr[i].length; j++)
				for(int k=0; k<cubeStr[i][j].length; k++)
					cubeStr[i][j][k] = color;
		}
		return cubeStr;
	}
	
	/*
	 * method to determine if the cube is in a solved state
	 */
	public boolean isSolved() {
		for(int i=0; i<3; i++) { //only need to traverse 3 faces
			byte color = cube[i][0][0]; //face color
			for(int j=0; j<cube[i].length; j++)
				for(int k=0; k<cube[i][j].length; k++)
					if(color != cube[i][j][k])
						return false;
		}
		return true;
	}
	
	/*
	 * Heuristic Calculation
	 */
	@Override
	public float h() { //estimate to end
		return HeuristicCalculation.calculate(this);
	}

	/*
	 * Comparison Method Overrides
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof RubiksCube)) return false;
		if(obj == this) return true;
		RubiksCube copy = (RubiksCube)obj; 
		if(Arrays.deepEquals(this.cube, copy.cube)) return true;
		return false;
	}
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(cube);
	}
	
	@Override
	public String toString() { //simple toString override
		String rtnStr = "[";
		for(int i=0; i<cube.length; i++) {
			rtnStr += "[";
			for(int j=0; j<cube[i].length; j++) {
				rtnStr += "[";
				for(int k=0; k<cube[i][j].length; k++) {
					rtnStr += ((char)cube[i][j][k]);
					if(k!=cube[i][j].length-1)
						rtnStr += ", ";
				}
				rtnStr += "]";
				if(j!=cube[i].length-1)
					rtnStr += ", ";
			}
			rtnStr += "]";
			if(i!=cube.length-1)
				rtnStr += ", ";
		}
		rtnStr += "]";
		return rtnStr;
	}

	public Move getLastMoveApplied() {
		return lastMoveApplied;
	}
	public void setLastMoveApplied(Move lastMoveApplied) {
		this.lastMoveApplied = lastMoveApplied;
	}
}
