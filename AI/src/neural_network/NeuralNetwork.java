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
	DoubleMatrix Wji;
	DoubleMatrix Wkj;
	double[] Wjbias;
	double[] Wkbias;
	double bias = 0.667;
	double A = 1.716;
	double[] input;
	
	public NeuralNetwork(int inputLayerSize, int hiddenLayerSize, int outputLayerSize, double initialEdgeWeight, double[] input) {
		
		this.input = input;
		inputMatrix = new DoubleMatrix(new double[][] { input } );
		Wji = DoubleMatrix.ones(inputLayerSize, hiddenLayerSize);
		Wkj = DoubleMatrix.ones(hiddenLayerSize, outputLayerSize);
		Wjbias = new double[hiddenLayerSize];
		Wkbias = new double[outputLayerSize];
		
		for(int i=0; i<Wji.rows; i++)
			for(int j=0; j<Wji.columns; j++)
				Wji.put(i, j, initialEdgeWeight);
		
		for(int i=0; i<Wkj.rows; i++)
			for(int j=0; j<Wkj.columns; j++)
				Wkj.put(i, j, initialEdgeWeight);

		for(int i=0; i<Wjbias.length; i++)
			Wjbias[i] = initialEdgeWeight;
		
		for(int i=0; i<Wkbias.length; i++)
			Wkbias[i] = initialEdgeWeight;
		
	}
	
	//feedForward(inputVector) : outputVector
	//output is actual output
	public double feedForward(double[] inputVector) {
		
		/* HIDDEN LAYER */
		//Net0 = i0*w00 + i1*w01 + BIAS*W0bias
		double hNet0 = input[0]*Wji.get(0,0) + input[1]*Wji.get(0,1) + bias*Wjbias[0];
		
		//Act0 = A*tanh(BIAS*NET0)
		double hAct0 = A*Math.tanh(bias*hNet0);
		
		//Net1 = i0*W10 + i1*W11 + Bias*W1bias
		double hNet1 = input[0]*Wji.get(1,0) + input[1]*Wji.get(1,1) + bias*Wjbias[1];
		
		//Act1 - A*tanh(BIAS*NET1)
		double hAct1 = A*Math.tanh(bias*hNet1);
		
		/* OUTPUT LAYER */
		//NET0 = ACT0*W00 + ACT1*W01 + ACT2*W20 + BIAS*W0bias
		double oNet0 = hAct0*Wkj.get(0,0) + hAct1*Wkj.get(0,1) + bias*Wkbias[0];
		
		//ACT0 = A*tanh(BIAS*NET0)
		double oAct0 = A*Math.tanh(bias*oNet0);
		
		return oAct0;
	}
}
