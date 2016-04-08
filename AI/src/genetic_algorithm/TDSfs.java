package genetic_algorithm;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import neural_network.NeuralNetwork;
import random_gen.RandomNumberGenerator;
import training_data.TrainingData;
import training_data.TrainingTuple;
import training_data.XORTrainingDataGenerator;

public class TDSfs {

	@Test
	public void test() {
		GeneticAlgorithm GA = new GeneticAlgorithm();
		NeuralNetwork NN = new NeuralNetwork();
		NN.init();
		TrainingData td = XORTrainingDataGenerator.gen();
		GA.apply(NN, new NNFitnessTester(td, NN.getParams()));
		System.out.println(GA.getBestGenome());
		NeuralNetwork NN2 = Genome.toNN(GA.getBestGenome(), NN.getParams());
		for(TrainingTuple tt : td.getData()) {
			System.out.println(tt.getInputs());
			System.out.println(NN2.feedForward(tt.getInputs()));
			System.out.println(tt.getOutputs());
		}
	}

}
