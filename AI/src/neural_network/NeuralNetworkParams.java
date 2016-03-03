package neural_network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkParams implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private double A;				//Sigmoid function related
	private double bias;
	private int inputLayerSize;		//layer sizes
	private ArrayList<Integer> hiddenLayerSizes;
	private int outputLayerSize;
	
	public NeuralNetworkParams() {
		setA(1);
		setBias(2.1);
		setInputLayerSize(2);
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		sizes.add(2);
		setHiddenLayerSizes(sizes);
		setOutputLayerSize(1);
	}
	
	public NeuralNetworkParams(double A, double bias, int inputLayerSize,
			ArrayList<Integer> hiddenLayerSizes, int outputLayerSize) {
		this.setA(A);
		this.setBias(bias);
		this.setInputLayerSize(inputLayerSize);
		this.setHiddenLayerSizes(hiddenLayerSizes);
		this.setOutputLayerSize(outputLayerSize);
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

	public void setHiddenLayerSizes(ArrayList<Integer> hiddenLayerSizes) {
		this.hiddenLayerSizes = hiddenLayerSizes;
	}

	public int getOutputLayerSize() {
		return outputLayerSize;
	}

	public void setOutputLayerSize(int outputLayerSize) {
		this.outputLayerSize = outputLayerSize;
	}

}
