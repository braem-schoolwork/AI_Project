package rubiks;

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
		
	}
}
