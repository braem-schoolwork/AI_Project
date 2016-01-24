package rubiks;

import java.util.Arrays;

public class HeuristicCalculation
{
	static float calculate(RubiksCube rubiksCube) {
		
		char cube[][][] = rubiksCube.getCube();
		int size = rubiksCube.getSize();
			
		float edgeManhattanDist3D = calcEdgeManhattan3DDistance(cube, size);
		float cornerManhattanDist3D = calcCornerManhattan3DDistance(cube, size);
		
		//divide each by 4
		cornerManhattanDist3D /= 4;
		edgeManhattanDist3D /= 4;
		
		//take the maximum of corner and edge 3D manhattan distances
		float manhattanDistance3D;
		if(cornerManhattanDist3D <= edgeManhattanDist3D)
			manhattanDistance3D = edgeManhattanDist3D;
		else
			manhattanDistance3D = cornerManhattanDist3D;
		
		//return
		return manhattanDistance3D;
	}
	
	private static float calcEdgeManhattan3DDistance(char[][][] cube, int size) {
		float edgeManhattanDist3D = 0;
		
		/* store the default values of these edges */
		//top layer
		char[] defaultTopFrontEdgeColor = { 'R', 'W' };
		char[] defaultTopLeftEdgeColor = { 'G', 'W' };
		char[] defaultTopRightEdgeColor = { 'B', 'W' };
		char[] defaultTopBackEdgeColor = { 'O', 'W' };
		
		//middle layer
		char[] defaultMidRightFrontEdgeColor = { 'B', 'R' };
		char[] defaultMidLeftFrontEdgeColor = { 'G', 'R' };
		char[] defaultMidRightBackEdgeColor = { 'B', 'O' };
		char[] defaultMidLeftBackEdgeColor = { 'G', 'O' };
		
		//bottom layer
		char[] defaultBotFrontEdgeColor = { 'R', 'Y' };
		char[] defaultBotLeftEdgeColor = { 'G', 'Y' };
		char[] defaultBotRightEdgeColor = { 'B', 'Y' };
		char[] defaultBotBackEdgeColor = { 'O', 'Y' };
		
		//put these in an array
		char[][] defaultEdgeValues = { defaultTopFrontEdgeColor, defaultTopLeftEdgeColor, defaultTopRightEdgeColor, defaultTopBackEdgeColor,
				defaultMidRightFrontEdgeColor, defaultMidLeftFrontEdgeColor, defaultMidRightBackEdgeColor, defaultMidLeftBackEdgeColor,
				defaultBotFrontEdgeColor, defaultBotLeftEdgeColor, defaultBotRightEdgeColor, defaultBotBackEdgeColor };
		
		/* get the actual values of the edges */
		char[] actualTopFrontEdgeColor = { cube[5][0][size/2], cube[3][size-1][size/2] };
		Arrays.sort(actualTopFrontEdgeColor);
		char[] actualTopLeftEdgeColor = { cube[3][size/2][0], cube[0][0][size/2] };
		Arrays.sort(actualTopLeftEdgeColor);
		char[] actualTopRightEdgeColor = { cube[3][size/2][size-1], cube[1][0][size/2] };
		Arrays.sort(actualTopRightEdgeColor);
		char[] actualTopBackEdgeColor = { cube[3][0][size/2], cube[4][0][size/2] };
		Arrays.sort(actualTopBackEdgeColor);
		
		//middle layer
		char[] actualMidRightFrontEdgeColor = { cube[5][size/2][size-1], cube[1][size/2][0] };
		Arrays.sort(actualMidRightFrontEdgeColor);
		char[] actualMidLeftFrontEdgeColor = { cube[5][size/2][0], cube[0][size/2][0] };
		Arrays.sort(actualMidLeftFrontEdgeColor);
		char[] actualMidRightBackEdgeColor = { cube[4][size/2][size-1], cube[1][size/2][size-1] };
		Arrays.sort(actualMidRightBackEdgeColor);
		char[] actualMidLeftBackEdgeColor = { cube[4][size/2][0], cube[0][size/2][size-1] };
		Arrays.sort(actualMidLeftBackEdgeColor);
		
		//bottom layer
		char[] actualBotFrontEdgeColor = { cube[5][size-1][size/2], cube[2][size-1][size/2] };
		Arrays.sort(actualBotFrontEdgeColor);
		char[] actualBotLeftEdgeColor = { cube[2][size/2][0], cube[0][size-1][size/2] };
		Arrays.sort(actualBotLeftEdgeColor);
		char[] actualBotRightEdgeColor = { cube[2][size/2][size-1], cube[1][size-1][size/2] };
		Arrays.sort(actualBotRightEdgeColor);
		char[] actualBotBackEdgeColor = { cube[2][0][size/2], cube[4][size-1][size/2] };
		Arrays.sort(actualBotBackEdgeColor);
		
		//put these in array like above
		char[][] actualEdgeValues = { actualTopFrontEdgeColor, actualTopLeftEdgeColor, actualTopRightEdgeColor, actualTopBackEdgeColor,
				actualMidRightFrontEdgeColor, actualMidLeftFrontEdgeColor, actualMidRightBackEdgeColor, actualMidLeftBackEdgeColor,
				actualBotFrontEdgeColor, actualBotLeftEdgeColor, actualBotRightEdgeColor, actualBotBackEdgeColor };
		
		/* find out which edges should go to where */
		int[] actualEdgeIndexes = new int[actualEdgeValues.length];
		for(int i=0; i<defaultEdgeValues.length; i++) {
			for(int j=0; j<actualEdgeValues.length; j++) {
				if(Arrays.equals(defaultEdgeValues[i], actualEdgeValues[j])) {
					actualEdgeIndexes[i] = j;
				}
			}
		} 
		
		/* 
		 * 0 TopFront			[1,2,2]
		 * 1 TopLeft			[0,2,1]
		 * 2 TopRight			[2,2,1]
		 * 3 TopBack			[1,2,0]
		 * 4 MidRightFront		[2,1,2]
		 * 5 MidLeftFront		[0,1,2]
		 * 6 MidRightBack		[2,1,0]
		 * 7 MidLeftBack		[0,1,0]
		 * 8 BotFront			[1,0,2]
		 * 9 BotLeft			[0,0,1]
		 * 10 BotRight			[2,0,1]
		 * 11 BotBack			[1,0,0]
		 * */
		
		/* create a pseudo 3D grid where indexes correspond to the edges */
		int[][] to_gridValues = { {size/2,size-1,size-1}, {0,size-1,size/2}, {size-1,size-1,size/2}, {size/2,size-1,0},
				{size-1,size/2,size-1}, {0,size/2,size-1}, {size-1,size/2,0}, {0,size/2,0},
				{size/2,0,size-1}, {0,0,size/2}, {size-1,0,size/2}, {size/2,0,0} };
		int[][] from_gridValues = new int[actualEdgeValues.length][3];
		for(int i=0; i<to_gridValues.length; i++) {
			from_gridValues[i] = to_gridValues[actualEdgeIndexes[i]];
		}
		
		/* sum up the 3D manhattan distance of the edges */
		for(int i=0; i<actualEdgeValues.length; i++) {
			int from_x = -1;
			int from_y = -1;
			int from_z = -1;
			
			int to_x = -1;
			int to_y = -1;
			int to_z = -1;
			for(int j=0; j<3; j++) {
				from_x = from_gridValues[i][0];
				from_y = from_gridValues[i][1];
				from_z = from_gridValues[i][2];
				
				to_x = to_gridValues[i][0];
				to_y = to_gridValues[i][1];
				to_z = to_gridValues[i][2];
			}
			edgeManhattanDist3D += manhattanDistance3D(from_x, to_x, from_y, to_y, from_z, to_z);
		}
		
		return edgeManhattanDist3D;
	}
	
	private static float calcCornerManhattan3DDistance(char[][][] cube, int size) {
		float cornerManhattanDist3D = 0;
		
		/* store the default values of these corners */
		//front corners (closest to observer)
		char[] defFrontTopRightCornerColor = { 'B', 'R', 'W' };
		char[] defFrontTopLeftCornerColor = { 'G', 'R', 'W' };
		char[] defFrontBotRightCornerColor = { 'B', 'R', 'Y' };
		char[] defFrontBotLeftCornerColor = { 'G', 'R', 'Y' };
		//back corners (furthest from observer)
		char[] defBackTopRightCornerColor = { 'B', 'O', 'W' };
		char[] defBackTopLeftCornerColor = { 'G', 'O', 'W' };
		char[] defBackBotRightCornerColor = { 'B', 'O', 'Y' };
		char[] defBackBotLeftCornerColor = { 'G', 'O', 'Y' };
		//put them in an array
		char[][] defaultCornerValues = { defFrontTopRightCornerColor, defFrontTopLeftCornerColor, defFrontBotRightCornerColor, defFrontBotLeftCornerColor,
				defBackTopRightCornerColor, defBackTopLeftCornerColor, defBackBotRightCornerColor, defBackBotLeftCornerColor };
		
		/* get actual corner values. Sort them to test for corner equality */
		char[] actualFrontTopRightCornerColor = { cube[5][0][size-1], cube[1][0][0], cube[3][size-1][size-1] };
		Arrays.sort(actualFrontTopRightCornerColor);
		char[] actualFrontTopLeftCornerColor = { cube[5][0][0], cube[0][0][0], cube[3][size-1][0] };
		Arrays.sort(actualFrontTopLeftCornerColor);
		char[] actualFrontBotRightCornerColor = { cube[5][size-1][size-1], cube[1][size-1][0], cube[2][size-1][size-1] };
		Arrays.sort(actualFrontBotRightCornerColor);
		char[] actualFrontBotLeftCornerColor = { cube[5][size-1][0], cube[0][size-1][0], cube[2][size-1][0] };
		Arrays.sort(actualFrontBotLeftCornerColor);
		char[] actualBackTopRightCornerColor = { cube[4][0][size-1], cube[1][0][size-1], cube[3][0][size-1] };
		Arrays.sort(actualBackTopRightCornerColor);
		char[] actualBackTopLeftCornerColor = { cube[4][0][0], cube[0][0][size-1], cube[3][0][0] };
		Arrays.sort(actualBackTopLeftCornerColor);
		char[] actualBackBotRightCornerColor = { cube[4][size-1][size-1], cube[1][size-1][size-1], cube[2][0][size-1] };
		Arrays.sort(actualBackBotRightCornerColor);
		char[] actualBackBotLeftCornerColor = { cube[4][size-1][0], cube[0][size-1][size-1], cube[2][0][0] };
		Arrays.sort(actualBackBotLeftCornerColor);
		//put them in an array like above
		char[][] actualCornerValues = { actualFrontTopRightCornerColor, actualFrontTopLeftCornerColor, actualFrontBotRightCornerColor, actualFrontBotLeftCornerColor,
				actualBackTopRightCornerColor, actualBackTopLeftCornerColor, actualBackBotRightCornerColor, actualBackBotLeftCornerColor };
		
		/* 
		 * 0 frontTopRight
		 * 1 frontTopLeft
		 * 2 frontBotRight
		 * 3 frontBotLeft
		 * 4 backTopRight
		 * 5 backTopLeft
		 * 6 backBotRight
		 * 7 backBotLeft
		 * */
		
		/* find out which corner should go to where */
		int[] actualCornerIndexes = new int[actualCornerValues.length];
		for(int i=0; i<defaultCornerValues.length; i++) {
			for(int j=0; j<actualCornerValues.length; j++) {
				if(Arrays.equals(defaultCornerValues[i], actualCornerValues[j])) {
					actualCornerIndexes[i] = j;
				}
			}
		} 
		
		/* create a pseudo 3D grid where indexes correspond to the corners */
		int[][] to_gridValues = { {size-1,size-1,size-1}, {0,size-1,size-1}, {size-1,0,size-1}, {0,0,size-1},
				{size-1,size-1,0}, {0,size-1,0}, {size-1,0,0}, {0,0,0} };
		int[][] from_gridValues = new int[actualCornerValues.length][3];
		for(int i=0; i<to_gridValues.length; i++) {
			from_gridValues[i] = to_gridValues[actualCornerIndexes[i]];
		}
		
		/* sum up the 3D manhattan distance of the corners */
		for(int i=0; i<actualCornerValues.length; i++) {
			int from_x = -1;
			int from_y = -1;
			int from_z = -1;
			
			int to_x = -1;
			int to_y = -1;
			int to_z = -1;
			for(int j=0; j<3; j++) {
				from_x = from_gridValues[i][0];
				from_y = from_gridValues[i][1];
				from_z = from_gridValues[i][2];
				
				to_x = to_gridValues[i][0];
				to_y = to_gridValues[i][1];
				to_z = to_gridValues[i][2];
			}
			cornerManhattanDist3D += manhattanDistance3D(from_x, to_x, from_y, to_y, from_z, to_z);
		}
		return cornerManhattanDist3D;
	}
	
	//1 == from, 2 == to
	private static int manhattanDistance3D(int x1, int x2, int y1, int y2, int z1, int z2) {
		return Math.abs(x2-x1) + Math.abs(y2-y1) + Math.abs(z2-z1);
	}
	
}
