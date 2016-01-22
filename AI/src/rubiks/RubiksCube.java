package rubiks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import search.Searchable;

/*
 * Cube represented by a [6][size][size] character array
 * 
 * CW and CCW directions
 * X, Y, Z axis
 * 0..S-1 slices per axis of rotation
 * numMovesPossible = #axis * #directions * #slices;
 */

public class RubiksCube implements Searchable, Comparable<RubiksCube>
{
	//links to parent to aid with searching
	private RubiksCube parent; //parent 'node'
	private Move moveAppliedToParent; //move applied to parent node to get this
	private int gValue; //A* search g value
	
	private final int size; //size=2 when dealing 2x2x2 cube & vice versa
	private char[][][] cube; //representation of the cube
	
	private Move[] moveSet; //move set for this cube
	
	//default color scheme
	private static char defaultFace0Color = 'G';
	private static char defaultFace1Color = 'B';
	private static char defaultFace2Color = 'Y';
	private static char defaultFace3Color = 'W';
	private static char defaultFace4Color = 'O';
	private static char defaultFace5Color = 'R';
	
	boolean isSolved = false; //keeps track of whether this cube is solved or not
	
	public RubiksCube(int size) {
		this.parent = null;
		this.moveAppliedToParent = null;
		this.gValue = 1;
		this.isSolved = true;
		
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
		char[][][] cube = rubiksCube.cube;
		char[][][] newCube = new char[6][size][size];
		//deep copy
		for(int i=0; i<cube.length; i++)
			for(int j=0; j<cube[i].length; j++)
				for(int k=0; k<cube[j].length; k++)
					newCube[i][j][k] = cube[i][j][k];
		this.cube = newCube;
		this.isSolved = rubiksCube.isSolved;
		this.moveSet = rubiksCube.moveSet;
	}
	
	//getters
	public Move[] getMoveSet() {
		return moveSet;
	}
	public char[][][] getCube() {
		return cube;
	}
	public int getSize() {
		return size;
	}
	
	//for tracing back moves from a search
	public RubiksCube getParent() {
		return parent;
	}
	public Move getMoveAppliedToParent() {
		return moveAppliedToParent;
	}
	public void setMoveAppliedToParent(Move move) {
		this.moveAppliedToParent = move;
	}
	//increments cost from start (g value)
	public void incrementGVal() {
		gValue++;
	}
	
	/*
	 * Method to trace back the path from a search
	 * Returns null if no parent is found
	 */
	public ArrayList<Move> traceMoves() {
		if(this.parent == null) return null;
		//assume this is the end state of a search
		RubiksCube parent = this.parent;
		ArrayList<Move> moves = new ArrayList<Move>();
		moves.add(moveAppliedToParent);
		while(!(parent.getParent() == null)) {
			moves.add(0, parent.getMoveAppliedToParent());
			parent = parent.getParent();
		}
		return moves;
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
		//2x2 and 3x3 have same number of moves (12)
		RubiksCube[] allChildren = new RubiksCube[moveSet.length];
		Move[] moveList = this.moveSet; //TODO MOVE THIS TO CONSTRUCTOR?
		//GET MOVES FROM THIS. IE create movelist at constructor & pass the movelist down
		for(int i=0; i<moveList.length; i++) {
			Move move = moveList[i];
			RubiksCube cubeCopy = new RubiksCube(this);
			cubeCopy.incrementGVal();
			move.apply(cubeCopy);
			allChildren[i] = cubeCopy;
			allChildren[i].setMoveAppliedToParent(move);
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
	private static char[][][] createSolvedCube(int size) {
		char[][][] cubeStr = new char[6][size][size];
		for(int i=0; i<cubeStr.length; i++) {
			char color;
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
			char color = cube[i][0][0]; //face color
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
	public int f() { //Cost function, f(x) = g() + h()
		return g() + h();
	}
	@Override
	public int g() { //cost from start
		return gValue;
	}
	public int h() { //estimate to end
		return HeuristicCalculation.calculate(this);
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
	public boolean equals(Searchable cube) {
		if(this == cube) return true; //same reference
		if(!(cube instanceof RubiksCube)) return false; //not a rubikscube
		if(((RubiksCube)cube).isSolved) return isSolved(); //all solved cubes are equal EATS TIME!
		RubiksCube copy = (RubiksCube)cube; 
		if(Arrays.deepEquals(this.cube, copy.cube)) return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(cube);
	}
	
	@Override
	public String toString() { //simple toString override
		return Arrays.deepToString(cube);
	}
	
}
