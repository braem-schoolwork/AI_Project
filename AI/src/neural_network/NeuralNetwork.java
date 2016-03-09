package neural_network;

import java.io.Serializable;
import java.util.ArrayList;

import org.jblas.*;

import random_gen.RandomNumberGenerator;
import training_algorithms.SBPImpl;

/**
 * A multilayered neural network
 * 
 * @author braem
 * @version 1.0
 */

public class NeuralNetwork implements SBPImpl, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DoubleMatrix Wji;		//Weight matrices
	private DoubleMatrix Wkj;
	private ArrayList<DoubleMatrix> Wjbias;
	private DoubleMatrix Wkbias;
	private ArrayList<DoubleMatrix> Wjs;
	private DoubleMatrix NETk;		//Nets stored in feedForward for SBP
	private ArrayList<DoubleMatrix> NETjs;
	private ArrayList<DoubleMatrix> ACTjs;
	private Sigmoid sigmoidFunc;
	private NeuralNetworkParams params;
	private DoubleMatrix error;
	
	public NeuralNetwork() { //initialize for basic XOR NN
		params = new NeuralNetworkParams();
		error = new DoubleMatrix(1, params.getOutputLayerSize());
		error = error.fill(Double.MAX_VALUE);
		NETjs = null;
		ACTjs = null;
		sigmoidFunc = new Sigmoid(new SigmoidParams());
	}
	
	public NeuralNetwork(NeuralNetworkParams params) {
		this.params = params;
		error = new DoubleMatrix(1, params.getOutputLayerSize());
		error = error.fill(Double.MAX_VALUE);
		NETjs = null;
		ACTjs = null;
		sigmoidFunc = new Sigmoid(new SigmoidParams());
	}
	public NeuralNetwork(NeuralNetworkParams params, Sigmoid sigmoidFunc) {
		this.params = params;
		error = new DoubleMatrix(1, params.getOutputLayerSize());
		error = error.fill(Double.MAX_VALUE);
		NETjs = null;
		ACTjs = null;
		this.sigmoidFunc = sigmoidFunc;
	}
	public NeuralNetwork(NeuralNetworkParams params, SigmoidParams sigParams) {
		this.params = params;
		error = new DoubleMatrix(1, params.getOutputLayerSize());
		error = error.fill(Double.MAX_VALUE);
		NETjs = null;
		ACTjs = null;
		sigmoidFunc = new Sigmoid(sigParams);
	}
	
	
	/**
	 * Feeds an input vector through the network and produces an output vector
	 * 
	 * @param inputVector input vector
	 * @return output vector of this network
	 */
	@Override
	public DoubleMatrix feedForward(DoubleMatrix inputVector) {
		if(NETjs != null)
			NETjs.clear();
		if(ACTjs != null)
			ACTjs.clear();
		double bias = params.getBias();
		
		/* HIDDEN LAYER */
		//Hidden Net Matrix = inputVector*Wji + Wjbias*bias
		NETjs = new ArrayList<DoubleMatrix>(); //NET values of each hidden layer
		ACTjs = new ArrayList<DoubleMatrix>(); //ACT values of each hidden layer
		for(int i=0; i<params.getHiddenLayerSizes().size(); i++) {
			DoubleMatrix hNetMatrix = new DoubleMatrix(1, params.getHiddenLayerSizes().get(i));
			DoubleMatrix hActMatrix = new DoubleMatrix(hNetMatrix.rows, hNetMatrix.columns);
			if(i==0)
				hNetMatrix = inputVector.mmul(Wji) .add(Wjbias.get(i).mul(bias));
			else	//get previous layers ACT values then *weights. Then add the bias
				hNetMatrix = ACTjs.get(i-1).mmul(Wjs.get(i-1)) .add(Wjbias.get(i).mul(bias));
			hActMatrix = applySigmoid(hNetMatrix);
			NETjs.add(hNetMatrix);
			ACTjs.add(hActMatrix);
		}
		
		/* OUTPUT LAYER */
		//Output Net Matrix = hiddenActMatrix*Wkj + Wkbias*bias
		DoubleMatrix outputNetMatrix = ACTjs.get(ACTjs.size()-1).mmul(Wkj).add(Wkbias.mul(bias));
		NETk = outputNetMatrix;
		
		//Actual Output Matrix = tanh(outputNetMatrix*B)*A
		DoubleMatrix outputActMatrix = applySigmoid(outputNetMatrix);
		
		//return actual output matrix
		return outputActMatrix;
	}
	
	/**
	 * Takes a Matrix and applies the sigmoid function to every cell
	 * @param inc incoming matrix
	 * @return same matrix with the sigmoid function applied to every cell
	 */
	@Override
	public DoubleMatrix applySigmoid(DoubleMatrix inc) {
		return sigmoidFunc.apply(inc);
	}
	/**
	 * Takes a mtrix and applies the derivative of the sigmoid function
	 * to every cell
	 * @param inc incoming matrix
	 * @return same matrix with the derivative of the sigmoid function applied
	 * to every cell
	 */
	@Override
	public DoubleMatrix applySigmoidDeriv(DoubleMatrix inc) {
		return sigmoidFunc.applyDeriv(inc);
	}
	
	/* update application */
	@Override
	public void applyWkjUpdate (DoubleMatrix Wkj) { this.Wkj.addi(Wkj); }
	@Override
	public void applyWkbiasUpdate (DoubleMatrix Wkbias) { this.Wkbias.addi(Wkbias); }
	@Override
	public void applyWjiUpdate (DoubleMatrix Wji) { this.Wji.addi(Wji); }
	@Override
	public void applyWjbiasUpdate (ArrayList<DoubleMatrix> Wjbias) {
		for(int i=0; i<Wjbias.size(); i++)
			this.Wjbias.set(i, this.Wjbias.get(i).add(Wjbias.get(i)));
	}
	@Override
	public void applyWjsUpdate (ArrayList<DoubleMatrix> Wjs) {
		for(int i=0; i<Wjs.size(); i++)
			this.Wjs.set(i, this.Wjs.get(i).add(Wjs.get(i)));
	}

	//getters
	public int getOutputLayerSize() { return params.getOutputLayerSize(); }
	public NeuralNetworkParams getParams() { return params; }
	@Override
	public DoubleMatrix getNETk() { return NETk; }
	@Override
	public ArrayList<DoubleMatrix> getNETjs() { return NETjs; }
	@Override
	public DoubleMatrix getWkj() { return Wkj; }
	@Override
	public ArrayList<DoubleMatrix> getACTjs() { return ACTjs; }
	@Override
	public ArrayList<DoubleMatrix> getWjs() { return Wjs; }
	DoubleMatrix getWji() { return Wji; }
	ArrayList<DoubleMatrix> getWjbias() { return Wjbias; }
	DoubleMatrix getWkbias() { return Wkbias; }
	public DoubleMatrix getError() { return error; }
	
	//setters
	public void setParams(NeuralNetworkParams params) { this.params = params; }
	void setWji(DoubleMatrix Wji) { this.Wji = Wji; }
	void setWjbias(ArrayList<DoubleMatrix> Wjbias) { this.Wjbias = Wjbias; }
	void setWkj(DoubleMatrix Wkj) { this.Wkj = Wkj; }
	void setWkbias(DoubleMatrix Wkbias) { this.Wkbias = Wkbias; }
	void setWjs(ArrayList<DoubleMatrix> Wjs) { this.Wjs = Wjs; }

	/**
	 * Initializes this neural network by creating the weight matrices with
	 * dimensions determined by the neural network parameters set & filling
	 * them with appropriate edge weights randomized between the intervals of
	 * -1/sqrt(d) and 1/sqrt(d) where d is the number of incoming neurons to a neuron
	 */
	@Override
	public void init() {
		//get the size parameters
		int inputLayerSize = params.getInputLayerSize();
		int numHiddenLayers = params.getHiddenLayerSizes().size();
		int outputLayerSize = params.getOutputLayerSize();
		ArrayList<Integer> hiddenLayerSizes = params.getHiddenLayerSizes();
		
		//initialize the weight matrices/vectors
		Wji = new DoubleMatrix(inputLayerSize, hiddenLayerSizes.get(0));
		Wkj = new DoubleMatrix(hiddenLayerSizes.get(numHiddenLayers-1), outputLayerSize);
		Wjbias = new ArrayList<DoubleMatrix>();
		Wkbias = new DoubleMatrix(1, outputLayerSize); //row vector
		Wjs = new ArrayList<DoubleMatrix>(); //will be empty if num hidden layers = 1
		for(int i=0; i<numHiddenLayers; i++) { //fill Wjbias'
			DoubleMatrix hiddenLayerToBias = new DoubleMatrix(1, hiddenLayerSizes.get(i));
			for(int j=0; j<hiddenLayerToBias.rows; j++)
				for(int k=0; k<hiddenLayerToBias.columns; k++)
					hiddenLayerToBias.put(j, k, RandomNumberGenerator.genBetweenInterval(-1, 1));
			Wjbias.add(hiddenLayerToBias);
		}
		for(int i=1; i<numHiddenLayers; i++) { //fill Wjs
			int rows = hiddenLayerSizes.get(i-1);
			int columns = hiddenLayerSizes.get(i);
			double numNeurons = rows;
			double leftCap = -1 / Math.sqrt(numNeurons);
			double rightCap = leftCap*-1;
			DoubleMatrix hiddenWeightsAtHi = new DoubleMatrix(rows, columns);
			for(int j=0; j<hiddenWeightsAtHi.rows; j++) //fill the hidden matrix
				for(int k=0; k<hiddenWeightsAtHi.columns; k++)
					hiddenWeightsAtHi.put(j, k, RandomNumberGenerator.genBetweenInterval(leftCap, rightCap));
			Wjs.add(hiddenWeightsAtHi);
		}
		
		//get the Wji and Wkj weight intervals
		double WjiNumInputs = inputLayerSize + 1; //inputs neurons + bias neuron
		double WjiLeftCap = -1 / Math.sqrt(WjiNumInputs);
		double WjiRightCap = WjiLeftCap * -1;
		double WkjNumInputs = hiddenLayerSizes.get(numHiddenLayers-1) + 1; //last hidden layer num neurons + bias
		double WkjLeftCap = -1 / Math.sqrt(WkjNumInputs);
		double WkjRightCap = WkjLeftCap * -1;
		//fill with initial edge weights
		for(int i=0; i<Wji.rows; i++)
			for(int j=0; j<Wji.columns; j++)
				Wji.put(i, j, RandomNumberGenerator.genBetweenInterval(WjiLeftCap, WjiRightCap));
		for(int i=0; i<Wkj.rows; i++)
			for(int j=0; j<Wkj.columns; j++)
				Wkj.put(i, j, RandomNumberGenerator.genBetweenInterval(WkjLeftCap, WkjRightCap));
		for(int i=0; i<Wkbias.rows; i++)
			for(int j=0; j<Wkbias.columns; j++)
				Wkbias.put(i, j, RandomNumberGenerator.genBetweenInterval(-1, 1));
	}
	
	@Override
	public void saveToDisk(DoubleMatrix error) {
		this.error = error;
		if(NeuralNetworkIO.isBestNetworkSoFar(error)) { //if best network so far, save it to disk
			NeuralNetworkIO.writeNetwork(this);
		}
	}
	
	@Override
	public void printAllEdges() {
		System.out.println("Wji"+Wji);
		System.out.println("Wjbias"+Wjbias);
		System.out.println("Wkj"+Wkj);
		System.out.println("Wkbias"+Wkbias);
		System.out.println("Wjs"+Wjs);
	}
}
