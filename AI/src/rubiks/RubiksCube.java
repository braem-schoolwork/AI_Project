package rubiks;

//Directions: CW and CCW
//X, Y, Z axis
//0..S-1 slices per axis of rotation
//which means numMovesPossible = #axis * #directions * size;

public class RubiksCube implements Searchable
{
	private int size; //size=2 when dealing 2x2x2 cube & vice versa
	private String cube;
	private int numMovesPossible;
	
	public RubiksCube(int size) {
		this.size = size;
		cube = createSolvedCube();
		numMovesPossible = 3*2*size;
	}

	//GGGGRRRRWWWWYYYYBBBBOOOO
	private String createSolvedCube() {
		String cubeStr = "";
		for(int i=0; i<6; i++) {
			String color = "";
			switch(i) {
			case 0: color = "G"; break;
			case 1: color = "R"; break;
			case 2: color = "W"; break;
			case 3: color = "Y"; break;
			case 4: color = "B"; break;
			case 5: color = "O"; break;
			}
			for(int j=0; j<size*size; j++) {
				cubeStr += color;
			}
		}
		return cubeStr;
	}
	
	public boolean isSolved() {
		String cubeCopy = cube;
		String testStr = "";
		int i = 0;
		while(!cubeCopy.isEmpty()) {
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
	
	public String getCube() {
		return cube;
	}
}
