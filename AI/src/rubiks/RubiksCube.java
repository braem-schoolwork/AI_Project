package rubiks;

//Directions: CW and CCW
//X, Y, Z axis
//0..S-1 slices per axis of rotation
//which means numMovesPossible = #axis * #directions * size;


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
public class RubiksCube implements Searchable
{
	private int size; //size=2 when dealing 2x2x2 cube & vice versa
	//representations of cube
	private char[] cubeStr;
	private int numMovesPossible; //cap on the amount of moves possible
	
	public RubiksCube(int size) {
		this.size = size;
		cubeStr = createSolvedCube();
		numMovesPossible = 3*2*size;
	}

	//GGGGRRRRWWWWYYYYBBBBOOOO
	private char[] createSolvedCube() {
		char[] cubeStr = new char[size*size*6];
		for(int i=0; i<6; i++) {
			char color = ' ';
			switch(i) {
			case 0: color = 'G'; break;
			case 1: color = 'R'; break;
			case 2: color = 'W'; break;
			case 3: color = 'Y'; break;
			case 4: color = 'B'; break;
			case 5: color = 'O'; break;
			}
			for(int j=0; j<size*size; j++) {
				cubeStr[j] = color;
			}
		}
		return cubeStr;
	}
	
	public boolean isSolved() {
		char[] cubeCopy = cubeStr;
		char[] testStr = new char[size*size*6];
		int i = 0;
		while() { //while(cube isnt empty)
			System.out.println(cubeCopy);
			testStr = cubeCopy.substring(0, size*size);
			System.out.println(testStr);
			char tmp = cubeCopy.charAt(0);
			for(int j=1; j<size*size; j++)
				if(tmp != testStr.charAt(j))
					return false;
			cubeCopy = cubeCopy.substring(size*size, cubeCopy.length());
		}
		return true;
	}
	
	public char[] getCube() {
		return cubeStr;
	}
}
