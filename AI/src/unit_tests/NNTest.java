package unit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.jblas.*;

import org.junit.Test;

import experimental_data.Phase2Experiment;
import neural_network.*;
import training_algorithms.SBP;
import training_data.TrainingData;
import training_data.TrainingTuple;
import training_data.XORTrainingDataGenerator;

public class NNTest {
	
	@Test
	public void errorTest() {
		SBP.setTrainee(new NeuralNetwork());
		SBP.apply(XORTrainingDataGenerator.gen());
		assertTrue(SBP.getError() >= 0);
	}
	
	@Test
	public void calcErrorTest() {
		/*Phase2Experiment.runExperimentLRTR();
		Phase2Experiment.runExperimentMRTR();
		Phase2Experiment.runExperimentLRMR();*/
	}
	
	@Test
	public void matrixIOText() {
		DoubleMatrix m1 = new DoubleMatrix(new double[][] {{-1,1}});
		String m1str = m1.toString().replace('[', ' ').replace(']', ' ').replace(',', ' ');
		DoubleMatrix m2 = DoubleMatrix.valueOf(m1str);
		assertTrue(m1.equals(m2));
	}

}
