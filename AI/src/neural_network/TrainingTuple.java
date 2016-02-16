package neural_network;

public class TrainingTuple
{
	double[] inputs;
	double[] answers;
	
	public TrainingTuple(double[] inputs, double[] answers) {
		this.inputs = inputs;
		this.answers = answers;
	}
	
	public double[] getInputs() {
		return inputs;
	}
	public double[] getAnswers() {
		return answers;
	}
	
	public void setInputs(double[] inputs) {
		this.inputs = inputs;
	}
	public void setAnswers(double[] answers) {
		this.answers = answers;
	}
	
}
