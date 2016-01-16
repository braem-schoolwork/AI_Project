package rubiks;

import java.util.Arrays;

public class Move
{
	public static void move(RubiksCube cube, int size, int sliceNum, Axis axis, Direction dir) {
		//checks
		if(size%2 == 1 && sliceNum == size/2)
			throw new IllegalMoveException("ATTEMPT TO MOVE MIDDLE SLICE OF AN ODD RUBIK'S CUBE");
		
		switch(axis) {
		case X: RotX(cube, size, sliceNum, dir); break;
		
		}
	}
	
	private static void RotX(RubiksCube cube, int size, int sliceNum, Direction dir) {
		int from_face, to_face, from_row, to_row, from_col, to_col;
		
		//TODO deal with edge cases
		boolean isEdgeCase;
		if(sliceNum == 0 || sliceNum == size-1)
			isEdgeCase = true;
		else
			isEdgeCase = false;
		
		char[][][] cubeArr = cube.getCube();
		char value_bfr[] = new char[size];
		int[] effectedFaces = findEffectedFaces(Axis.X);
		for(int i=0, j=effectedFaces.length; i<effectedFaces.length && j>0 ;i++, j--) {
			//set from_face and to_face
			from_face = effectedFaces[i];
			
			if(dir.equals(Direction.CW))
				if(i == effectedFaces.length-1)
					to_face = effectedFaces[0];
				else
					to_face = effectedFaces[i+1];
			else {
				if(j == 0)
					to_face = effectedFaces[effectedFaces.length-1];
				else
					to_face = effectedFaces[j-1];
			}
			//store the values of the face in the value buffer if this
			//is the first face explored
			if(i==0)
				for(int k=0; k<size; k++)
					value_bfr[k] = cubeArr[from_face][k][sliceNum];
			
			//do the actual moving of rows/columns, juggling of characters, etc.
			for(int k=0; k<size; k++) {
				from_row = k;
				from_col = sliceNum;
				if(from_face%2==to_face%2) { //[even to odd] or [odd to even]
					to_row = from_row;
					to_col = from_col;
				}
				else { //[even to even] or [odd to odd]
					to_row = size-1-from_row;
					to_col = from_col;
				}
				//write the new values to cube
				char tmp = cubeArr[to_face][to_row][to_col]; //new to_face value
				cubeArr[to_face][to_row][to_col] = value_bfr[k]; //fill from_face value in
				value_bfr[k] = tmp; //write new value to buffer
			}
		}
	}
	
	public static void rotate(char[][] face, Direction dir) {
		int n = face.length;
		for(int i=0; i<=(n-1)/2; i++)
			for(int j=i; j<n-i-1; j++) {
				char tmp;
				if(dir.equals(Direction.CW)) {
					tmp = face[i][j];
					face[i][j] = face[n-j-1][i];
					face[n-i-1][n-j-1] = face[j][n-i-1];
					face[n-j-1][i] = face[n-i-1][n-j-1];
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
	
	private static int[] findEffectedFaces(Axis axis) {
		switch(axis) {
		case X:
			int[] effectedX = { 5, 3, 4, 2 };
			return effectedX;
		case Y:
			int[] effectedY = { 0, 5, 1, 4 };
			return effectedY;
		case Z:
			int[] effectedZ = { 0, 3, 1, 2 };
			return effectedZ;
		default: return null;
		}
	}
	
	private static void reverse(int[] arr) {
		for(int i=0; i<arr.length; i++) {
			int temp = arr[i];
			arr[i] = arr[arr.length -1 - i];
			arr[arr.length - 1 - i] = temp;
		}
	}
}
