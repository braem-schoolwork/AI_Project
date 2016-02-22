package training_data;

import org.jblas.DoubleMatrix;

public class TrainingTuple
{
	DoubleMatrix inputs;
	DoubleMatrix answers;
	
	public TrainingTuple(DoubleMatrix inputs, DoubleMatrix answers) {
		this.inputs = inputs;
		this.answers = answers;
	}
	
	public DoubleMatrix getInputs() {
		return inputs;
	}
	public DoubleMatrix getAnswers() {
		return answers;
	}
	
	public void setInputs(DoubleMatrix inputs) {
		this.inputs = inputs;
	}
	public void setAnswers(DoubleMatrix answers) {
		this.answers = answers;
	}
	
}
