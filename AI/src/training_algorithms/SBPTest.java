package training_algorithms;

import static org.junit.Assert.*;

import org.jblas.DoubleMatrix;
import org.junit.Test;

import matrix_wrapper.MatrixFunctionWrapper;
import neural_network.NeuralNetwork;
import training_data.XORTrainingDataGenerator;

public class SBPTest
{
	@Test
	public void test() { //train on XOR
		NeuralNetwork NN = new NeuralNetwork();
		SBPParams sbpParams = new SBPParams();
		sbpParams.setErrorThreshold(0);
		sbpParams.setEpochs(100);
		sbpParams.setTrainingIterations(4000);
		SBP sbp = new SBP(sbpParams);
		sbp.setTrainee(NN);
		sbp.apply(XORTrainingDataGenerator.gen());
		DoubleMatrix error = sbp.getError();
		System.out.println(error);
		assertTrue(MatrixFunctionWrapper.isContentsBelowValue(error, 0.01));
	}
	
}
