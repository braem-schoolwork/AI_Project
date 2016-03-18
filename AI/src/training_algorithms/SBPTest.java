package training_algorithms;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.jblas.DoubleMatrix;
import org.junit.Test;

import experimental_data.ExperimentIO;
import experimental_data.Phase3Experiment;
import matrix_wrapper.MatrixFunctionWrapper;
import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkParams;
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
		assertTrue(MatrixFunctionWrapper.isContentsBelowValue(error, 0.0001));
	}
	
}
