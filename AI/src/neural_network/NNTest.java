package neural_network;

import static org.junit.Assert.*;

import org.jblas.*;

import org.junit.Test;

public class NNTest {
	
	@Test
	public void matrixIOTest() {
		DoubleMatrix m1 = new DoubleMatrix(new double[][] {{-1,1}});
		String m1str = m1.toString().replace('[', ' ').replace(']', ' ').replace(',', ' ');
		DoubleMatrix m2 = DoubleMatrix.valueOf(m1str);
		assertTrue(m1.equals(m2));
	}
	
	@Test
	public void test() {
		NeuralNetwork NN = NeuralNetworkIO.readNetwork();
		DoubleMatrix output = NN.feedForward(new DoubleMatrix(new double[][] {{1,1}}));
		System.out.println(output);
		System.out.println(NN.feedForward(new DoubleMatrix(new double[][] {{-1,-1}})));
		System.out.println(NN.feedForward(new DoubleMatrix(new double[][] {{-1,1}})));
		System.out.println(NN.feedForward(new DoubleMatrix(new double[][] {{1,-1}})));
	}
	
}
