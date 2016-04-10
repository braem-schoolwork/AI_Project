package training_algorithms;

import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

import training_data.TrainingData;
import training_data.TrainingTuple;

public class ErrorCalculator
{
	public static DoubleMatrix calculateError(TrainingData trainingData, SBPImpl trainee) {
		DoubleMatrix errorVec = DoubleMatrix.zeros(1, trainingData.getData().get(0).getOutputs().columns);
		for(TrainingTuple tt : trainingData.getData()) {
			DoubleMatrix inputVec = tt.getInputs();
			DoubleMatrix expectedOutputVec = tt.getOutputs();
			DoubleMatrix actualOutputVec = trainee.feedForward(inputVec);
			DoubleMatrix thisTupleError = MatrixFunctions.pow(expectedOutputVec.sub(actualOutputVec), 2);
			thisTupleError.mmuli(0.5);
			errorVec = errorVec.addRowVector(thisTupleError);
		}
		for(int i=0; i<errorVec.columns; i++) {
			errorVec.put(0,i,errorVec.get(0,i)/trainingData.getData().size());
		}
		return errorVec;
	}
}
