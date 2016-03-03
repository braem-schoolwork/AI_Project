package training_algorithms;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.jblas.DoubleMatrix;
import org.junit.Test;

import neural_network.NeuralNetwork;
import training_data.XORTrainingDataGenerator;

public class SBPTest {

	@Test
	public void trainingErrorTest() {
		SBP sbp = new SBP();
		sbp.setTrainee(new NeuralNetwork());
		sbp.apply(XORTrainingDataGenerator.gen());
		assertTrue(sbp.getError() >= 0);
	}
	
	@Test
	public void calcErrorTest1() {
		DoubleMatrix output1 = new DoubleMatrix(new double[][] {{-1}});
		DoubleMatrix output2 = new DoubleMatrix(new double[][] {{-1}});
		DoubleMatrix output3 = new DoubleMatrix(new double[][] {{1}});
		DoubleMatrix output4 = new DoubleMatrix(new double[][] {{1}});
		ArrayList<DoubleMatrix> ttOutputs = new ArrayList<DoubleMatrix>();
		SBP sbp = new SBP();
		ttOutputs.add(output1);
		ttOutputs.add(output2);
		ttOutputs.add(output3);
		ttOutputs.add(output4);
		double error = sbp.calculateError(ttOutputs, ttOutputs); //should be 0
		assertTrue(error == 0.0);
	}
	
	@Test
	public void calcErrorTest2() {
		DoubleMatrix output1 = new DoubleMatrix(new double[][] {{-1}});
		DoubleMatrix output2 = new DoubleMatrix(new double[][] {{-1}});
		DoubleMatrix output3 = new DoubleMatrix(new double[][] {{1}});
		DoubleMatrix output4 = new DoubleMatrix(new double[][] {{1}});
		ArrayList<DoubleMatrix> ttOutputs = new ArrayList<DoubleMatrix>();
		SBP sbp = new SBP();
		ttOutputs.add(output1);
		ttOutputs.add(output2);
		ttOutputs.add(output3);
		ttOutputs.add(output4);
		ArrayList<DoubleMatrix> cActualOutputs = new ArrayList<DoubleMatrix>();
		cActualOutputs.add(output1);
		cActualOutputs.add(output2);
		cActualOutputs.add(output3);
		cActualOutputs.add(output1); //should get an error of 2
		double error = sbp.calculateError(ttOutputs, cActualOutputs); //should be 2
		assertTrue(Math.round(error) == 2.0);
	}

}
