package unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;
import neural_network.NeuralNetwork;

public class NNTest {

	@Test
	public void nnTest() {
		NeuralNetwork nn = new NeuralNetwork(2,2,1, 1f);
		System.out.println(nn.getNetwork().toString());
	}

}
