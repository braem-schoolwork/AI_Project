package rubiks;

import java.util.Arrays;
import java.util.Collections;

public class Move
{	
	/*
	 * METHOD FOR ALL MOVES
	 */
	public static void move(RubiksCube cube, int size, int sliceNum, Axis axis, Direction dir) {
		//checks
		if(size%2 == 1 && sliceNum == size/2)
			throw new IllegalMoveException("ATTEMPT TO MOVE MIDDLE SLICE OF AN ODD RUBIK'S CUBE");
		
		//initializations
		int from_face, to_face, from_row = -40, to_row, from_col = -40, to_col;
		char[][][] cubeArr = cube.getCube();
		char value_bfr[] = new char[size];
		int[] effectedFaces = findEffectedFaces(axis, dir);
		
		/* Dealing with edge cases */
		boolean isEdgeCase;
		if(sliceNum == 0 || sliceNum == size-1)
			isEdgeCase = true;
		else
			isEdgeCase = false;
		//if it is an edge case, rotate the respective face by the respective direction
		if(isEdgeCase) {
			int f = findEffectedEdgeFace(axis, sliceNum);
			rotate(cubeArr[f], dir);
		}
		
		for(int i=0, j=effectedFaces.length; i<effectedFaces.length && j>0 ;i++, j--) {
			/*set from_face and to_face based on direction*/
			from_face = effectedFaces[i];
			if(i == effectedFaces.length-1)
				to_face = effectedFaces[0];
			else
				to_face = effectedFaces[i+1];
			System.out.println(from_face+" "+to_face+" "+axis);
			
			/* Varies based on axis section */
			//store the values of the face in the value buffer if this
			//is the first face explored
			if(i==0 || j==effectedFaces.length){
				for(int k=0; k<size; k++)
					switch(axis) {
					case X:
						value_bfr[k] = cubeArr[from_face][k][sliceNum];
						break;
					case Y: 
						value_bfr[k] = cubeArr[from_face][size-1-sliceNum][k];
						break;
					case Z:
						value_bfr[k] = cubeArr[from_face][sliceNum][k];
						break;
					}
			}
					
			//get the faces values then move?
			
			
			//do the actual moving of rows/columns, juggling of characters, etc.
			for(int k=0, l=size; k<size && l>0; k++, l--) {
				switch(axis) {
				case X:
					from_row = k;
					from_col = sliceNum;
					to_col = from_col;
					//[even to even] OR [odd to odd]
					if((from_face%2==0 && to_face%2==0) || (from_face%2==1 && to_face%2==1)) {
						to_row = from_row;
					}
					else {
						to_row = size-1-from_row;
					}
					break;
				case Y: 
					from_row = size-1-sliceNum;
					from_col = k;
					to_row = from_row;
					if((from_face%2==0 && to_face%2==0) || (from_face%2==1 && to_face%2==1)) {
						to_col = from_col;
					}
					else {
						to_col = size-1-from_col;
					}
					break;
				case Z:
					if(from_face==3 || from_face==2) {
						from_row = sliceNum;
						from_col = k;
					}
					//from 1 to 2 switch columns
					//3 to 0 switch rows
					else { //1,0
						from_row = k;
						from_col = size-1-sliceNum;
					}
					if((from_face%2==0 && to_face%2==0) || (from_face%2==1 && to_face%2==1)) {
						to_row = from_col;
						to_col = size-1-from_row;
					}
					else {
						to_row = size-1-from_col;
						to_col = size-1-from_row;
					}
					break;
				default:
					from_row = -1;
					from_col = -1;
					to_row = -1;
					to_col = -1;
					break;
				}

				System.out.println(from_row+" "+from_col+" FROM "+axis+".. BUFFER: "+Arrays.toString(value_bfr));
				System.out.println(to_row+" "+to_col+" TO "+axis);
				
				char tmp = cubeArr[to_face][to_row][to_col]; //new to_face value
				cubeArr[to_face][to_row][to_col] = value_bfr[k]; //fill to_face value with from_face value
				value_bfr[k] = tmp; //write new value to buffer
				
			}//for moving of rows/columns
			
		}//for all effected faces
	}
	
	
	
	/*
	 * HELPER FUNCTIONS
	 */
	//rotates a two-dimensional array in a direction
	//used to implement edge cases
	private static void rotate(char[][] face, Direction dir) {
		int n = face.length;
		for(int i=0; i<=(n-1)/2; i++)
			for(int j=i; j<n-i-1; j++) {
				char tmp;
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
	private static int findEffectedEdgeFace(Axis axis, int sliceNum) {
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
	private static int[] findEffectedFaces(Axis axis, Direction dir) {
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
}
