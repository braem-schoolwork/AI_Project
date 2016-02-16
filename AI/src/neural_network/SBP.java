package neural_network;

import java.util.ArrayList;
import java.util.Random;
import org.jblas.*;

public class SBP
{
	//class params
	private static int epochs = 2;
	private static int trainingIterations = 5;
	private static int errorThreshold;
	private static double learningRate = 0.1;
	private static SBPImpl network;
	
	//setters
	public static void setEpochs(int epochs) { SBP.epochs = epochs; }
	public static void setTrainingIterations(int trainingIterations) { SBP.trainingIterations = trainingIterations; }
	public static void setErrorThreshold(int errorThreshold) { SBP.errorThreshold = errorThreshold; }
	public static void setLearningRate(double learningRate) { SBP.learningRate = learningRate; }
	public static void setNetwork(SBPImpl nwrk) { network = nwrk; }
	
	//SBP method
	public static void apply(TrainingData trainingData) {
		ArrayList<TrainingTuple> data = trainingData.getData();
		for(int epoch=0; epoch<epochs; epoch++) {
			/* initialize NN */
			network.init();
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
				DoubleMatrix actualOutputVector = network.feedForward(inputVector);
				
				/* Calculate Updates */
				//delta k			(error at output layer)
				//( (expect output k) - (actual output k) ) * sigmoid'(NETk)
				DoubleMatrix deltaK = ( expectedOutputVector.sub(actualOutputVector) ).mulRowVector(network.applySigmoidDeriv(network.getNETk()));
				
				DoubleMatrix Yj = network.applySigmoid(network.getNETj());
				//delta Wkj			(matrix of weight difference) 
				//(learning curve) * deltaK * f(NETj)
				DoubleMatrix deltaWkj = new DoubleMatrix(network.getOutputLayerSize(), network.getHiddenLayerSize());
				for(int i=0; i<deltaWkj.columns; i++) {
					for(int j=0; j<deltaWkj.rows; j++) {
						deltaWkj.put(j, i, learningRate*deltaK.get(0, i)*Yj.get(0, j) );
					}
				}
				
				//delta Wkbias
				//(learning curve) * deltaK * 1
				DoubleMatrix deltaWkbias = deltaK.mul(learningRate);
				
				//delta j 			(error at hidden layer)
				//sigmoid'(NETj) * (sum(Wkj) k=0 to n) * delta k
				DoubleMatrix deltaJ = network.applySigmoidDeriv(network.getNETj());
				for(int i=0; i<deltaJ.columns; i++) {
					double sum = 0.0;
					for(int j=0; j<actualOutputVector.length; j++) {
						sum += network.getWkj().get(i,j)*deltaK.get(0,i);
					}
					deltaJ.put(0, i, deltaJ.get(0,i)*sum);
				}
				
				DoubleMatrix ACTi = network.applySigmoid(inputVector);
				//delta Wji			(weight updates)
				//(learning curve) * (activation at input i) * delta j
				DoubleMatrix deltaWji = new DoubleMatrix(network.getHiddenLayerSize(), network.getInputLayerSize());
				for(int i=0; i<deltaWji.columns; i++) {
					for(int j=0; j<deltaWji.rows; j++) {
						deltaWkj.put(j, i, learningRate*ACTi.get(0,j)*deltaJ.get(0, i));
					}
				}
				
				//delta Wjbias
				//(learning curve) * delta j
				DoubleMatrix deltaWjbias = deltaJ.mul(learningRate);
				
				/* apply updates */
				//delta Wkj
				network.applyWkjUpdate(deltaWkj);
				//delta Wkbias
				network.applyWkbiasUpdate(deltaWkbias);
				//delta Wji
				network.applyWjiUpdate(deltaWji);
				//delta Wjbias
				network.applyWjbiasUpdate(deltaWjbias);
			}
			
			/* Check error */
			double error = 0.0;
			for(int i=0; i<trainingData.getData().size(); i++) {
				error += 0.5;
			}
			if(error < errorThreshold) {
				/* save best network so far to disk */
				network.writeNetworkToFile(error);
				return;
			}
		}
	}
}
