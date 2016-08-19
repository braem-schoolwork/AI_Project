package genetic_algorithm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import experimental_data.ExperimentSize;
import experimental_data.Phase1Experiment;
import neural_network.NNFitnessTester;
import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkParams;
import training_data.TrainingData;
import training_data.TrainingDataGenerator;
import training_data.XORTrainingDataGenerator;

public class GATest {

	@Test
	public void NNTest() {
		GeneticAlgorithmParams 	GAparams 	= new GeneticAlgorithmParams();
		GeneticAlgorithm 		GA 			= new GeneticAlgorithm(GAparams);
		NeuralNetwork 			NN 			= new NeuralNetwork();
		NN.init();
		GA.apply(NN, new NNFitnessTester(XORTrainingDataGenerator.gen(), NN));
		assertTrue(GA.getBestFitness() >= 0);
		assertTrue(GA.getBestGenomeImpl() instanceof NeuralNetwork);
		assertTrue(GA.getBestGenomeImpl() != null);
		assertTrue(GA.getAvgFitnesses().size() == GA.getBestFitnesses().size());
		assertTrue(GA.getBestFitnesses().size() == GA.getWorstFitnesses().size());
	}

	@Test
	public void Test() {
		GeneticAlgorithmParams GAparams = new GeneticAlgorithmParams();
		GAparams.setNumGenerations(1);
		GAparams.setPopulationSize(20);
		GeneticAlgorithm 	GA 			= new GeneticAlgorithm(GAparams);
		NeuralNetworkParams NNparams 	= new NeuralNetworkParams();
		ArrayList<Integer> 	hls 		= new ArrayList<Integer>();
		hls.add(1);
		NNparams.setHiddenLayerSizes	(hls);
		NNparams.setInputLayerSize		(324);
		NNparams.setOutputLayerSize		(7);
		NeuralNetwork NN = new NeuralNetwork(NNparams);
		NN.init();
		TrainingData td = TrainingDataGenerator.genFromFile();
		if(td == null) {
			Phase1Experiment exp = new Phase1Experiment();
			exp.runExperiment(".csv", ExperimentSize.SMALL);
		}
		td = TrainingDataGenerator.genFromFile();
		GA.apply(NN, new NNFitnessTester(td, NN));
		assertTrue(GA.getBestFitness() >= 0);
	}
	
}
