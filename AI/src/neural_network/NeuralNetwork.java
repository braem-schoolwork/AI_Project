package neural_network;

import java.util.ArrayList;
import java.util.List;

import org.jblas.*;

import training_algorithms.SBPImpl;

/**
 * 
 * @author braem
 *
 * Input/Hidden/Output Layered Neural Network
 * 
 */

public class NeuralNetwork implements SBPImpl
{
	private DoubleMatrix Wji;		//Weight matrices
	private DoubleMatrix Wkj;
	private DoubleMatrix Wjbias;
	private DoubleMatrix Wkbias;
	private List<DoubleMatrix> hiddenLayers;
	private DoubleMatrix NETk;		//Nets stored in feedForward for SBP
	private DoubleMatrix NETj;
	private DoubleMatrix ACTj;
	private NeuralNetworkParams params;
	
	public NeuralNetwork() { //initialize for basic XOR NN
		params = new NeuralNetworkParams();
	}
	
	public NeuralNetwork(NeuralNetworkParams params) {
		this.params = params;
	}
	
	/* FEED FORWARD */
	@Override
	public DoubleMatrix feedForward(DoubleMatrix inputVector) {
		double bias = params.getBias();
		/* HIDDEN LAYER */
		//Hidden Net Matrix = inputVector*Wji + Wjbias*bias
		DoubleMatrix hiddenNetMatrix = inputVector.mmul(Wji).add(Wjbias.mmul(bias));
		NETj = hiddenNetMatrix;
		
		//Hidden Act Matrix = tanh(hiddenNetMatrix*bias)*A
		DoubleMatrix hiddenActMatrix = applySigmoid(hiddenNetMatrix);
		ACTj = hiddenActMatrix;
		
		/* OUTPUT LAYER */
		//Output Net Matrix = hiddenActMatrix*Wkj + Wkbias*bias
		DoubleMatrix outputNetMatrix = hiddenActMatrix.mmul(Wkj).add(Wkbias.mmul(bias));
		NETk = outputNetMatrix;
		
		//Actual Output Matrix = tanh(outputNetMatrix*bias)*A
		DoubleMatrix outputActMatrix = applySigmoid(outputNetMatrix);
		
		//return actual output matrix
		return outputActMatrix;
		
	}
	
	/* sigmoid function application */
	@Override
	public DoubleMatrix applySigmoid(DoubleMatrix inc) {
		double bias = params.getBias();
		double A = params.getA();
		return MatrixFunctions.tanh(inc.mmul(bias)).mmul(A);
	}
	@Override
	public DoubleMatrix applySigmoidDeriv(DoubleMatrix inc) {
		double bias = params.getBias();
		double A = params.getA();
		return MatrixFunctions.pow( MatrixFunctions.tanh(inc.mmul(bias)),2 ).mmul(-A*bias).add(A*bias);
	}
	
	/* update application */
	@Override
	public void applyWkjUpdate (DoubleMatrix Wkj) { this.Wkj.addi(Wkj); }
	@Override
	public void applyWkbiasUpdate (DoubleMatrix Wkbias) { this.Wkbias.addi(Wkbias); }
	@Override
	public void applyWjiUpdate (DoubleMatrix Wji) { this.Wji.addi(Wji); }
	@Override
	public void applyWjbiasUpdate (DoubleMatrix Wjbias) { this.Wjbias.addi(Wjbias); }

	//getters
	public int getInputLayerSize() { return params.getInputLayerSize(); }
	public List<Integer> getHiddenLayerSizes() { return params.getHiddenLayerSizes(); }
	public int getOutputLayerSize() { return params.getOutputLayerSize(); }
	public double getInitialEdgeWeight() { return params.getInitialEdgeWeight(); }
	public double getAVal() { return params.getA(); }
	public double getBiasVal() { return params.getBias(); }
	@Override
	public DoubleMatrix getNETk() { return NETk; }
	@Override
	public DoubleMatrix getNETj() { return NETj; }
	@Override
	public DoubleMatrix getWkj() { return Wkj; }
	@Override
	public DoubleMatrix getACTj() { return ACTj; }
	DoubleMatrix getWji() { return Wji; }
	DoubleMatrix getWjbias() { return Wjbias; }
	DoubleMatrix getWkbias() { return Wkbias; }
	
	//setters
	public void setParams(NeuralNetworkParams params) { this.params = params; }
	void setWji(DoubleMatrix Wji) { this.Wji = Wji; }
	void setWjbias(DoubleMatrix Wjbias) { this.Wjbias = Wjbias; }
	void setWkj(DoubleMatrix Wkj) { this.Wkj = Wkj; }
	void setWkbias(DoubleMatrix Wkbias) { this.Wkbias = Wkbias; }
	void setHiddenLayers(List<DoubleMatrix> hiddenLayers) { this.hiddenLayers = hiddenLayers; }

	/* Initialize this network */
	@Override
	public void init() {
		List<Integer> hiddenLayerSizes = params.getHiddenLayerSizes();
		Wji = new DoubleMatrix(params.getInputLayerSize(), hiddenLayerSizes.get(0));
		Wkj = new DoubleMatrix(hiddenLayerSizes.get(hiddenLayerSizes.size()-1), params.getOutputLayerSize());
		Wjbias = new DoubleMatrix(1, params.getHiddenLayerSizes().get(0)); //row vector
		Wkbias = new DoubleMatrix(1, params.getOutputLayerSize()); //row vector
		hiddenLayers = new ArrayList<DoubleMatrix>();
		
		for(int i=1; i<hiddenLayerSizes.size(); i++) {
			DoubleMatrix hiddenWeightsAtHi = new DoubleMatrix(hiddenLayerSizes.get(i-1), hiddenLayerSizes.get(i));
			for(int j=0; j<hiddenWeightsAtHi.rows; j++) //fill the hidden matrix
				for(int k=0; k<hiddenWeightsAtHi.columns; k++)
					hiddenWeightsAtHi.put(j, k, params.getInitialEdgeWeight());
			hiddenLayers.add(hiddenWeightsAtHi);
		}
		
		//fill with initial edge weights
		for(int i=0; i<Wji.rows; i++)
			for(int j=0; j<Wji.columns; j++)
				Wji.put(i, j, params.getInitialEdgeWeight());
		for(int i=0; i<Wkj.rows; i++)
			for(int j=0; j<Wkj.columns; j++)
				Wkj.put(i, j, params.getInitialEdgeWeight());
		for(int i=0; i<Wjbias.rows; i++)
			for(int j=0; j<Wjbias.columns; j++)
				Wjbias.put(i, j, params.getInitialEdgeWeight());
		for(int i=0; i<Wkbias.rows; i++)
			for(int j=0; j<Wkbias.columns; j++)
				Wkbias.put(i, j, params.getInitialEdgeWeight());
	}
	
	@Override
	public void saveToDisk(double error) {
		if(isBestSoFar(error)) //if best network so far, save it to disk
			NeuralNetworkIO.writeNetwork(this, error);
	}

	public static boolean isBestSoFar(double error) {
		return NeuralNetworkIO.isBestNetworkSoFar(error);
	}

}
