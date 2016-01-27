package rubiks;

import java.util.Arrays;
import java.util.Random;

import search.Edge;
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

public class RubiksCube implements Searchable, Comparable<RubiksCube>
{
	//links to parent to aid with searching
	private RubiksCube parent; //parent 'node'
	private Edge edge; //edge from parent to this
	private int gValue; //A* search g value
	
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
		this.parent = null;
		this.edge = null;
		this.gValue = 0;
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
		this.gValue = rubiksCube.g();
		this.parent = rubiksCube;
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
	
	//for tracing back moves from a search
	@Override
	public RubiksCube getParent() {
		return parent;
	}
	@Override
	public Edge getEdge() {
		return edge;
	}
	
	//private setters
	private void setEdge(Edge move) {
		this.edge = move;
	}
	//increments cost from start (g value)
	private void incrementGVal() {
		gValue++;
	}
	
	/* 
	 * Method to apply a number of random moves to the cube
	 * 
	 * try to preserve the integrity of perturbation by making sure moves
	 * dont roll back to a previous state
	 */
	public void perturb(int depth) {
		//check
		if(depth <= 0 || depth > moveSet.length) throw new IllegalDepthException();
		//variables
		Move[] moveList = moveSet;
		int consecutiveMoves = 0; //counts consecutive same moves
		Random rand = new Random();
		int randomMove; //index for a move in the list
		
		//apply a randomMove
		randomMove = rand.nextInt(moveSet.length);
		Move moveToApply = moveList[randomMove];
		//copy this instead
		Move lastMoveApplied = moveToApply;
		moveToApply.apply(this);
		//attempt to preserve depth integrity
		for(int i=1; i<depth; i++) {
			randomMove = rand.nextInt(moveSet.length);
			moveToApply = moveList[randomMove];
			//same axis && same sliceNum
			if( lastMoveApplied.getAxis().equals(moveToApply.getAxis()) && lastMoveApplied.getSliceNum()==moveToApply.getSliceNum() )
				if(!lastMoveApplied.getDirection().equals(lastMoveApplied.getDirection())) {//not same direction (undos previous move)
					if(randomMove == moveList.length-1) //apply different move
						moveToApply = moveList[0];
					else
						moveToApply = moveList[randomMove+1];
					consecutiveMoves = 0;
				}
				else { //same move as previous
					if(consecutiveMoves == 2) { //2 of the same moves applied already
						if(randomMove == moveList.length-1) //apply different move
							moveToApply = moveList[0];
						else
							moveToApply = moveList[randomMove+1];
						consecutiveMoves = 0;
					}
					else //count consecutiveMoves
						consecutiveMoves++;
				}
			else
				consecutiveMoves = 0;
			moveToApply.apply(this);
			lastMoveApplied = moveToApply;
		}
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
			cubeCopy.incrementGVal();
			move.apply(cubeCopy);
			allChildren[i] = cubeCopy;
			allChildren[i].setEdge(move);
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
					Move move = new Move(i%size, axis, dir);
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
	 * A* Search related methods
	 */
	public float f() { //Cost function, f(x) = g() + h()
		float fVal = g() + h();
		return fVal;
	}
	@Override
	public int g() { //cost from start
		return gValue;
	}
	public float h() { //estimate to end
		float hVal = HeuristicCalculation.calculate(this);
		return hVal;
	}

	/*
	 * Comparison Method Overrides
	 */
	@Override
	public int compareTo(RubiksCube cube) {
		if(this.f() > cube.f())
			return 10;
		else if(this.f() < cube.f())
			return -10;
		else
			return 0;
	}
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
	
	public String toTrainingData() {
		String rtnStr = "\n";
		for(int i=0; i<cube.length; i++) {
			for(int j=0; j<cube[i].length; j++) {
				for(int k=0; k<cube[i][j].length; k++) {
					rtnStr += colorToBits(cube[i][j][k]);
					if(k!=cube[i][j].length-1)
						rtnStr += ".";
				}
				rtnStr += ".";
			}
			if(i!=cube.length-1)
				rtnStr += "\n";
		}
		return rtnStr;
	}
	
	private String colorToBits(byte color) {
		switch(color) {
		case 'G':
			byte[] GcolorStream = { -1,-1,-1,-1,-1,1 };
			return Arrays.toString(GcolorStream);
		case 'B':
			byte[] BcolorStream = { -1,-1,-1,-1,1,-1 };
			return Arrays.toString(BcolorStream);
		case 'Y':
			byte[] YcolorStream = { -1,-1,-1,1,-1,-1 };
			return Arrays.toString(YcolorStream);
		case 'W':
			byte[] WcolorStream = { -1,-1,1,-1,-1,-1 };
			return Arrays.toString(WcolorStream);
		case 'O':
			byte[] OcolorStream = { -1,1,-1,-1,-1,-1 };
			return Arrays.toString(OcolorStream);
		case 'R':
			byte[] RcolorStream = { 1,-1,-1,-1,-1,-1 };
			return Arrays.toString(RcolorStream);
		default: return null;
		}
	}
	
}
