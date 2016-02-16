package unit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.jblas.*;

import org.junit.Test;
import neural_network.*;

public class NNTest {
	
	@Test
	public void nnTest() {
		NeuralNetwork NN = new NeuralNetwork();
		SBP.setNetwork(NN);
		TrainingTuple t1 = new TrainingTuple(new double[] {-1,-1}, new double[] {-1});
		TrainingTuple t2 = new TrainingTuple(new double[] {-1,1}, new double[] {1});
		TrainingTuple t3 = new TrainingTuple(new double[] {1,-1}, new double[] {1});
		TrainingTuple t4 = new TrainingTuple(new double[] {1,1}, new double[] {-1});
		ArrayList<TrainingTuple> tuples = new ArrayList<TrainingTuple>();
		tuples.add(t1); tuples.add(t2); tuples.add(t3); tuples.add(t4); 
		TrainingData data = new TrainingData(tuples);
		SBP.apply(data);
	}
	
	@Test
	public void mTest() {
		DoubleMatrix matrix1 = new DoubleMatrix(new double[][] {{-1,1}});
		DoubleMatrix matrix2 = new DoubleMatrix(new double[][] {{20,20}, {20,20}});
	}

}
