package neural_network;

import org.jblas.*;

import training_algorithms.SBPImpl;

/**
 * 
 * @author braem
 *
 * 
 */

public class NeuralNetwork implements SBPImpl
{
	private DoubleMatrix Wji;			//Weight matrices
	private DoubleMatrix Wkj;
	private DoubleMatrix Wjbias;
	private DoubleMatrix Wkbias;
	private double A = 1;			//Sigmoid function related
	private double bias = 1;
	private DoubleMatrix NETk;			//Nets stored in feedForward for SBP
	private DoubleMatrix NETj;
	private DoubleMatrix ACTj;
	private int inputLayerSize = 2;		//layer sizes
	private int hiddenLayerSize = 2;
	private int outputLayerSize = 1;
	private double initialEdgeWeight = 0.1;
	
	/* FEED FORWARD */
	@Override
	public DoubleMatrix feedForward(DoubleMatrix inputVector) {
		
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
		
		/*System.out.println("FF");
		System.out.println(Wji);
		System.out.println(Wjbias);
		System.out.println(Wkj);
		System.out.println(Wkbias);*/
		
		//return actual output matrix
		return outputActMatrix;
		
	}
	
	/* sigmoid function application */
	@Override
	public DoubleMatrix applySigmoid(DoubleMatrix inc) {
		return MatrixFunctions.tanh(inc.mmul(bias)).mmul(A);
	}
	@Override
	public DoubleMatrix applySigmoidDeriv(DoubleMatrix inc) {
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
	public int getInputLayerSize() { return inputLayerSize; }
	public int getHiddenLayerSize() { return hiddenLayerSize; }
	public int getOutputLayerSize() { return outputLayerSize; }
	public double getAVal() { return A; }
	public double getBiasVal() { return bias; }
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
	public void setInputLayerSize(int p) { this.inputLayerSize = p; }
	public void setHiddenLayerSize(int p) { this.hiddenLayerSize = p; }
	public void setOutputLayerSize(int p) { this.outputLayerSize = p; }
	public void setInitialEdgeWeight(double p) { this.initialEdgeWeight = p; }
	public void setA(double a) { this.A = a; }
	public void setBias(double bias) { this.bias = bias; }

	/* Initialize this network */
	@Override
	public void init() {
		Wji = new DoubleMatrix(inputLayerSize, hiddenLayerSize);
		Wkj = new DoubleMatrix(hiddenLayerSize, outputLayerSize);
		Wjbias = new DoubleMatrix(1, hiddenLayerSize); //row vector
		Wkbias = new DoubleMatrix(1, outputLayerSize); //row vector
		
		//fill with initial edge weights
		for(int i=0; i<Wji.rows; i++)
			for(int j=0; j<Wji.columns; j++)
				Wji.put(i, j, initialEdgeWeight);
		for(int i=0; i<Wkj.rows; i++)
			for(int j=0; j<Wkj.columns; j++)
				Wkj.put(i, j, initialEdgeWeight);
		for(int i=0; i<Wjbias.rows; i++)
			for(int j=0; j<Wjbias.columns; j++)
				Wjbias.put(i, j, initialEdgeWeight);
		for(int i=0; i<Wkbias.rows; i++)
			for(int j=0; j<Wkbias.columns; j++)
				Wkbias.put(i, j, initialEdgeWeight);
	}
	
	@Override
	public void saveToDisk(double error) {
		NeuralNetworkIO.writeNetwork(this, error);
	}

	@Override
	public boolean isBestSoFar(double error) {
		return NeuralNetworkIO.isBestNetworkSoFar(error);
	}

}
