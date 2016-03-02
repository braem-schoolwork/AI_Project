package neural_network;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkParams
{
	private double A;				//Sigmoid function related
	private double bias;
	private int inputLayerSize;		//layer sizes
	private List<Integer> hiddenLayerSizes;
	private int outputLayerSize;
	private double initialEdgeWeight;
	
	public NeuralNetworkParams() {
		setA(1);
		setBias(2.1);
		setInputLayerSize(2);
		List<Integer> sizes = new ArrayList<Integer>();
		sizes.add(2);
		setHiddenLayerSizes(sizes);
		setOutputLayerSize(1);
		setInitialEdgeWeight(0.1);
	}
	
	public NeuralNetworkParams(double A, double bias, int inputLayerSize, List<Integer> hiddenLayerSizes,
			int outputLayerSize, double initialEdgeWeight) {
		this.setA(A);
		this.setBias(bias);
		this.setInputLayerSize(inputLayerSize);
		this.setHiddenLayerSizes(hiddenLayerSizes);
		this.setOutputLayerSize(outputLayerSize);
		this.setInitialEdgeWeight(initialEdgeWeight);
	}

	public double getA() {
		return A;
	}

	public void setA(double a) {
		A = a;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public int getInputLayerSize() {
		return inputLayerSize;
	}

	public void setInputLayerSize(int inputLayerSize) {
		this.inputLayerSize = inputLayerSize;
	}

	public List<Integer> getHiddenLayerSizes() {
		return hiddenLayerSizes;
	}

	public void setHiddenLayerSizes(List<Integer> hiddenLayerSizes) {
		this.hiddenLayerSizes = hiddenLayerSizes;
	}

	public int getOutputLayerSize() {
		return outputLayerSize;
	}

	public void setOutputLayerSize(int outputLayerSize) {
		this.outputLayerSize = outputLayerSize;
	}

	public double getInitialEdgeWeight() {
		return initialEdgeWeight;
	}

	public void setInitialEdgeWeight(double initialEdgeWeight) {
		this.initialEdgeWeight = initialEdgeWeight;
	}
}
