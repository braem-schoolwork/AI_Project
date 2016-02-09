package neural_network;

import java.util.ArrayList;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


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
	SimpleWeightedGraph<Neuron, DefaultWeightedEdge> network;
	ArrayList<Integer> inputValues;
	ArrayList<Neuron> inputs;
	ArrayList<Neuron> hidden;
	ArrayList<Neuron> outputs;
	
	public NeuralNetwork(int inputLayerSize, int hiddenLayerSize, int outputLayerSize, float initialEdgeWeight) {
		network = new SimpleWeightedGraph<Neuron, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		inputValues = new ArrayList<Integer>();
		inputs = new ArrayList<Neuron>();
		hidden = new ArrayList<Neuron>();
		outputs = new ArrayList<Neuron>();
		
		Neuron bias = new Neuron('b', 0);
		network.addVertex(bias);
		for(int i=0; i<inputLayerSize; i++) { 
			inputs.add(new Neuron('i', i));
			network.addVertex(inputs.get(i));
		}
		for(int i=0; i<hiddenLayerSize; i++) {
			hidden.add(new Neuron('h', i));
			network.addVertex(hidden.get(i));
			for(int j=0; j<inputLayerSize; j++) {
				DefaultWeightedEdge edge = network.addEdge(inputs.get(j), hidden.get(i));
				network.setEdgeWeight(edge, initialEdgeWeight);
			}
			DefaultWeightedEdge edge = network.addEdge(bias, hidden.get(i));
			network.setEdgeWeight(edge, initialEdgeWeight);
		}
		for(int i=0; i<outputLayerSize; i++) {
			outputs.add(new Neuron('o', i));
			network.addVertex(outputs.get(i));
			for(int j=0; j<hiddenLayerSize; j++) {
				DefaultWeightedEdge edge = network.addEdge(hidden.get(j), outputs.get(i));
				network.setEdgeWeight(edge, initialEdgeWeight);
			}
			DefaultWeightedEdge edge = network.addEdge(bias, outputs.get(i));
			network.setEdgeWeight(edge, initialEdgeWeight);
		}
	}
	
	public SimpleWeightedGraph<Neuron, DefaultWeightedEdge> getNetwork() {
		return network;
	}
	
	//feedForward(inputVector) : outputVector
	//output is actual output
	public TrainingTuple feedForward(TrainingTuple inputVector) {
		
		/* INPUT LAYER */
		//Net0 = i0*w00 + i1*w01 + BIAS*W0bias
		
		//Act0 = A*tanh(BIAS*NET0)
		
		//Net1 = i0 + W10 + i*W11 + Bias*W1bias
		
		//Act1 - A*tanh(BIAS*NET1)
		
		/* OUTPUT LAYER */
		//NET0 = ACT0*W00 + ACT1*W01 + ACT2*W20 + BIAS*W0bias
		
		//ACT0 = A*tanh(BIAS*NET0)
		
		return null;
	}
}
