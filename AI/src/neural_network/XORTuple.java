package neural_network;

import java.util.ArrayList;

public class XORTuple implements TrainingTuple
{
	double[] inputs;
	double[] answers;
	
	@Override
	public double[] getInputs() {
		return inputs;
	}
	@Override
	public double[] getAnswers() {
		return answers;
	}
}
