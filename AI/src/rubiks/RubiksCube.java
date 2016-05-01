package rubiks;

import java.util.Arrays;

import search.Searchable;

/**
 * Object to represent a physical Rubik's Cube
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */

public class RubiksCube implements Searchable
{
	private Move 		lastMoveApplied;
	private final int 	size;
	private byte[][][] 	cube;
	private Move[] 		moveSet;
	
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
		cube 	= createSolvedCube(size);
		moveSet = genAllMoves(3*2*size);
	}
	
	public RubiksCube(RubiksCube rubiksCube) {
		this.size 			= rubiksCube.size;
		byte[][][] cube 	= rubiksCube.cube;
		byte[][][] newCube 	= new byte[6][size][size];
		for(int i=0; i<cube.length; i++) //deep copy
			for(int j=0; j<cube[i].length; j++)
				for(int k=0; k<cube[j].length; k++)
					newCube[i][j][k] = cube[i][j][k];
		this.cube 		= newCube;
		this.moveSet 	= rubiksCube.moveSet;
	}
	
	/**
	 * @return	every legal move that could be applied to this Rubik's Cube
	 */
	public Move[] 		getMoveSet() 								{ return moveSet; }
	/**
	 * @return	this Rubik's Cube as a byte array
	 */
	public byte[][][] 	getCube() 									{ return cube; }
	public int 			getSize() 									{ return size; }
	public Move 		getLastMoveApplied() 						{ return lastMoveApplied; }
	public void 		setLastMoveApplied(Move lastMoveApplied) 	{ this.lastMoveApplied = lastMoveApplied; }
	
	@Override
	public Searchable[] genChildren() {
		RubiksCube[] 	allChildren 	= new RubiksCube[moveSet.length];
		Move[] 			moveList 		= this.moveSet;
		
		for(int i=0; i<moveList.length; i++) {
			Move 		move 		= moveList[i];
			RubiksCube 	cubeCopy 	= new RubiksCube(this);
			move.apply(cubeCopy);
			allChildren[i] 				= cubeCopy;
			cubeCopy.lastMoveApplied 	= move;
		}
		return allChildren;
	}
	
	/**
	 * Generates all possible moves for this Rubik's Cube
	 * @param numMovesPossible		the maximum number of moves for this rubik's cube
	 * @return						the list of all possible moves
	 */
	private Move[] genAllMoves(int numMovesPossible) {
		Move[] 	moveList 	= new Move[numMovesPossible];
		int 	ctr 		= 0;
		for(Axis axis : Axis.values())
			for(Direction dir : Direction.values())
				for(int i=0; i<size; i++) {
					Move move 		= new Move(new MoveParams(i%size, axis, dir));
					moveList[ctr] 	= move;
					ctr++;
				}
		return moveList;
	}
	
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
				default: color = ' '; //impossible
			}
			for(int j=0; j<cubeStr[i].length; j++)
				for(int k=0; k<cubeStr[i][j].length; k++)
					cubeStr[i][j][k] = color;
		}
		return cubeStr;
	}
	
	/**
	 * Returns whether or not this rubik's cube is in a solved state
	 * @return	<code>true</code> 	if this RubiksCube is solved
	 * 			<code>false</code> 	if this RubiksCube is not solved
	 */
	public boolean isSolved() {
		for(int i=0; i<3; i++) { //only need to traverse 3 faces
			byte color = cube[i][0][0];
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
		if(!(obj instanceof RubiksCube))
			return false;
		if(obj == this)
			return true;
		RubiksCube copy = (RubiksCube)obj; 
		if(Arrays.deepEquals(this.cube, copy.cube))
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(cube);
	}
	
	@Override
	public String toString() {
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
}
