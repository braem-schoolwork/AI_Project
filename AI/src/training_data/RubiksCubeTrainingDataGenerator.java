package training_data;

import java.util.ArrayList;
import java.util.List;

import org.jblas.DoubleMatrix;

import rubiks.Direction;
import rubiks.Move;
import rubiks.MoveParams;
import rubiks.RubiksCube;

public class RubiksCubeTrainingDataGenerator
{
	public static List<String> genTrainingData(List<RubiksCube> cubes, List<Move> moves) {
		List<String> content = new ArrayList<String>();
		for(int i=0; i<cubes.size(); i++) {
			content.add(genCubeTrainingData(cubes.get(i)) +"|"+ genMoveTrainingData(moves.get(i)));
		}
		return content;
	}
	
	public static TrainingData fileContentsToTrainingData(List<String> trainingDataStrs) {
		List<TrainingTuple> tuples = new ArrayList<TrainingTuple>();
		for(String tupleStr : trainingDataStrs) {
			DoubleMatrix inputs = new DoubleMatrix(1, 36); //I/O vectors
			DoubleMatrix outputs = new DoubleMatrix(1, 7);
			String[] rCMoveTupleStrs = tupleStr.split("|"); //split RC from Move
			String rCStr = rCMoveTupleStrs[0]; //RC
			String moveStr = rCMoveTupleStrs[1]; //Move
			String[] rCStrs = rCStr.split(","); //get each RC value
			String[] moveStrs = moveStr.split(","); //get each Move value
			for(int i=0; i<inputs.columns; i++) { //put RC values into input vector
				String inputStr = rCStrs[i];
				double input = Double.parseDouble(inputStr);
				inputs.put(0, i, input);
			}
			for(int i=0; i<outputs.columns; i++) { //put Move values into output vector
				String outputStr = moveStrs[i];
				double output = Double.parseDouble(outputStr);
				outputs.put(0, i, output);
			}
			//add this training tuple to the data
			TrainingTuple trainingTuple = new TrainingTuple(inputs, outputs);
			tuples.add(trainingTuple);
		}
		return new TrainingData(tuples);
	}
	
	public static RubiksCube trainingDataToRubiksCube(TrainingData data) {
		return null;
	}
	
	private static String genCubeTrainingData(RubiksCube rubiksCube) {
		byte[][][] cube = rubiksCube.getCube();
		String rtnStr = "";
		for(int i=0; i<cube.length; i++) {
			for(int j=0; j<cube[i].length; j++) {
				for(int k=0; k<cube[i][j].length; k++) {
					rtnStr += colorToBits(cube[i][j][k]);
					if(k!=cube[i][j].length-1)
						rtnStr += ",";
				}
				if(j!=cube[i].length-1)
					rtnStr += ",";
			}
			if(i!=cube.length-1)
				rtnStr += ",";
		}
		return rtnStr;
	}
	
	private static String genMoveTrainingData(Move move) {
		MoveParams params = move.getMoveParams();
		String rtnStr = "";
		switch(params.getSliceNum()) {
		case 0: 
			rtnStr += "-1,-1,1"; break;
		case 1:
			rtnStr += "-1,1,-1"; break;
		case 2:
			rtnStr += "1,-1,-1"; break;
		}
		rtnStr += ",";
		switch(params.getAxis()) {
		case X:
			rtnStr += "-1,-1,1"; break;
		case Y:
			rtnStr += "-1,1,-1"; break;
		case Z:
			rtnStr += "1,-1,-1"; break;
		}
		rtnStr += ",";
		if(params.getDirection().equals(Direction.CW)) rtnStr += -1;
		else rtnStr += 1;
		return rtnStr;
	}
	
	private static String colorToBits(byte color) {
		switch(color) {
		case 'G':
			return "-1,-1,-1,-1,-1,1";
		case 'B':
			return "-1,-1,-1,-1,1,-1";
		case 'Y':
			return "-1,-1,-1,1,-1,-1";
		case 'W':
			return "-1,-1,1,-1,-1,-1";
		case 'O':
			return "-1,1,-1,-1,-1,-1";
		case 'R':
			return "1,-1,-1,-1,-1,-1";
		default: return null;
		}
	}
}
