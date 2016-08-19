package rubiks;

/**
 * Represents a move on a rubik's cube.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */

public class Move
{	
	private MoveParams params;
	
	public Move(MoveParams params) 		{ this.params = params; }
	
	public MoveParams getMoveParams() 	{ return params; }
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Move))
			return false;
		Move move = (Move)obj;
		if(this.params.getSliceNum() == move.params.getSliceNum() && this.params.getAxis().equals(move.params.getAxis()) &&
				this.params.getDirection().equals(move.params.getDirection())) 
			
			return true;
		else
			return false;
	}
	
	/**
	 * Applies the move according to this class' MoveParams object.
	 * 
	 * @param cube		Rubik's cube to perform the move on
	 */
	public void apply(RubiksCube cube) {
		int 		sliceNum 		= params.getSliceNum();
		Axis 		axis 			= params.getAxis();
		int 		size 			= cube.getSize();
		byte[][][] 	cubeArr 		= cube.getCube();
		byte 		values[][] 		= new byte[4][size];
		int[] 		effectedFaces 	= findEffectedFaces();
		int 		from_face, to_face, from_row, to_row, from_col, to_col;
		
		boolean isEdgeCase;
		if(sliceNum == 0 || sliceNum == size-1)
			isEdgeCase = true;
		else
			isEdgeCase = false;
		if(isEdgeCase) {
			int f = findEffectedEdgeFace();
			rotate(cubeArr[f]);
		}
		
		for(int i=0; i<effectedFaces.length; i++)
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
					break;
			}
		
		for(int i=0; i<effectedFaces.length; i++) {
			from_face = effectedFaces[i];
			if(i == effectedFaces.length-1)
				to_face = effectedFaces[0];
			else
				to_face = effectedFaces[i+1];

			for(int k=0; k<size; k++) {
				switch(axis) {
					case X:
						from_row 	= k;
						from_col 	= sliceNum;
						to_col 		= from_col;
						if((from_face%2==0 && to_face%2==0) || (from_face%2==1 && to_face%2==1))
							to_row = from_row;
						else
							to_row = size-1-from_row;
						break;
					case Y: 
						from_row 	= size-1-sliceNum;
						from_col 	= k;
						to_row 		= from_row;
						if((from_face%2==0 && to_face%2==0) || (from_face%2==1 && to_face%2==1))
							to_col = from_col;
						else
							to_col = size-1-from_col;
						break;
					case Z:
						if(from_face == 1 || from_face == 0) {
							from_row 	= k;
							from_col 	= size-1-sliceNum;
						}
						else {
							from_row 	= sliceNum;
							from_col 	= k;
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
						break;
					default:
						from_row 	= -1;
						from_col 	= -1;
						to_row 		= -1;
						to_col 		= -1;
						break;
				}
				cubeArr[to_face][to_row][to_col] = values[i][k]; //set value here
			}//for moving of rows/columns
		}//for all effected faces
	}
	
	private void rotate(byte[][] face) {
		int n = face.length;
		for(int i=0; i<=(n-1)/2; i++)
			for(int j=i; j<n-i-1; j++) {
				byte tmp;
				if(params.getDirection().equals(Direction.CW)) {
					tmp 				= face[i][j];
					face[i][j] 			= face[n-j-1][i];
					face[n-j-1][i] 		= face[n-i-1][n-j-1];
					face[n-i-1][n-j-1] 	= face[j][n-i-1];
					face[j][n-i-1] 		= tmp;
				}
				else {
					tmp 				= face[i][j];
					face[i][j] 			= face[j][n-i-1];
					face[j][n-i-1] 		= face[n-i-1][n-j-1];
					face[n-i-1][n-j-1] 	= face[n-j-1][i];
					face[n-j-1][i] 		= tmp;
				}
			}
	}
	
	private int findEffectedEdgeFace() {
		switch(params.getAxis()) {
			case X:
				if(params.getSliceNum() == 0)
					return 0;
				else
					return 1;
			case Y:
				if(params.getSliceNum() == 0)
					return 2;
				else
					return 3;
			case Z:
				if(params.getSliceNum() == 0)
					return 4;
				else
					return 5;
			default: return -1;
		}
	}
	
	private int[] findEffectedFaces() {
		switch(params.getAxis()) {
			case X:
				if(params.getDirection().equals(Direction.CW)) {
					int[] effectedX = { 5, 3, 4, 2 };
					return effectedX;
				}
				else {
					int[] effectedX = { 5, 2, 4, 3 };
					return effectedX;
				}
			case Y:
				if(params.getDirection().equals(Direction.CW)) {
					int[] effectedY = { 5, 0, 4, 1 };
					return effectedY;
				}
				else {
					int[] effectedY = { 5, 1, 4, 0 };
					return effectedY;
				}
			case Z:
				if(params.getDirection().equals(Direction.CW)) {
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
		return "("+params.getSliceNum()+", "+params.getAxis()+", "+params.getDirection()+")";
	}
}
