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
	private DoubleMatrix Wji;
	private DoubleMatrix Wkj;
	private DoubleMatrix Wjbias;
	private DoubleMatrix Wkbias;
	private double bias = 0.667;
	private double A = 1.716;
	DoubleMatrix NETk;
	DoubleMatrix NETj;
	DoubleMatrix ACTj;
	int hiddenLayerSize;
	
	public NeuralNetwork(int inputLayerSize, int hiddenLayerSize, int outputLayerSize, double initialEdgeWeight) {
		this.hiddenLayerSize = hiddenLayerSize;
		Wji = DoubleMatrix.ones(inputLayerSize, hiddenLayerSize);
		Wkj = DoubleMatrix.ones(hiddenLayerSize, outputLayerSize);
		Wjbias = DoubleMatrix.ones(1, hiddenLayerSize);
		Wkbias = DoubleMatrix.ones(1, outputLayerSize);
		
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
	
	//feedForward(inputVector) : outputVector
	//output is actual output
	@Override
	public DoubleMatrix feedForward(DoubleMatrix inputVector) {
		
		/* HIDDEN LAYER */
		//Hidden Net Matrix = inputVector*Wji + Wjbias*bias
		//(h0 h1 h2) etc.
		DoubleMatrix hiddenNetMatrix = inputVector.mul(Wji).add(Wjbias.mul(bias));
		NETj = hiddenNetMatrix;
		
		//Hidden Act Matrix = tanh(hiddenNetMatrix*bias)*A
		DoubleMatrix hiddenActMatrix = SigmoidFunction.apply(hiddenNetMatrix);
		ACTj = hiddenActMatrix;
		
		/* OUTPUT LAYER */
		//Output Net Matrix = hiddenActMatrix*Wkj + Wkbias*bias
		DoubleMatrix outputNetMatrix = hiddenActMatrix.mul(Wkj).add(Wkbias.mul(bias));
		NETk = outputNetMatrix;
		
		//Actual Output Matrix = tanh(outputNetMatrix*bias)*A
		DoubleMatrix outputActMatrix = SigmoidFunction.apply(outputNetMatrix);
		
		//return actual output matrix
		return outputActMatrix;
		
	}
	
	public void applyWkjUpdate (DoubleMatrix Wkj) {
		this.Wkj.add(Wkj);
	}
	public void applyWkbiasUpdate (DoubleMatrix Wkbias) {
		this.Wkbias.add(Wkbias);
	}
	public void applyWjiUpdate (DoubleMatrix Wji) {
		this.Wji.add(Wji);
	}
	public void applyWjbiasUpdate (DoubleMatrix Wjbias) {
		this.Wjbias.add(Wjbias);
	}
}
