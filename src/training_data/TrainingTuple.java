package training_data;

import org.jblas.DoubleMatrix;

/**
 * Container for a training tuple
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class TrainingTuple
{
	DoubleMatrix inputs;
	DoubleMatrix outputs;
	
	public TrainingTuple(DoubleMatrix inputs, DoubleMatrix answers) {
		this.inputs = inputs;
		this.outputs = answers;
	}
	
	public DoubleMatrix getInputs() 						{ return inputs; }
	public DoubleMatrix getOutputs() 						{ return outputs; }
	public void 		setInputs(DoubleMatrix inputs) 		{ this.inputs = inputs; }
	public void 		setOutputs(DoubleMatrix outputs) 	{ this.outputs = outputs; }
	
	@Override
	public String toString() {
		String input 	= (""+inputs).replace("0", "").replace(".", "");
		String output 	= (""+outputs).replace("0", "").replace(".", "");
		
		return input +" | "+ output;
	}
}
