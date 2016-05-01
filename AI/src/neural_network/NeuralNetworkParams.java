package neural_network;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Parameter object for a Neural Network.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class NeuralNetworkParams implements Serializable
{
	private static final long 	serialVersionUID = 2L;
	private double 				bias;
	private int 				inputLayerSize;	
	private ArrayList<Integer> 	hiddenLayerSizes;
	private int 				outputLayerSize;
	
	public NeuralNetworkParams() {
		setBias(1.0);
		setInputLayerSize(2);
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		sizes.add(2);
		setHiddenLayerSizes(sizes);
		setOutputLayerSize(1);
	}
	
	public NeuralNetworkParams(double bias, int inputLayerSize,
			ArrayList<Integer> hiddenLayerSizes, int outputLayerSize) {
		this.setBias(bias);
		this.setInputLayerSize(inputLayerSize);
		this.setHiddenLayerSizes(hiddenLayerSizes);
		this.setOutputLayerSize(outputLayerSize);
	}

	public double 				getBias() 													{ return bias; }
	public void 				setBias(double bias) 										{ this.bias = bias; }
	public int 					getInputLayerSize() 										{ return inputLayerSize; }
	public void 				setInputLayerSize(int inputLayerSize) 						{ this.inputLayerSize = inputLayerSize; }
	public ArrayList<Integer> 	getHiddenLayerSizes() 										{ return hiddenLayerSizes; }
	public void 				setHiddenLayerSizes(ArrayList<Integer> hiddenLayerSizes) 	{ this.hiddenLayerSizes = hiddenLayerSizes; }
	public int 					getOutputLayerSize() 										{ return outputLayerSize; }
	public void 				setOutputLayerSize(int outputLayerSize) 					{ this.outputLayerSize = outputLayerSize; }

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof NeuralNetworkParams) {
			NeuralNetworkParams p = (NeuralNetworkParams) obj;
			boolean 			b = this.inputLayerSize == p.inputLayerSize;
			b &= this.outputLayerSize == p.outputLayerSize;
			b &= this.hiddenLayerSizes.equals(p.hiddenLayerSizes);
			b &= this.bias == p.bias;
			if(b)
				return true;
		}
		return false;
	}
	
}
