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
	public void isBestNetworkTest() {
		assertTrue(NeuralNetwork.isBestSoFar(0.0));
	}
	
	@Test
	public void feedForwardTest1() {
		DoubleMatrix inputMatrix = new DoubleMatrix(new double[][] {{-1,-1}});
		NeuralNetwork NN = new NeuralNetwork();
		NN.init();
		System.out.println(NN.feedForward(inputMatrix));
		assertTrue(Math.round(0.041501*10000) == Math.round(NN.feedForward(inputMatrix).get(0,0)*10000));
	}
	
	@Test
	public void feedForwardTest2() {
		DoubleMatrix inputMatrix = new DoubleMatrix(new double[][] {{-1,1}});
		NeuralNetwork NN = new NeuralNetwork();
		NN.init();
		System.out.println(NN.feedForward(inputMatrix));
		assertTrue(Math.round(0.093714*10000) == Math.round(NN.feedForward(inputMatrix).get(0,0)*10000));
	}
	
	@Test
	public void feedForwardTest3() {
		DoubleMatrix inputMatrix = new DoubleMatrix(new double[][] {{1,-1}});
		NeuralNetwork NN = new NeuralNetwork();
		NN.init();
		System.out.println(NN.feedForward(inputMatrix));
		assertTrue(Math.round(0.093714*10000) == Math.round(NN.feedForward(inputMatrix).get(0,0)*10000));
	}
	
	@Test
	public void feedForwardTest4() {
		DoubleMatrix inputMatrix = new DoubleMatrix(new double[][] {{1,1}});
		NeuralNetwork NN = new NeuralNetwork();
		NN.init();
		System.out.println(NN.feedForward(inputMatrix));
		assertTrue(Math.round(0.145145*10000) == Math.round(NN.feedForward(inputMatrix).get(0,0)*10000));
	}

}
