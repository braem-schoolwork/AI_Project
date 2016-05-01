package rubiks;

import java.util.Arrays;

/**
 * static class to calculate a rubik's cube heuristic that 
 * uses 3D Manhattan Distance.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
class HeuristicCalculation
{
	private static float magicDivideByNumber = 8;
	
	/**
	 * Calculates the heuristic value.
	 *
	 * @param rubiksCube		rubik's cube
	 * @return					heuristic value of rubik's cube
	 */
	static float calculate(RubiksCube rubiksCube) {
		byte 	cube[][][] 	= rubiksCube.getCube();
		int 	size 		= rubiksCube.getSize();
			
		float 	edgeManhattanDist3D = 0;
		if(size > 2)
			edgeManhattanDist3D = calcEdgeManhattan3DDistance(cube, size);
		float 	cornerManhattanDist3D = calcCornerManhattan3DDistance(cube, size);
		
		float manhattanDistance3D;
		if(cornerManhattanDist3D <= edgeManhattanDist3D)
			manhattanDistance3D = edgeManhattanDist3D;
		else
			manhattanDistance3D = cornerManhattanDist3D;

		return manhattanDistance3D;
	}
	
	private static float calcEdgeManhattan3DDistance(byte[][][] cube, int size) {
		float edgeManhattanDist3D = 0;
		
		/* store the default values of these edges */
		byte[] defaultTopFrontEdgeColor 		= { 'R', 'W' };
		byte[] defaultTopLeftEdgeColor 			= { 'G', 'W' };
		byte[] defaultTopRightEdgeColor 		= { 'B', 'W' };
		byte[] defaultTopBackEdgeColor 			= { 'O', 'W' };

		byte[] defaultMidRightFrontEdgeColor 	= { 'B', 'R' };
		byte[] defaultMidLeftFrontEdgeColor 	= { 'G', 'R' };
		byte[] defaultMidRightBackEdgeColor 	= { 'B', 'O' };
		byte[] defaultMidLeftBackEdgeColor 		= { 'G', 'O' };

		byte[] defaultBotFrontEdgeColor 		= { 'R', 'Y' };
		byte[] defaultBotLeftEdgeColor 			= { 'G', 'Y' };
		byte[] defaultBotRightEdgeColor 		= { 'B', 'Y' };
		byte[] defaultBotBackEdgeColor 			= { 'O', 'Y' };
		
		byte[][] 	defaultEdgeValues 	= { defaultTopFrontEdgeColor, defaultTopLeftEdgeColor, defaultTopRightEdgeColor, defaultTopBackEdgeColor,
				defaultMidRightFrontEdgeColor, defaultMidLeftFrontEdgeColor, defaultMidRightBackEdgeColor, defaultMidLeftBackEdgeColor,
				defaultBotFrontEdgeColor, defaultBotLeftEdgeColor, defaultBotRightEdgeColor, defaultBotBackEdgeColor };
		
		/* get the actual values of the edges */
		byte[] actualTopFrontEdgeColor 			= { cube[5][0][size/2], cube[3][size-1][size/2] };
		Arrays.sort(actualTopFrontEdgeColor);
		byte[] actualTopLeftEdgeColor 			= { cube[3][size/2][0], cube[0][0][size/2] };
		Arrays.sort(actualTopLeftEdgeColor);
		byte[] actualTopRightEdgeColor 			= { cube[3][size/2][size-1], cube[1][0][size/2] };
		Arrays.sort(actualTopRightEdgeColor);
		byte[] actualTopBackEdgeColor 			= { cube[3][0][size/2], cube[4][0][size/2] };
		Arrays.sort(actualTopBackEdgeColor);

		byte[] actualMidRightFrontEdgeColor 	= { cube[5][size/2][size-1], cube[1][size/2][0] };
		Arrays.sort(actualMidRightFrontEdgeColor);
		byte[] actualMidLeftFrontEdgeColor 		= { cube[5][size/2][0], cube[0][size/2][0] };
		Arrays.sort(actualMidLeftFrontEdgeColor);
		byte[] actualMidRightBackEdgeColor 		= { cube[4][size/2][size-1], cube[1][size/2][size-1] };
		Arrays.sort(actualMidRightBackEdgeColor);
		byte[] actualMidLeftBackEdgeColor 		= { cube[4][size/2][0], cube[0][size/2][size-1] };
		Arrays.sort(actualMidLeftBackEdgeColor);
		
		byte[] actualBotFrontEdgeColor 			= { cube[5][size-1][size/2], cube[2][size-1][size/2] };
		Arrays.sort(actualBotFrontEdgeColor);
		byte[] actualBotLeftEdgeColor 			= { cube[2][size/2][0], cube[0][size-1][size/2] };
		Arrays.sort(actualBotLeftEdgeColor);
		byte[] actualBotRightEdgeColor 			= { cube[2][size/2][size-1], cube[1][size-1][size/2] };
		Arrays.sort(actualBotRightEdgeColor);
		byte[] actualBotBackEdgeColor 			= { cube[2][0][size/2], cube[4][size-1][size/2] };
		Arrays.sort(actualBotBackEdgeColor);
		
		byte[][] 	actualEdgeValues 	= { actualTopFrontEdgeColor, actualTopLeftEdgeColor, actualTopRightEdgeColor, actualTopBackEdgeColor,
				actualMidRightFrontEdgeColor, actualMidLeftFrontEdgeColor, actualMidRightBackEdgeColor, actualMidLeftBackEdgeColor,
				actualBotFrontEdgeColor, actualBotLeftEdgeColor, actualBotRightEdgeColor, actualBotBackEdgeColor };
		
		/* find out which edges should go to where */
		int[] 		actualEdgeIndexes 	= new int[actualEdgeValues.length];
		for(int i=0; i<defaultEdgeValues.length; i++)
			for(int j=0; j<actualEdgeValues.length; j++)
				if(Arrays.equals(defaultEdgeValues[i], actualEdgeValues[j]))
					actualEdgeIndexes[i] = j;

		/* create a pseudo 3D grid where indexes correspond to the edges */
		int[][] 	to_gridValues 		= { {size/2,size-1,size-1}, {0,size-1,size/2}, {size-1,size-1,size/2}, {size/2,size-1,0},
				{size-1,size/2,size-1}, {0,size/2,size-1}, {size-1,size/2,0}, {0,size/2,0},
				{size/2,0,size-1}, {0,0,size/2}, {size-1,0,size/2}, {size/2,0,0} };
		int[][] 	from_gridValues 	= new int[actualEdgeValues.length][3];
		for(int i=0; i<to_gridValues.length; i++)
			from_gridValues[i] = to_gridValues[actualEdgeIndexes[i]];
		
		/* sum up the 3D manhattan distance of the edges */
		for(int i=0; i<actualEdgeValues.length; i++) {
			int from_x 	= -1;
			int from_y 	= -1;
			int from_z 	= -1;
			
			int to_x 	= -1;
			int to_y 	= -1;
			int to_z 	= -1;
			for(int j=0; j<3; j++) {
				from_x 	= from_gridValues[i][0];
				from_y 	= from_gridValues[i][1];
				from_z 	= from_gridValues[i][2];
				
				to_x 	= to_gridValues[i][0];
				to_y 	= to_gridValues[i][1];
				to_z 	= to_gridValues[i][2];
			}
			edgeManhattanDist3D += manhattanDistance3D(from_x, to_x, from_y, to_y, from_z, to_z);
		}
		return edgeManhattanDist3D/magicDivideByNumber;
	}
	
	private static float calcCornerManhattan3DDistance(byte[][][] cube, int size) {
		float cornerManhattanDist3D = 0;
		
		/* store the default values of these corners */
		byte[] defFrontTopRightCornerColor 		= { 'B', 'R', 'W' };
		byte[] defFrontTopLeftCornerColor 		= { 'G', 'R', 'W' };
		byte[] defFrontBotRightCornerColor 		= { 'B', 'R', 'Y' };
		byte[] defFrontBotLeftCornerColor 		= { 'G', 'R', 'Y' };
		
		byte[] defBackTopRightCornerColor 		= { 'B', 'O', 'W' };
		byte[] defBackTopLeftCornerColor 		= { 'G', 'O', 'W' };
		byte[] defBackBotRightCornerColor 		= { 'B', 'O', 'Y' };
		byte[] defBackBotLeftCornerColor 		= { 'G', 'O', 'Y' };
		byte[][] defaultCornerValues = { defFrontTopRightCornerColor, defFrontTopLeftCornerColor, defFrontBotRightCornerColor, defFrontBotLeftCornerColor,
				defBackTopRightCornerColor, defBackTopLeftCornerColor, defBackBotRightCornerColor, defBackBotLeftCornerColor };
		
		/* get actual corner values. Sort them to test for corner equality */
		byte[] actualFrontTopRightCornerColor 	= { cube[5][0][size-1], cube[1][0][0], cube[3][size-1][size-1] };
		Arrays.sort(actualFrontTopRightCornerColor);
		byte[] actualFrontTopLeftCornerColor 	= { cube[5][0][0], cube[0][0][0], cube[3][size-1][0] };
		Arrays.sort(actualFrontTopLeftCornerColor);
		byte[] actualFrontBotRightCornerColor 	= { cube[5][size-1][size-1], cube[1][size-1][0], cube[2][size-1][size-1] };
		Arrays.sort(actualFrontBotRightCornerColor);
		byte[] actualFrontBotLeftCornerColor 	= { cube[5][size-1][0], cube[0][size-1][0], cube[2][size-1][0] };
		Arrays.sort(actualFrontBotLeftCornerColor);
		byte[] actualBackTopRightCornerColor 	= { cube[4][0][size-1], cube[1][0][size-1], cube[3][0][size-1] };
		Arrays.sort(actualBackTopRightCornerColor);
		byte[] actualBackTopLeftCornerColor 	= { cube[4][0][0], cube[0][0][size-1], cube[3][0][0] };
		Arrays.sort(actualBackTopLeftCornerColor);
		byte[] actualBackBotRightCornerColor 	= { cube[4][size-1][size-1], cube[1][size-1][size-1], cube[2][0][size-1] };
		Arrays.sort(actualBackBotRightCornerColor);
		byte[] actualBackBotLeftCornerColor 	= { cube[4][size-1][0], cube[0][size-1][size-1], cube[2][0][0] };
		Arrays.sort(actualBackBotLeftCornerColor);
		byte[][] actualCornerValues = { actualFrontTopRightCornerColor, actualFrontTopLeftCornerColor, actualFrontBotRightCornerColor, actualFrontBotLeftCornerColor,
				actualBackTopRightCornerColor, actualBackTopLeftCornerColor, actualBackBotRightCornerColor, actualBackBotLeftCornerColor };

		/* find out which corner should go to where */
		int[] actualCornerIndexes = new int[actualCornerValues.length];
		for(int i=0; i<defaultCornerValues.length; i++)
			for(int j=0; j<actualCornerValues.length; j++)
				if(Arrays.equals(defaultCornerValues[i], actualCornerValues[j]))
					actualCornerIndexes[i] = j;
		
		/* create a pseudo 3D grid where indexes correspond to the corners */
		int[][] to_gridValues = { {size-1,size-1,size-1}, {0,size-1,size-1}, {size-1,0,size-1}, {0,0,size-1},
				{size-1,size-1,0}, {0,size-1,0}, {size-1,0,0}, {0,0,0} };
		int[][] from_gridValues = new int[actualCornerValues.length][3];
		for(int i=0; i<to_gridValues.length; i++) {
			from_gridValues[i] = to_gridValues[actualCornerIndexes[i]];
		}
		
		/* sum up the 3D manhattan distance of the corners */
		for(int i=0; i<actualCornerValues.length; i++) {
			int from_x 	= -1;
			int from_y 	= -1;
			int from_z 	= -1;
			
			int to_x 	= -1;
			int to_y 	= -1;
			int to_z 	= -1;
			for(int j=0; j<3; j++) {
				from_x 	= from_gridValues[i][0];
				from_y 	= from_gridValues[i][1];
				from_z 	= from_gridValues[i][2];
				
				to_x 	= to_gridValues[i][0];
				to_y 	= to_gridValues[i][1];
				to_z 	= to_gridValues[i][2];
			}
			cornerManhattanDist3D += manhattanDistance3D(from_x, to_x, from_y, to_y, from_z, to_z);
		}
		return cornerManhattanDist3D/magicDivideByNumber;
	}
	
	private static int manhattanDistance3D(int x1, int x2, int y1, int y2, int z1, int z2) {
		return Math.abs(x2-x1) + Math.abs(y2-y1) + Math.abs(z2-z1);
	}
	
}
