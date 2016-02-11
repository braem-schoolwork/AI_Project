package unit_tests;

import static org.junit.Assert.*;

import org.jblas.*;

import org.junit.Test;
import neural_network.NeuralNetwork;

public class NNTest {

	@Test
	public void nnTest() {
		DoubleMatrix matrix1 = new DoubleMatrix(new double[][] {{1.2, 1.6, 2.0}} );
		System.out.println(matrix1);
		System.out.println(matrix1.rows);
		
		DoubleMatrix matrix2 = DoubleMatrix.ones(2,3);
		System.out.println(matrix2);
		for(int i=0; i<matrix2.rows; i++) {
			for(int j=0; j<matrix2.columns; j++) {
				matrix2.put(i, j, 4.7);
			}
		}
		System.out.println(matrix2);
	}

}
