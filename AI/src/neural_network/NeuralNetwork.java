package neural_network;

import java.io.Serializable;
import java.util.ArrayList;

import org.jblas.*;

import genetic_algorithm.GenomeImpl;
import random_gen.RandomNumberGenerator;
import training_algorithms.SBPImpl;

/**
 * A multilayered neural network.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class NeuralNetwork implements SBPImpl, GenomeImpl, Serializable
{
	private static final long 		serialVersionUID = 1L;
	private DoubleMatrix 			Wji;
	private DoubleMatrix 			Wkj;
	private ArrayList<DoubleMatrix> Wjbias;
	private DoubleMatrix 			Wkbias;
	private ArrayList<DoubleMatrix> Wjs;
	private DoubleMatrix 			NETk;
	private ArrayList<DoubleMatrix> NETjs;
	private ArrayList<DoubleMatrix> ACTjs;
	private Sigmoid 				sigmoidFunc;
	private NeuralNetworkParams 	params;
	private DoubleMatrix 			error;
	
	public NeuralNetwork() {
		params 		= new NeuralNetworkParams();
		error 		= new DoubleMatrix(1, params.getOutputLayerSize());
		error 		= error.fill(Double.MAX_VALUE);
		NETjs 		= null;
		ACTjs 		= null;
		sigmoidFunc = new Sigmoid(new SigmoidParams());
	}
	public NeuralNetwork(NeuralNetworkParams params) {
		this.params = params;
		error 		= new DoubleMatrix(1, params.getOutputLayerSize());
		error 		= error.fill(Double.MAX_VALUE);
		NETjs 		= null;
		ACTjs 		= null;
		sigmoidFunc = new Sigmoid(new SigmoidParams());
	}
	public NeuralNetwork(NeuralNetworkParams params, Sigmoid sigmoidFunc) {
		this.params 		= params;
		error 				= new DoubleMatrix(1, params.getOutputLayerSize());
		error 				= error.fill(Double.MAX_VALUE);
		NETjs 				= null;
		ACTjs 				= null;
		this.sigmoidFunc 	= sigmoidFunc;
	}
	public NeuralNetwork(NeuralNetworkParams params, SigmoidParams sigParams) {
		this.params = params;
		error 		= new DoubleMatrix(1, params.getOutputLayerSize());
		error 		= error.fill(Double.MAX_VALUE);
		NETjs 		= null;
		ACTjs 		= null;
		sigmoidFunc = new Sigmoid(sigParams);
	}
	
	@Override
	public DoubleMatrix feedForward(DoubleMatrix inputVector) {
		if(NETjs != null)
			NETjs.clear();
		if(ACTjs != null)
			ACTjs.clear();
		double bias = params.getBias();
		
		/* HIDDEN LAYER */
		NETjs = new ArrayList<DoubleMatrix>();
		ACTjs = new ArrayList<DoubleMatrix>();
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
		DoubleMatrix outputNetMatrix = ACTjs.get(ACTjs.size()-1).mmul(Wkj).add(Wkbias.mul(bias));
		NETk = outputNetMatrix;
		
		DoubleMatrix outputActMatrix = applySigmoid(outputNetMatrix);

		return outputActMatrix;
	}
	
	@Override
	public DoubleMatrix applySigmoid(DoubleMatrix inc) 		{ return sigmoidFunc.apply(inc); }
	@Override
	public DoubleMatrix applySigmoidDeriv(DoubleMatrix inc) { return sigmoidFunc.applyDeriv(inc); }
	
	@Override
	public void applyWkjUpdate (DoubleMatrix Wkj) 		{ this.Wkj.addi(Wkj); }
	@Override
	public void applyWkbiasUpdate (DoubleMatrix Wkbias) { this.Wkbias.addi(Wkbias); }
	@Override
	public void applyWjiUpdate (DoubleMatrix Wji) 		{ this.Wji.addi(Wji); }
	@Override
	public void applyWjbiasUpdate (ArrayList<DoubleMatrix> Wjbias) {
		for(int i=0; i<Wjbias.size(); i++)
			this.Wjbias.get(i).addi(Wjbias.get(i));
	}
	@Override
	public void applyWjsUpdate (ArrayList<DoubleMatrix> Wjs) {
		for(int i=0; i<Wjs.size(); i++)
			this.Wjs.get(i).addi(Wjs.get(i));
	}

	public int 						getOutputLayerSize() 	{ return params.getOutputLayerSize(); }
	public NeuralNetworkParams 		getParams() 			{ return params; }
	@Override
	public DoubleMatrix 			getNETk() 				{ return NETk; }
	@Override
	public ArrayList<DoubleMatrix> 	getNETjs() 				{ return NETjs; }
	@Override
	public DoubleMatrix 			getWkj() 				{ return Wkj; }
	@Override
	public ArrayList<DoubleMatrix> 	getACTjs() 				{ return ACTjs; }
	@Override
	public ArrayList<DoubleMatrix> 	getWjs() 				{ return Wjs; }
	public DoubleMatrix 			getWji() 				{ return Wji; }
	public ArrayList<DoubleMatrix> 	getWjbias() 			{ return Wjbias; }
	public DoubleMatrix 			getWkbias() 			{ return Wkbias; }
	public DoubleMatrix 			getError() 				{ return error; }

	public void setParams(NeuralNetworkParams params) 		{ this.params = params; }
	public void setWji(DoubleMatrix Wji) 					{ this.Wji = Wji; }
	public void setWjbias(ArrayList<DoubleMatrix> Wjbias) 	{ this.Wjbias = Wjbias; }
	public void setWkj(DoubleMatrix Wkj) 					{ this.Wkj = Wkj; }
	public void setWkbias(DoubleMatrix Wkbias) 				{ this.Wkbias = Wkbias; }
	public void setWjs(ArrayList<DoubleMatrix> Wjs) 		{ this.Wjs = Wjs; }

	@Override
	public void init() {
		int 				inputLayerSize 		= params.getInputLayerSize();
		int 				numHiddenLayers 	= params.getHiddenLayerSizes().size();
		int 				outputLayerSize 	= params.getOutputLayerSize();
		ArrayList<Integer> 	hiddenLayerSizes 	= params.getHiddenLayerSizes();

		Wji 	= new DoubleMatrix(inputLayerSize, hiddenLayerSizes.get(0));
		Wkj 	= new DoubleMatrix(hiddenLayerSizes.get(hiddenLayerSizes.size()-1), outputLayerSize);
		Wjbias 	= new ArrayList<DoubleMatrix>();
		Wkbias 	= new DoubleMatrix(1, outputLayerSize);
		Wjs 	= new ArrayList<DoubleMatrix>();
		
		for(int i=0; i<numHiddenLayers; i++) {
			DoubleMatrix hiddenLayerToBias = new DoubleMatrix(1, hiddenLayerSizes.get(i));
			for(int j=0; j<hiddenLayerToBias.rows; j++)
				for(int k=0; k<hiddenLayerToBias.columns; k++)
					hiddenLayerToBias.put(j, k, RandomNumberGenerator.genDoubleBetweenInterval(-1, 1));
			Wjbias.add(hiddenLayerToBias);
		}
		for(int i=1; i<numHiddenLayers; i++) {
			int 			rows 				= hiddenLayerSizes.get(i-1);
			int 			columns 			= hiddenLayerSizes.get(i);
			double 			numNeurons 			= rows;
			double 			leftCap 			= -1 / Math.sqrt(numNeurons);
			double 			rightCap 			= leftCap*-1;
			DoubleMatrix 	hiddenWeightsAtHi 	= new DoubleMatrix(rows, columns);
			for(int j=0; j<hiddenWeightsAtHi.rows; j++)
				for(int k=0; k<hiddenWeightsAtHi.columns; k++)
					hiddenWeightsAtHi.put(j, k, RandomNumberGenerator.genDoubleBetweenInterval(leftCap, rightCap));
			Wjs.add(hiddenWeightsAtHi);
		}
		
		//get the Wji and Wkj weight intervals
		double WjiNumInputs 	= inputLayerSize + 1;
		double WjiLeftCap 		= -1 / Math.sqrt(WjiNumInputs);
		double WjiRightCap 		= WjiLeftCap * -1;
		double WkjNumInputs 	= hiddenLayerSizes.get(numHiddenLayers-1) + 1;
		double WkjLeftCap 		= -1 / Math.sqrt(WkjNumInputs);
		double WkjRightCap 		= WkjLeftCap * -1;
		
		for(int i=0; i<Wji.rows; i++)
			for(int j=0; j<Wji.columns; j++)
				Wji.put(i, j, RandomNumberGenerator.genDoubleBetweenInterval(WjiLeftCap, WjiRightCap));
		for(int i=0; i<Wkj.rows; i++)
			for(int j=0; j<Wkj.columns; j++)
				Wkj.put(i, j, RandomNumberGenerator.genDoubleBetweenInterval(WkjLeftCap, WkjRightCap));
		for(int i=0; i<Wkbias.rows; i++)
			for(int j=0; j<Wkbias.columns; j++)
				Wkbias.put(i, j, RandomNumberGenerator.genDoubleBetweenInterval(-1, 1));
	}
	
	@Override
	public void setError(DoubleMatrix error) { this.error = error; }
	
	@Override
	public void saveToDisk(DoubleMatrix error) {
		this.error = error;
		if(NeuralNetworkIO.isBestNetworkSoFar(error)) { //if best network so far, save it to disk
			NeuralNetworkIO.writeNetwork(this);
		}
	}

	@Override
	public GenomeImpl[] genOthers(int size) {
		NeuralNetwork[] randomNetworks = new NeuralNetwork[size];
		for(int i=0; i<randomNetworks.length; i++) {
			NeuralNetwork newNN = new NeuralNetwork(this.getParams());
			newNN.init();
			randomNetworks[i] = newNN;
		}
		return randomNetworks;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof NeuralNetwork) {
			NeuralNetwork 	NN 	= (NeuralNetwork) obj;
			boolean 		b 	= NN.params.equals(this.params);
			b &= NN.Wjbias.equals(this.Wjbias);
			b &= NN.Wkbias.equals(this.Wkbias);
			b &= NN.Wjs.equals(this.Wjs);
			b &= NN.Wji.equals(this.Wji);
			b &= NN.Wkj.equals(this.Wkj);
			if(b)
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() { return Wji+"\n"+Wjs+"\n"+Wkj+"\n"+Wjbias+"\n"+Wkbias; }
}
