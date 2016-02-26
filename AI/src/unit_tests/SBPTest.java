package unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import neural_network.NeuralNetwork;
import training_algorithms.SBP;
import training_data.XORTrainingDataGenerator;

public class SBPTest {

	@Test
	public void errorTest() {
		SBP.setTrainee(new NeuralNetwork());
		SBP.apply(XORTrainingDataGenerator.gen());
		assertTrue(SBP.getError() >= 0);
	}

}
