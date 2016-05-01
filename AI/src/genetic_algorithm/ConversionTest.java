package genetic_algorithm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkParams;

public class ConversionTest {

	@Test
	public void test() {
		NeuralNetwork NN = new NeuralNetwork();
		NN.init();
		Genome 			g 	= Genome.convertTo(NN);
		NeuralNetwork 	NN2 = (NeuralNetwork) Genome.convertFrom(NN, g);
		assertTrue(NN2.equals(NN));
	}
	
	@Test
	public void test2() {
		NeuralNetworkParams NNparams 	= new NeuralNetworkParams();
		ArrayList<Integer> 	hls 		= new ArrayList<Integer>();
		hls.add(36);
		hls.add(67);
		NNparams.setHiddenLayerSizes	(hls);
		NNparams.setInputLayerSize		(20);
		NNparams.setOutputLayerSize		(400);
		NeuralNetwork NN = new NeuralNetwork(NNparams);
		NN.init();
		Genome 			g 	= Genome.convertTo(NN);
		NeuralNetwork 	NN2 = (NeuralNetwork) Genome.convertFrom(NN, g);
		assertTrue(NN2.equals(NN));
	}
	
}
