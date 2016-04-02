package genetic_algorithm;

import static org.junit.Assert.*;

import org.junit.Test;

import neural_network.NeuralNetwork;
import training_data.XORTrainingDataGenerator;

public class TDSfs {

	@Test
	public void test() {
		GeneticAlgorithm GA = new GeneticAlgorithm();
		NeuralNetwork NN = new NeuralNetwork();
		NN.init();
		GA.apply(NN, new NNFitnessTester(XORTrainingDataGenerator.gen(), NN.getParams()));
	}

}
