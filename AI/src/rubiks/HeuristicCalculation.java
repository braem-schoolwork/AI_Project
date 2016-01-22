package rubiks;

class HeuristicCalculation
{
	static int calculate(RubiksCube rubiksCube) {
		
		char cube[][][] = rubiksCube.getCube();
		int size = rubiksCube.getSize();
			
		//TODO floating point heuristic
		
		
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
			int ctr = 0;
			for(int i=0; i<columnColors.length; i++) {
				if(i == columnColors.length-1 && columnColors[i] != columnColors[0])
					ctr++;
				else if(i != columnColors.length-1 && columnColors[i] != columnColors[i+1])
					ctr++;
			}
				
			if(ctr == 2) {
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
			for(int i=0; i<rowColors.length; i++) {
				if(i == rowColors.length-1 && rowColors[i] != rowColors[0])
					ctr++;
				else if(i != rowColors.length-1 && rowColors[i] != rowColors[i+1])
					ctr++;
			}
			if(ctr == 2) {
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
			for(int i=0; i<rowColors.length; i++) {
				if(i == rowColors.length-1 && rowColors[i] != rowColors[0])
					ctr++;
				else if(i != rowColors.length-1 && rowColors[i] != rowColors[i+1])
					ctr++;
			}
			if(ctr == 2) {
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
