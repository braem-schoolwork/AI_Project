package training_data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.jblas.DoubleMatrix;

import rubiks.Axis;
import rubiks.Direction;
import rubiks.Move;
import rubiks.MoveParams;
import rubiks.RubiksCube;

/**
 * Generator for rubik's cube related training data.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class RubiksCubeTrainingDataGenerator
{
	/**
	 * Generates file training data from rubik's cubes and moves.
	 * 
	 * @param cubes			list of rubik's cubes
	 * @param moves			list of moves corresponding to each rubik's cube
	 * @return				file contents
	 */
	public static List<String> genFileTrainingData(List<RubiksCube> cubes, List<Move> moves) {
		List<String> 	content 	= new ArrayList<String>();
		HashSet<String> contentSet 	= new HashSet<String>();
		
		for(int i=0; i<cubes.size(); i++)
			content.add(genCubeTrainingData(cubes.get(i)) +"|"+ genMoveTrainingData(moves.get(i)));
		contentSet.addAll(content);
		content.clear();
		content.addAll(contentSet);
		return content;
	}
	
	/**
	 * Generates a TrainingData object from a files contents.
	 * 
	 * @param trainingDataStrs			file contents
	 * @return							a corresponding training data set from the file contents
	 */
	public static TrainingData fileContentsToTrainingData(List<String> trainingDataStrs) {
		List<TrainingTuple> tuples = new ArrayList<TrainingTuple>();
		for(String tupleStr : trainingDataStrs) {
			DoubleMatrix 	inputs 			= new DoubleMatrix(1, 36);
			DoubleMatrix 	outputs 		= new DoubleMatrix(1, 7);
			String[] 		rCMoveTupleStrs = tupleStr.split("|");
			String 			rCStr 			= rCMoveTupleStrs[0];
			String 			moveStr 		= rCMoveTupleStrs[1];
			String[] 		rCStrs 			= rCStr.split(",");
			String[] 		moveStrs 		= moveStr.split(",");
			
			for(int i=0; i<inputs.columns; i++) {
				String inputStr 	= rCStrs[i];
				double input 		= Double.parseDouble(inputStr);
				inputs.put(0, i, input);
			}
			for(int i=0; i<outputs.columns; i++) {
				String outputStr 	= moveStrs[i];
				double output 		= Double.parseDouble(outputStr);
				outputs.put(0, i, output);
			}
			
			TrainingTuple trainingTuple = new TrainingTuple(inputs, outputs);
			tuples.add(trainingTuple);
		}
		return new TrainingData(tuples);
	}
	
	/**
	 * Generates a move given a training data output vector.
	 * 
	 * @param outputVector			vector of outputs from neural network
	 * @return						move corresponding to the outputVector
	 */
	public static Move outputVectorToMove(DoubleMatrix outputVector) {
		int 		sliceNum 	= -1;
		Axis 		axis 		= null;
		Direction 	dir 		= null;
		
		double sliceBit0 	= Math.round(outputVector.get(0, 0));
		double sliceBit1 	= Math.round(outputVector.get(0, 1));
		double sliceBit2 	= Math.round(outputVector.get(0, 2));
		double axisBit0 	= Math.round(outputVector.get(0, 3));
		double axisBit1 	= Math.round(outputVector.get(0, 4));
		double axisBit2 	= Math.round(outputVector.get(0, 5));
		double dirBit 		= Math.round(outputVector.get(0, 6));
		
		if(sliceBit0 >= 0.0) 		sliceNum = 2;
		else if(sliceBit1 >= 0.0) 	sliceNum = 1;
		else if(sliceBit2 >= 0.0) 	sliceNum = 0;
		
		if(axisBit0 >= 0.0) 		axis = Axis.Z;
		else if(axisBit1 >= 0.0) 	axis = Axis.Y;
		else if(axisBit2 >= 0.0) 	axis = Axis.X;
		
		if(dirBit >= 0.0) 			dir = Direction.CCW;
		else if(dirBit < 0.0) 		dir = Direction.CW;
		
		MoveParams moveParams 		= new MoveParams(sliceNum, axis, dir);
		Move move 					= new Move(moveParams);
		return move;
	}
	
	/**
	 * Creates an input vector from a rubik's cube to be fed into a neural network.
	 * 
	 * @param rubiksCube		rubik's cube
	 * @return					vector to input into a neural network
	 */
	public static DoubleMatrix rubiksCubeToInputVector(RubiksCube rubiksCube) {
		String 			dataStr 	= genCubeTrainingData(rubiksCube);
		String[] 		dataStrArr 	= dataStr.split("[,]+");
		DoubleMatrix 	inputVec 	= new DoubleMatrix(1, dataStrArr.length);
		for(int i=0; i<dataStrArr.length; i++) {
			double data = Double.parseDouble(dataStrArr[i]);
			inputVec.put(0, i, data);
		}
		return inputVec;
	}
	
	/* helper function */
	private static String genCubeTrainingData(RubiksCube rubiksCube) {
		byte[][][] 	cube 	= rubiksCube.getCube();
		String 		rtnStr 	= "";
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
	
	/* helper function */
	private static String genMoveTrainingData(Move move) {
		MoveParams 	params 	= move.getMoveParams();
		String 		rtnStr 	= "";
		
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
		if(params.getDirection().equals(Direction.CW))	rtnStr += -1;
		else 											rtnStr += 1;
		
		return rtnStr;
	}
	
	/* helper function */
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
