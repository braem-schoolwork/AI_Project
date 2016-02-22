package training_data;

import java.util.Arrays;

import rubiks.RubiksCube;

public class RubiksCubeTrainingDataGenerator
{
	public static String genTrainingData(RubiksCube rubiksCube) {
		byte[][][] cube = rubiksCube.getCube();
		String rtnStr = "\n";
		for(int i=0; i<cube.length; i++) {
			for(int j=0; j<cube[i].length; j++) {
				for(int k=0; k<cube[i][j].length; k++) {
					rtnStr += colorToBits(cube[i][j][k]);
					if(k!=cube[i][j].length-1)
						rtnStr += ".";
				}
				rtnStr += ".";
			}
			if(i!=cube.length-1)
				rtnStr += "\n";
		}
		return rtnStr;
	}
	
	private static String colorToBits(byte color) {
		switch(color) {
		case 'G':
			byte[] GcolorStream = { -1,-1,-1,-1,-1,1 };
			return Arrays.toString(GcolorStream);
		case 'B':
			byte[] BcolorStream = { -1,-1,-1,-1,1,-1 };
			return Arrays.toString(BcolorStream);
		case 'Y':
			byte[] YcolorStream = { -1,-1,-1,1,-1,-1 };
			return Arrays.toString(YcolorStream);
		case 'W':
			byte[] WcolorStream = { -1,-1,1,-1,-1,-1 };
			return Arrays.toString(WcolorStream);
		case 'O':
			byte[] OcolorStream = { -1,1,-1,-1,-1,-1 };
			return Arrays.toString(OcolorStream);
		case 'R':
			byte[] RcolorStream = { 1,-1,-1,-1,-1,-1 };
			return Arrays.toString(RcolorStream);
		default: return null;
		}
	}
}
