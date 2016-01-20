package rubiks;

class HeuristicCalculation
{
	static int calculate(RubiksCube rubiksCube, char defaultFace0Color, char defaultFace1Color, char defaultFace2Color,
			char defaultFace3Color, char defaultFace4Color, char defaultFace5Color) {
		if(rubiksCube.isSolved()) //we're at the goal state so 0!
			return 0;
		
		char cube[][][] = rubiksCube.getCube();
		int size = rubiksCube.getSize();
		
		//check for possible Y turns that could solve the cube
		if(faceSolved(cube[3]) && faceSolved(cube[2])) {
			int cond;
			if(cube[5][0][0] == defaultFace5Color)
				cond = size-1;
			else
				cond = 0;
			boolean allOrange = true;
			boolean allGreen = true;
			boolean allBlue = true;
			for(int i=0; i<cube[5].length; i++) { //row 
				for(int j=0; j<cube[5][i].length; j++) { //col
					if(i != cond) {
						if(cube[5][i][j] != defaultFace5Color)
							return 20;
					}
					else {
						if(cube[5][i][j] != defaultFace4Color)
							allOrange = false;
						if(cube[5][i][j] != defaultFace1Color)
							allBlue = false;
						if(cube[5][i][j] != defaultFace0Color)
							allGreen = false;
					}
				}
			}
			if(allOrange) { return 2; }
			else if(allBlue) { return 1; }
			else if(allGreen) { return 1; }
			else return 20;
		}
		
		//check for possible X turns that could solve the cube
		if(faceSolved(cube[0]) && faceSolved(cube[1])) {
			int cond;
			if(cube[5][0][0] == defaultFace5Color)
				cond = size-1;
			else
				cond = 0;
			boolean allOrange = true;
			boolean allYellow = true;
			boolean allWhite = true;
			for(int i=0; i<cube[5].length; i++) { //row 
				for(int j=0; j<cube[5][i].length; j++) { //col
					if(j != cond){
						if(cube[5][i][j] != defaultFace5Color)
							return 20;
					}
					else {
						if(cube[5][i][j] != defaultFace2Color)
							allYellow = false;
						if(cube[5][i][j] != defaultFace3Color)
							allWhite = false;
						if(cube[5][i][j] != defaultFace4Color)
							allOrange = false;
					}
				}
			}
			if(allOrange) { return 2; }
			else if(allYellow) { return 1; }
			else if(allWhite) { return 1; }
			else return 20;
		}
		
		//check for possible Z turns that could solve the cube
		if(faceSolved(cube[5]) && faceSolved(cube[4])) {
			int cond;
			if(cube[3][0][0] == defaultFace3Color)
				cond = size-1;
			else
				cond = 0;
			boolean allYellow = true;
			boolean allBlue = true;
			boolean allGreen = true;
			for(int i=0; i<cube[3].length; i++) { //row 
				for(int j=0; j<cube[3][i].length; j++) { //col
					if(i != cond) {
						if(cube[3][i][j] != defaultFace3Color)
							return 20;
					}
					else {
						if(cube[3][i][j] != defaultFace0Color)
							allGreen = false;
						if(cube[3][i][j] != defaultFace1Color)
							allBlue = false;
						if(cube[3][i][j] != defaultFace2Color)
							allYellow = false;
					}
				}
			}
			if(allYellow) { return 2; }
			else if(allBlue) { return 1; }
			else if(allGreen) { return 1; }
			else return 20;
		}
		
		//we dont recognize this cube! => assume maximum number of turns to solve
		else
			return 20;
	}
	
	private static boolean faceSolved(char[][] face) {
		char color = face[0][0];
		for(int i=0; i<face.length; i++) {
			for(int j=0; j<face[i].length; j++) {
				if(! (face[i][j] == color))
					return false;
			}
		}
		return true;
	}
}
