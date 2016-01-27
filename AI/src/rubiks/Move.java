package rubiks;

import java.util.Arrays;

import search.Edge;
import search.Searchable;

/**
 * 
 * @author braem
 *
 * Represents a move on a RubiksCube
 * 
 * Similar to a Function in which has an apply method.
 * The details of the individual blocks (or cubies) is hidden
 * 
 * Abstracts moves and allows for easy generation of them.
 * 
 * CW and CCW directions
 * X, Y, Z axis
 * 0..S-1 slices per axis of rotation
 * 
 * A move can be intuitively thought of as a triple, 
 * (sliceNum, Axis, Direction)
 * 
 */


public class Move implements Edge
{	
	
	private int sliceNum;
	private Axis axis;
	private Direction dir;

	public Move(int sliceNum, Axis axis, Direction dir) {
		this.sliceNum = sliceNum;
		this.axis = axis;
		this.dir = dir;
	}
	
	public int getSliceNum() {
		return sliceNum;
	}
	public Axis getAxis() {
		return axis;
	}
	public Direction getDirection() {
		return dir;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Move)) return false;
		Move move = (Move)obj;
		if(this.sliceNum == move.getSliceNum() && this.axis.equals(move.getAxis()) &&
				this.dir.equals(move.getDirection())) 
			return true;
		else return false;
	}
	
	/*
	 * APPLY Method
	 * applies this move to a cube parameter
	 */
	public void apply(Searchable obj) {
		
		RubiksCube cube = (RubiksCube) obj;
		int size = cube.getSize();
		
		//initializations
		int from_face, to_face, from_row, to_row, from_col, to_col;
		byte[][][] cubeArr = cube.getCube();
		byte values[][] = new byte[4][size];
		int[] effectedFaces = findEffectedFaces();
		
		/* Dealing with edge cases */
		boolean isEdgeCase;
		if(sliceNum == 0 || sliceNum == size-1)
			isEdgeCase = true;
		else
			isEdgeCase = false;
		//if it is an edge case, rotate the respective face by the respective direction
		if(isEdgeCase) {
			int f = findEffectedEdgeFace();
			rotate(cubeArr[f]);
		}
		
		/* store needed values for turning */
		for(int i=0; i<effectedFaces.length; i++) {
			switch(axis) {
			case X:
				for(int k=0; k<values[i].length; k++)
					values[i][k] = cubeArr[effectedFaces[i]][k][sliceNum];
				break;
			case Y:
				for(int k=0; k<values[i].length; k++)
					values[i][k] = cubeArr[effectedFaces[i]][size-1-sliceNum][k];
				break;
			case Z:
				for(int k=0; k<values[i].length; k++)
					if(effectedFaces[i]==0 || effectedFaces[i]==1)
						values[i][k] = cubeArr[effectedFaces[i]][k][size-1-sliceNum];
					else
						values[i][k] = cubeArr[effectedFaces[i]][sliceNum][k];
				//System.out.println(Arrays.deepToString(values));
				break;
			}
		}
		
		
		/* 
		 * Loop to do the move 
		 */
		for(int i=0; i<effectedFaces.length; i++) {
			/*set from_face and to_face*/
			from_face = effectedFaces[i];
			if(i == effectedFaces.length-1)
				to_face = effectedFaces[0];
			else
				to_face = effectedFaces[i+1];
			//System.out.println(from_face+" "+to_face+" "+axis);
			
			//do the actual moving of rows/columns, juggling of characters, etc.
			for(int k=0; k<size; k++) {
				switch(axis) {
				case X:
					from_row = k;
					from_col = sliceNum;
					to_col = from_col;
					//[even to even] OR [odd to odd]
					if((from_face%2==0 && to_face%2==0) || (from_face%2==1 && to_face%2==1))
						to_row = from_row;
					else
						to_row = size-1-from_row;
					break;
				case Y: 
					from_row = size-1-sliceNum;
					from_col = k;
					to_row = from_row;
					if((from_face%2==0 && to_face%2==0) || (from_face%2==1 && to_face%2==1))
						to_col = from_col;
					else
						to_col = size-1-from_col;
					break;
				case Z:
					if(from_face == 1 || from_face == 0) {
						from_row = k;
						from_col = size-1-sliceNum;
					}
					else {
						from_row = sliceNum;
						from_col = k;
					}
					if((from_face%2==0 && to_face%2==0) || (from_face%2==1 && to_face%2==1)) {
						if(from_face == 1 || from_face == 0) {
							to_row = size-1-from_col;
							to_col = from_row;
						}
						else {
							to_row = from_col;
							to_col = size-1-from_row;
						}
					}
					else {
						to_col = size-1-from_row;
						to_row = size-1-from_col;
					}
					//System.out.println(from_face+" "+from_row+" "+from_col+" FROM "+axis);
					//System.out.println(to_face+" "+to_row+" "+to_col+" TO "+axis);
					break;
				default:
					from_row = -1;
					from_col = -1;
					to_row = -1;
					to_col = -1;
					break;
				}
				cubeArr[to_face][to_row][to_col] = values[i][k]; //set value here
			}//for moving of rows/columns
		}//for all effected faces
	}
	
	
	
	/*
	 * HELPER FUNCTIONS
	 */
	//rotates a two-dimensional array in a direction
	//used to implement edge cases
	private void rotate(byte[][] face) {
		int n = face.length;
		for(int i=0; i<=(n-1)/2; i++)
			for(int j=i; j<n-i-1; j++) {
				byte tmp;
				if(dir.equals(Direction.CW)) {
					tmp = face[i][j];
					face[i][j] = face[n-j-1][i];
					face[n-j-1][i] = face[n-i-1][n-j-1];
					face[n-i-1][n-j-1] = face[j][n-i-1];
					face[j][n-i-1] = tmp;
				}
				else {
					tmp = face[i][j];
					face[i][j] = face[j][n-i-1];
					face[j][n-i-1] = face[n-i-1][n-j-1];
					face[n-i-1][n-j-1] = face[n-j-1][i];
					face[n-j-1][i] = tmp;
				}
			}
	}
	//faces effected by edge moves
	private int findEffectedEdgeFace() {
		switch(axis) {
		case X:
			if(sliceNum == 0)
				return 0;
			else
				return 1;
		case Y:
			if(sliceNum == 0)
				return 2;
			else
				return 3;
		case Z:
			if(sliceNum == 0)
				return 4;
			else
				return 5;
		default: return -1;
		}
	}
	//faces effected on move
	private int[] findEffectedFaces() {
		switch(axis) {
		case X:
			if(dir.equals(Direction.CW)) {
				int[] effectedX = { 5, 3, 4, 2 };
				return effectedX;
			}
			else {
				int[] effectedX = { 5, 2, 4, 3 };
				return effectedX;
			}
		case Y:
			if(dir.equals(Direction.CW)) {
				int[] effectedY = { 5, 0, 4, 1 };
				return effectedY;
			}
			else {
				int[] effectedY = { 5, 1, 4, 0 };
				return effectedY;
			}
		case Z:
			if(dir.equals(Direction.CW)) {
				int[] effectedZ = { 3, 1, 2, 0 };
				return effectedZ;
			}
			else {
				int[] effectedZ = { 3, 0, 2, 1 };
				return effectedZ;
			}
		default: return null;
		}
	}
	
	@Override
	public String toString() {
		return "("+sliceNum+", "+axis+", "+dir+")";
	}
	
	public String toTrainingData() {
		String rtnStr = "";
		switch(sliceNum) {
		case 0: 
			byte[] slice0Stream = { -1,-1,1 };
			rtnStr += Arrays.toString(slice0Stream); break;
		case 1:
			byte[] slice1Stream = { -1,1,-1 };
			rtnStr += Arrays.toString(slice1Stream); break;
		case 2:
			byte[] slice2Stream = { 1,-1,-1 };
			rtnStr += Arrays.toString(slice2Stream); break;
		}
		rtnStr += ",";
		switch(axis) {
		case X:
			byte[] XaxisStream = { -1,-1,1 };
			rtnStr += Arrays.toString(XaxisStream); break;
		case Y:
			byte[] YaxisStream = { -1,1,-1 };
			rtnStr += Arrays.toString(YaxisStream); break;
		case Z:
			byte[] ZaxisStream = { 1,-1,-1 };
			rtnStr += Arrays.toString(ZaxisStream); break;
		}
		rtnStr += ",";
		if(dir.equals(Direction.CW)) rtnStr += -1;
		else rtnStr += 1;
		return rtnStr;
	}
	
}
