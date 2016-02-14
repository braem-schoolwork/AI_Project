package neural_network;

import java.util.ArrayList;
import java.util.Random;
import org.jblas.*;

public class SBP
{
	//class params
	private static int epochs = 2;
	private static int trainingIterations = 5000;
	private static int errorThreshold;
	private static double learningRate = 0.1;
	private static double startingEdgeWeight = 20.0;
	
	//setters
	public static void setEpochs(int epochs) { SBP.epochs = epochs; }
	public static void setTrainingIterations(int trainingIterations) { SBP.trainingIterations = trainingIterations; }
	public static void setErrorThreshold(int errorThreshold) { SBP.errorThreshold = errorThreshold; }
	public static void setLearningRate(double learningRate) { SBP.learningRate = learningRate; }
	public static void setStartingEdgeWeight(double startingEdgeWeight) { SBP.startingEdgeWeight = startingEdgeWeight; }
	
	//SBP method
	public static void apply(TrainingData trainingData) {
		ArrayList<TrainingTuple> data = trainingData.getData();
		for(int epoch=0; epoch<epochs; epoch++) {
			/* initialize NN */
			NeuralNetwork NN = new NeuralNetwork(2,2,1, startingEdgeWeight);
			for(int iter=0; iter<trainingIterations; iter++) {
				//TT -1,1|-1
				/* pick a training tuple from trainer at random */
				Random rand = new Random();
				int randInt = rand.nextInt(data.size());
				TrainingTuple chosenTuple = data.get(randInt);
				double[] inputArr = chosenTuple.getInputs();
				double[] expectedOutputArr = chosenTuple.getAnswers();
				DoubleMatrix inputVector = new DoubleMatrix(new double[][] { inputArr } );
				DoubleMatrix expectedOutputVector = new DoubleMatrix(new double[][] { expectedOutputArr } );
				
				/* test training tuple */
				DoubleMatrix actualOutputVector = NN.feedForward(inputVector);
				
				/* Calculate Updates */
				//delta k			(error at output layer)
				//( (expect output k) - (actual output k) ) * sigmoid'(NETk)  TODO fix mul for vectors
				DoubleMatrix deltaK = ( expectedOutputVector.sub(actualOutputVector) ).mul(SigmoidFunction.applyDeriv(NN.NETk));
				
				//delta Wkj			(matrix of weight difference) 
				//(learning curve) * deltaK
				DoubleMatrix deltaWkj = deltaK.mul(learningRate);
				
				//delta Wkbias
				//(learning curve) * ( (expect output k) - (actual output k) ) * sigmoid'(NETk)  /  delta k
				DoubleMatrix deltaWkbias;
				
				//delta j 			(error at hidden layer)
				//sigmoid'(NETj) * (sum(Wkj) k=0 to n) * delta k
				DoubleMatrix deltaJ = deltaK;
				
				//delta Wji			(weight updates)
				//(learning curve) * (activation at input i) * delta j
				DoubleMatrix deltaWji;
				
				//delta Wjbias
				//(learning curve) * delta j
				DoubleMatrix deltaWjbias = deltaJ.mul(learningRate);
				
				
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
