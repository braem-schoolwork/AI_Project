package neural_network;

import java.util.ArrayList;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import java.math.*;
import org.jblas.*;

/**
 * 
 * @author braem
 *
 *
 * A = 1.716
 * B = 0.667
 */
public class NeuralNetwork implements SBPImpl
{
	DoubleMatrix inputMatrix;
	SimpleWeightedGraph<Float, DefaultWeightedEdge> network;
	ArrayList<Float> inputs;
	ArrayList<Float> hidden;
	ArrayList<Float> outputs;
	double bias = 0.667;
	double A = 1.716;
	
	public NeuralNetwork(int inputLayerSize, int hiddenLayerSize, int outputLayerSize, float initialEdgeWeight, float[] input) {
		network = new SimpleWeightedGraph<Float, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		inputs = new ArrayList<Float>();
		hidden = new ArrayList<Float>();
		outputs = new ArrayList<Float>();
		
		network.addVertex(bias);
		for(int i=0; i<inputLayerSize; i++) { 
			inputs.add(20f);
			network.addVertex(inputs.get(i));
		}
		for(int i=0; i<hiddenLayerSize; i++) {
			hidden.add(20f);
			network.addVertex(hidden.get(i));
			for(int j=0; j<inputLayerSize; j++) {
				DefaultWeightedEdge edge = network.addEdge(inputs.get(j), hidden.get(i));
				network.setEdgeWeight(edge, initialEdgeWeight);
			}
			DefaultWeightedEdge edge = network.addEdge(bias, hidden.get(i));
			network.setEdgeWeight(edge, initialEdgeWeight);
		}
		for(int i=0; i<outputLayerSize; i++) {
			outputs.add(20f);
			network.addVertex(outputs.get(i));
			for(int j=0; j<hiddenLayerSize; j++) {
				DefaultWeightedEdge edge = network.addEdge(hidden.get(j), outputs.get(i));
				network.setEdgeWeight(edge, initialEdgeWeight);
			}
			DefaultWeightedEdge edge = network.addEdge(bias, outputs.get(i));
			network.setEdgeWeight(edge, initialEdgeWeight);
		}
	}
	
	public SimpleWeightedGraph<Float, DefaultWeightedEdge> getNetwork() {
		return network;
	}
	
	//feedForward(inputVector) : outputVector
	//output is actual output
	public TrainingTuple feedForward(TrainingTuple inputVector) {
		
		/* HIDDEN LAYER */
		//Net0 = i0*w00 + i1*w01 + BIAS*W0bias
		double Net0 = inputs.get(0)*network.getEdgeWeight(network.getEdge(inputs.get(0), hidden.get(0))) + 
				inputs.get(1)*network.getEdgeWeight(network.getEdge(inputs.get(1), hidden.get(0))) +
				bias*network.getEdgeWeight(network.getEdge(bias, hidden.get(0)));
		
		//Act0 = A*tanh(BIAS*NET0)
		double Act0 = A*Math.tanh(bias*Net0);
		
		//Net1 = i0*W10 + i1*W11 + Bias*W1bias
		double Net1 = inputs.get(0)*network.getEdgeWeight(network.getEdge(inputs.get(0), hidden.get(1))) + 
				inputs.get(1)*network.getEdgeWeight(network.getEdge(inputs.get(1), hidden.get(1))) +
				bias*network.getEdgeWeight(network.getEdge(bias, hidden.get(1)));
		
		//Act1 - A*tanh(BIAS*NET1)
		double Act1 = A*Math.tanh(bias*Net1);
		
		/* OUTPUT LAYER */
		//NET0 = ACT0*W00 + ACT1*W01 + ACT2*W20 + BIAS*W0bias
		
		//ACT0 = A*tanh(BIAS*NET0)
		
		return null;
	}
}
