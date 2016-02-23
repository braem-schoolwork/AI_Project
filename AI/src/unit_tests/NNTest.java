package unit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.jblas.*;

import org.junit.Test;

import neural_network.*;
import training_algorithms.SBP;
import training_data.TrainingData;
import training_data.TrainingTuple;

public class NNTest {
	
	@Test
	public void nnTest() {
		NeuralNetwork NN = new NeuralNetwork();
		SBP.setNetwork(NN);
		TrainingTuple t1 = new TrainingTuple(new DoubleMatrix(new double[][] {{1,1}}), new DoubleMatrix(new double[][] {{-1}}));
		TrainingTuple t2 = new TrainingTuple(new DoubleMatrix(new double[][] {{-1,1}}), new DoubleMatrix(new double[][] {{1}}));
		TrainingTuple t3 = new TrainingTuple(new DoubleMatrix(new double[][] {{-1,-1}}), new DoubleMatrix(new double[][] {{-1}}));
		TrainingTuple t4 = new TrainingTuple(new DoubleMatrix(new double[][] {{1,-1}}), new DoubleMatrix(new double[][] {{1}}));
		ArrayList<TrainingTuple> tuples = new ArrayList<TrainingTuple>();
		tuples.add(t1); 
		tuples.add(t2); 
		tuples.add(t3); 
		tuples.add(t4); 
		TrainingData data = new TrainingData(tuples);
		SBP.apply(data);
		
		
	}
	
	@Test
	public void calcErrorTest() {
		DoubleMatrix matrix1 = new DoubleMatrix(new double[][] {{1,-1,1,-1}});
		ArrayList<DoubleMatrix> da = new ArrayList<DoubleMatrix>();
		da.add(matrix1);
		assertTrue(SBP.calculateError(da, da) == 0);
	}

}
