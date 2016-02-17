package neural_network;

import java.util.ArrayList;
import java.util.Random;
import org.jblas.*;

public class SBP
{
	//class params
	private static int epochs = 1;
	private static int trainingIterations = 1000;
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
			ArrayList<DoubleMatrix> ttOutputs = new ArrayList<DoubleMatrix>();
			for(TrainingTuple tt : data)
				ttOutputs.add(tt.getAnswers());
			ArrayList<DoubleMatrix> cActualOutputs = new ArrayList<DoubleMatrix>(ttOutputs.size());
			for(int i=0; i<ttOutputs.size(); i++) {
				cActualOutputs.add(null);
			}
			/* initialize NN */
			network.init();
			
			for(int iter=0; iter<trainingIterations; iter++) {
				//TT -1,1|-1
				/* pick a training tuple from trainer at random */
				Random rand = new Random();
				int randInt = rand.nextInt(data.size());
				TrainingTuple chosenTuple = data.get(randInt);
				DoubleMatrix inputVector = chosenTuple.getInputs();
				DoubleMatrix expectedOutputVector = chosenTuple.getAnswers();
				
				/* test training tuple */
				DoubleMatrix actualOutputVector = network.feedForward(inputVector);
				cActualOutputs.add(randInt, actualOutputVector);
				
				/* Calculate Updates */
				//delta k			(error at output layer)
				//( (expect output k) - (actual output k) ) * sigmoid'(NETk)    TODO really big, fix!
				DoubleMatrix deltaK = ( expectedOutputVector.sub(actualOutputVector) ).
						mulRowVector(network.applySigmoidDeriv(network.getNETk()));
				
				DoubleMatrix Yj = network.applySigmoid(network.getNETj());
				//delta Wkj			(matrix of weight difference) 
				//(learning curve) * deltaK * f(NETj)
				DoubleMatrix deltaWkj = new DoubleMatrix(network.getHiddenLayerSize(), network.getOutputLayerSize());
				for(int i=0; i<deltaWkj.columns; i++)
					for(int j=0; j<deltaWkj.rows; j++)
						deltaWkj.put(j, i, learningRate*deltaK.get(0, i)*Yj.get(0, j) );
				
				//delta Wkbias
				//(learning curve) * deltaK * 1
				DoubleMatrix deltaWkbias = deltaK.mul(learningRate);
				
				//delta j 			(error at hidden layer)
				//sigmoid'(NETj) * (sum(Wkj) k=0 to n) * delta k
				DoubleMatrix deltaJ = network.applySigmoidDeriv(network.getNETj());
				for(int i=0; i<deltaJ.columns; i++) {
					double sum = 0.0;
					for(int j=0; j<actualOutputVector.length; j++)
						sum += network.getWkj().get(i,j)*deltaK.get(0,j);
					deltaJ.put(0, i, deltaJ.get(0,i)*sum);
				}
				
				DoubleMatrix ACTi = network.applySigmoid(inputVector);
				//delta Wji			(weight updates)
				//(learning curve) * (activation at input i) * delta j
				DoubleMatrix deltaWji = new DoubleMatrix(network.getInputLayerSize(), network.getHiddenLayerSize());
				for(int i=0; i<deltaWji.rows; i++)
					for(int j=0; j<deltaWji.columns; j++)
						deltaWji.put(i, j, learningRate*ACTi.get(0,i)*deltaJ.get(0,j));
				
				//delta Wjbias
				//(learning curve) * delta j
				DoubleMatrix deltaWjbias = deltaJ.mul(learningRate);
				
				/*
				System.out.println("SBP");
				System.out.println(deltaWji);
				System.out.println(deltaWjbias);
				System.out.println(deltaWkj);
				System.out.println(deltaWkbias);
				*/
				
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
			
			System.out.println("Error Test");
			/* Check error */
			DoubleMatrix errorVec = new DoubleMatrix(1, ttOutputs.get(0).columns);
			for(int i=0; i<errorVec.rows; i++)
				for(int j=0; j<errorVec.columns; j++)
					errorVec.put(i, j, 0);
			for(int i=0; i<ttOutputs.size(); i++) {
				if(ttOutputs.get(i) != null) {
					DoubleMatrix tmp = MatrixFunctions.pow(ttOutputs.get(i).sub(cActualOutputs.get(i)), 2);
					errorVec = errorVec.addi(tmp);
				}
			}
			errorVec.mmuli(0.5);
			System.out.println(errorVec);
			double error = 0;
			if(error < errorThreshold) {
				/* save best network so far to disk */
				network.writeNetworkToFile(error);
				return;
			}
		}
	}
}
