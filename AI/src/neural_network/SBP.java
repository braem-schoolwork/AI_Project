package neural_network;

public class SBP
{
	private static int epochs;
	private static int trainingIterations;
	private static int errorThreshold;
	private static double learningRate;
	private static double startingEdgeWeight = 20.0;
	
	public static void apply(SBPImpl trainee) {
		double[] inputs = {-1,1};
		for(int epoch=0; epoch<epochs; epoch++) {
			/* initialize NN */
			NeuralNetwork NN = new NeuralNetwork(2,2,1, startingEdgeWeight, inputs);
			for(int iter=0; iter<trainingIterations; iter++) {
				/* pick a training tuple from trainee */
				
				/* test training tuple */
				double actualOutput = NN.feedForward(inputs);
				
				/* Calculate Updates */
				//delta k			(error at output layer)
				//( (expect output k) - (actual output k) ) * sigmoid'(NETk)
				//sigmoid' = 1-tanh(x)^2
				double deltaK = 0f;
				
				//delta Wkj			(matrix of weight difference)
				//(learning curve) * ( (expect output k) - (actual output k) ) * sigmoid'(NETk) * sigmoid(NETj)  /  delta k
				double deltaWkj = (learningRate)/deltaK;
				
				//delta Wkbias
				//(learning curve) * ( (expect output k) - (actual output k) ) * sigmoid'(NETk)  /  delta k
				double deltaWkbias = (learningRate)/deltaK;
				
				//delta j 			(error at hidden layer)
				//sigmoid'(NETj) * (sum(Wkj) k=0 to n) * delta k
				double deltaJ = deltaK;
				
				//delta Wji			(weight updates)
				//(learning curve) * (activation at input i) * delta j
				double deltaWji = learningRate;
				
				//delta Wjbias
				//(learning curve) * delta j
				double deltaWjbias = learningRate * deltaJ;
				
				
				/* apply updates */
				//delta Wkj
				//delta Wkbias
				//delta Wji
				//delta Wjbias
			}
			
			/* Check error */
			double error = 0f;
			if(error < errorThreshold) {
				/* save best network so far to disk */
				return;
			}
		}
	}
}
