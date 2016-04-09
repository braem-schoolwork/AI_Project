package training_data;

import java.util.ArrayList;

import org.jblas.DoubleMatrix;

/**
 * Generator to generate the XOR training data
 * 
 * @author braemen
 * @version 1.0
 */
public class XORTrainingDataGenerator {

	/**
	 * generates XOR training data
	 * 
	 * @return XOR TrainingData
	 */
	public static TrainingData gen() {
		TrainingTuple t1 = new TrainingTuple(new DoubleMatrix(new double[][] {{-1,1}}), new DoubleMatrix(new double[][] {{1}}));
		TrainingTuple t2 = new TrainingTuple(new DoubleMatrix(new double[][] {{-1,-1}}), new DoubleMatrix(new double[][] {{-1}}));
		TrainingTuple t3 = new TrainingTuple(new DoubleMatrix(new double[][] {{1,1}}), new DoubleMatrix(new double[][] {{-1}}));
		TrainingTuple t4 = new TrainingTuple(new DoubleMatrix(new double[][] {{1,-1}}), new DoubleMatrix(new double[][] {{1}}));
		ArrayList<TrainingTuple> tuples = new ArrayList<TrainingTuple>();
		tuples.add(t1); 
		tuples.add(t2); 
		tuples.add(t3); 
		tuples.add(t4); 
		return new TrainingData(tuples);
	}

}
