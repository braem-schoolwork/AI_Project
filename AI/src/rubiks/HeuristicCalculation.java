package rubiks;

class HeuristicCalculation
{
	static int calculate(RubiksCube rubiksCube, char defaultFace0Color, char defaultFace1Color, char defaultFace2Color,
			char defaultFace3Color, char defaultFace4Color, char defaultFace5Color) {
		if(rubiksCube.isSolved()) //we're at the goal state so 0!
			return 0;
		
		char cube[][][] = rubiksCube.getCube();
		int size = rubiksCube.getSize();
			
		
		/*
		 * R W O
		 * R W O
		 * R W O
		 * 
		 * where theyre all bars. Can say needs only 2 moves to solve
		 */
		//check for possible X turns
		if(faceSolved(cube[0]) && faceSolved(cube[1])) {
			boolean isBars = true;
			char[] columnColors = new char[size];
			for(int i=0; i<cube[5].length; i++) { //row
				for(int j=0; j<cube[5][i].length; j++) { //column
					char current = cube[5][i][j];
					if(i==0) {
						columnColors[j] = current;
					}
					else {
						if(columnColors[j] != current)
							isBars = false;
					}
				}
			}
			//TODO make sure that the face has all same color minus one row/col
			int ctr = 0;
			for(int i=1; i<columnColors.length; i++)
				if(columnColors[i-1] != columnColors[i])
					ctr++;
			if(ctr == 1) {
				//get dominating color from columnColors 
				char dominatingColor = getDominatingColor(columnColors); 
				//if this color exists once in an adjacent face, only 1 turn needed
				for(int i=0; i<size; i++)
					if(cube[3][0][i] == dominatingColor)
						return 1;
				for(int i=0; i<size; i++)
					if(cube[2][0][i] == dominatingColor)
						return 1;
			}
			if(isBars) return size/2+1;
		}
		
		if(faceSolved(cube[3]) && faceSolved(cube[2])) {
			boolean isRows = true;
			char[] rowColors = new char[size];
			for(int i=0; i<cube[5].length; i++) { //row
				for(int j=0; j<cube[5][i].length; j++) { //column
					char current = cube[5][j][j];
					if(i==0) {
						rowColors[j] = current;
					}
					else {
						if(rowColors[j] != current)
							isRows = false;
					}
				}
			}
			int ctr = 0;
			for(int i=1; i<rowColors.length; i++)
				if(rowColors[i-1] != rowColors[i])
					ctr++;
			if(ctr == 1) {
				//get dominating color from columnColors 
				char dominatingColor = getDominatingColor(rowColors);
				//if this color exists once in an adjacent face, only 1 turn needed
				for(int i=0; i<size; i++)
					if(cube[0][i][0] == dominatingColor)
						return 1;
				for(int i=0; i<size; i++)
					if(cube[1][i][0] == dominatingColor)
						return 1;
			}
			if(isRows) return size/2+1;
		}
		
		if(faceSolved(cube[5]) && faceSolved(cube[4])) {
			boolean isRows = true;
			char[] rowColors = new char[size];
			for(int i=0; i<cube[3].length; i++) { //row
				for(int j=0; j<cube[3][i].length; j++) { //column
					char current = cube[3][j][j];
					if(i==0) {
						rowColors[j] = current;
					}
					else {
						if(rowColors[j] != current)
							isRows = false;
					}
				}
			}
			int ctr = 0;
			for(int i=1; i<rowColors.length; i++)
				if(rowColors[i-1] != rowColors[i])
					ctr++;
			if(ctr == 1) {
				//get dominating color from columnColors 
				char dominatingColor = getDominatingColor(rowColors);
				//if this color exists once in an adjacent face, only 1 turn needed
				for(int i=0; i<size; i++)
					if(cube[0][0][size-1-i] == dominatingColor)
						return 1;
				for(int i=0; i<size; i++)
					if(cube[1][0][size-1-i] == dominatingColor)
						return 1;
			}
			if(isRows) return size/2+1;
		}
		
		return 20;
		
		//check for possible Y turns that could solve the cube
		/*
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
			*/
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
	
	private static char getDominatingColor(char[] colors) {
		char dominatingColor = colors[0];
		int ctr = 0;
		for(int i=0; i<colors.length-1; i++) {
			char temp = colors[i];
			int tempCount = 0;
			for(int j=1; j<colors.length; j++) {
				if(temp == colors[j])
					tempCount++;
			}
			if(tempCount > ctr) {
				dominatingColor = temp;
				ctr = tempCount;
			}
		}
		return dominatingColor;
	}
}
