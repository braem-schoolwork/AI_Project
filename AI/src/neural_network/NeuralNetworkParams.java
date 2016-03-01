package neural_network;

public class NeuralNetworkParams
{
	private double A;				//Sigmoid function related
	private double bias;
	private int inputLayerSize;		//layer sizes
	private int hiddenLayerSize;
	private int outputLayerSize;
	private int hiddenLayers;
	private double initialEdgeWeight;
	
	public NeuralNetworkParams() {
		setA(1.716);
		setBias(0.667);
		setInputLayerSize(2);
		setHiddenLayerSize(2);
		setOutputLayerSize(1);
		setHiddenLayers(1);
		setInitialEdgeWeight(0.1);
	}
	
	public NeuralNetworkParams(double A, double bias, int inputLayerSize, int hiddenLayerSize,
			int outputLayerSize, int hiddenLayers, double initialEdgeWeight) {
		this.setA(A);
		this.setBias(bias);
		this.setInputLayerSize(inputLayerSize);
		this.setHiddenLayerSize(hiddenLayerSize);
		this.setOutputLayerSize(outputLayerSize);
		this.setHiddenLayers(hiddenLayers);
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

	public int getHiddenLayerSize() {
		return hiddenLayerSize;
	}

	public void setHiddenLayerSize(int hiddenLayerSize) {
		this.hiddenLayerSize = hiddenLayerSize;
	}

	public int getOutputLayerSize() {
		return outputLayerSize;
	}

	public void setOutputLayerSize(int outputLayerSize) {
		this.outputLayerSize = outputLayerSize;
	}

	public int getHiddenLayers() {
		return hiddenLayers;
	}

	public void setHiddenLayers(int hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
	}

	public double getInitialEdgeWeight() {
		return initialEdgeWeight;
	}

	public void setInitialEdgeWeight(double initialEdgeWeight) {
		this.initialEdgeWeight = initialEdgeWeight;
	}
}
