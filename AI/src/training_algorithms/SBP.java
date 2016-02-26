package training_algorithms;

import java.util.ArrayList;
import java.util.Random;
import org.jblas.*;

import training_data.TrainingData;
import training_data.TrainingTuple;

/**
 * 
 * @author braem
 *
 * Stochastic Back Propagation Algorithm
 */
public class SBP
{
	//class params initialized to best values from experimentation
	private static int epochs = 5000;
	private static int trainingIterations = 3500;
	private static double errorThreshold = 0.0001;
	private static double learningRate = 0.30;
	private static double momentumRate = 0.65;
	private static SBPImpl trainee;
	private static double error;
	
	//keeping track of previous values
	private static DoubleMatrix deltaWkjPrev;
	private static DoubleMatrix deltaWkbiasPrev;
	private static DoubleMatrix deltaWjiPrev;
	private static DoubleMatrix deltaWjbiasPrev;
	
	//setters
	public static void setEpochs(int epochs) { SBP.epochs = epochs; }
	public static void setTrainingIterations(int trainingIterations) { SBP.trainingIterations = trainingIterations; }
	public static void setErrorThreshold(double errorThreshold) { SBP.errorThreshold = errorThreshold; }
	public static void setLearningRate(double learningRate) { SBP.learningRate = learningRate; }
	public static void setMomentumRate(double momentumRate) { SBP.momentumRate = momentumRate; }
	public static void setTrainee(SBPImpl sbpObj) { SBP.trainee = sbpObj; }
	
	//getters
	public static int getEpochs() { return epochs; }
	public static int getTrainingIterations() { return trainingIterations; }
	public static double getErrorThreshold() { return errorThreshold; }
	public static double getLearningRate() { return learningRate; }
	public static double getMomentumRate() { return momentumRate; }
	public static SBPImpl getTrainee() { return trainee; }
	public static double getError() { return error; }
	
	//SBP method
	public static void apply(TrainingData trainingData) {
		for(int epoch=0; epoch<epochs; epoch++) { //epoch loop
			
			//keep track of training tuple outputs
			ArrayList<DoubleMatrix> ttOutputs = new ArrayList<DoubleMatrix>();
			for(TrainingTuple tt : trainingData.getData())
				ttOutputs.add(tt.getAnswers());
			//keep track of best actual output corresponding to each tuple output
			ArrayList<DoubleMatrix> cActualOutputs = new ArrayList<DoubleMatrix>(ttOutputs.size());
			for(int i=0; i<ttOutputs.size(); i++)
				cActualOutputs.add(null);
			
			/* initialize trainee */
			trainee.init();
			
			boolean firstPass = true; //dont apply momentum on first pass
			
			for(int iter=0; iter<trainingIterations; iter++) { //training iteration loop
				/* pick a training tuple from trainer at random */
				Random rand = new Random();
				int randInt = rand.nextInt(trainingData.getData().size());
				TrainingTuple chosenTuple = trainingData.getData().get(randInt);
				DoubleMatrix inputVector = chosenTuple.getInputs();
				DoubleMatrix expectedOutputVector = chosenTuple.getAnswers();
				
				/* Get actual output from feed forward */
				DoubleMatrix actualOutputVector = trainee.feedForward(inputVector);
				setCorrespondingOutput(cActualOutputs, expectedOutputVector, actualOutputVector, randInt);
				
				/* Calculate Updates */
				DoubleMatrix deltaK = calcDeltaK(expectedOutputVector, actualOutputVector);
				DoubleMatrix deltaJ = calcDeltaJ(deltaK, actualOutputVector);
				DoubleMatrix deltaWkj = calcDeltaWkj(deltaK, deltaJ.columns, expectedOutputVector.columns);
				DoubleMatrix deltaWkbias = calcDeltaWkbias(deltaK);
				DoubleMatrix deltaWji = calcDeltaWji(deltaJ, inputVector, inputVector.columns, deltaJ.columns);
				DoubleMatrix deltaWjbias = calcDeltaWjbias(deltaJ);
				
				/* Apply Momentum */
				if(!firstPass)
					applyMomentum(deltaWji, deltaWjbias, deltaWkj, deltaWkbias);
				
				/* apply updates */
				applyUpdates(deltaWji, deltaWjbias, deltaWkj, deltaWkbias);
				
				//set the previous weight differences as these
				deltaWkjPrev = deltaWkj;
				deltaWkbiasPrev = deltaWkbias;
				deltaWjiPrev = deltaWji;
				deltaWjbiasPrev = deltaWjbias;
			
				firstPass = false;
			}
			
			/* Calculate error */
			double error = calculateError(ttOutputs, cActualOutputs);
			/* save best network so far to disk */
			if(error < errorThreshold) { //if below threshold
				trainee.saveToDisk(error);
				SBP.error = error;
				return;
			}
		}
	}
	
	private static void setCorrespondingOutput(ArrayList<DoubleMatrix> cActualOutputs,
			DoubleMatrix expectedOutputVector, DoubleMatrix actualOutputVector, int randInt) {
		if(cActualOutputs.get(randInt) == null) {
			cActualOutputs.set(randInt, actualOutputVector);
		}
		else if(expectedOutputVector.get(0,0) == -1) {
			double difference1 = -1 - actualOutputVector.get(0,0);
			double difference2 = -1 - cActualOutputs.get(randInt).get(0,0);
			if(difference1 > difference2) {
				cActualOutputs.set(randInt, actualOutputVector);
			}
		}
		else if(expectedOutputVector.get(0,0) == 1) {
			double difference1 = -1 - actualOutputVector.get(0,0);
			double difference2 = -1 - cActualOutputs.get(randInt).get(0,0);
			if(difference1 < difference2) {
				cActualOutputs.set(randInt, actualOutputVector);
			}
		}
	}
	
	private static DoubleMatrix calcDeltaK(DoubleMatrix expectedOutputVector, DoubleMatrix actualOutputVector) {
		//delta k			(error at output layer)
		//( (expect output k) - (actual output k) ) * sigmoid'(NETk)
		return ( expectedOutputVector.sub(actualOutputVector) ).
				mulRowVector(trainee.applySigmoidDeriv(trainee.getNETk()));
	}
	
	private static DoubleMatrix calcDeltaWkj(DoubleMatrix deltaK, int rows, int cols) {
		DoubleMatrix Yj =(trainee.getACTj());
		//delta Wkj			(matrix of weight difference) 
		//(learning rate) * deltaK * ACTj
		DoubleMatrix deltaWkj = new DoubleMatrix(rows, cols);
		for(int i=0; i<deltaWkj.columns; i++)
			for(int j=0; j<deltaWkj.rows; j++)
				deltaWkj.put(j, i, learningRate*deltaK.get(0, i)*Yj.get(0, j) );
		return deltaWkj;
	}
	
	private static DoubleMatrix calcDeltaWkbias(DoubleMatrix deltaK) {
		//(learning rate) * deltaK * 1
		return deltaK.mul(learningRate);
	}
	
	private static DoubleMatrix calcDeltaJ(DoubleMatrix deltaK, DoubleMatrix actualOutputVector) {
		//sigmoid'(NETj) * (sum(Wkj) k=0 to n) * delta k
		DoubleMatrix deltaJ = trainee.applySigmoidDeriv(trainee.getNETj());
		for(int i=0; i<deltaJ.columns; i++) {
			double sum = 0.0;
			for(int j=0; j<actualOutputVector.length; j++)
				sum += trainee.getWkj().get(i,j)*deltaK.get(0,j);
			deltaJ.put(0, i, deltaJ.get(0,i)*sum);
		}
		return deltaJ;
	}
	
	private static DoubleMatrix calcDeltaWji(DoubleMatrix deltaJ, DoubleMatrix inputVector, int rows, int cols) {
		//activation at inputs
		DoubleMatrix ACTi = (inputVector);
		//delta Wji			(weight updates)
		//(learning curve) * (activation at input i) * delta j
		DoubleMatrix deltaWji = new DoubleMatrix(rows, cols);
		for(int i=0; i<deltaWji.rows; i++)
			for(int j=0; j<deltaWji.columns; j++)
				deltaWji.put(i, j, learningRate*ACTi.get(0,i)*deltaJ.get(0,j));
		return deltaWji;
	}
	
	private static DoubleMatrix calcDeltaWjbias(DoubleMatrix deltaJ) {
		//(learning curve) * delta j
		return deltaJ.mul(learningRate);
	}
	
	private static void applyMomentum(DoubleMatrix deltaWji, DoubleMatrix deltaWjbias,
			DoubleMatrix deltaWkj, DoubleMatrix deltaWkbias) {
		deltaWkj = deltaWkj.mmul(1-momentumRate).add(deltaWkjPrev.mmul(momentumRate));
		deltaWkbias = deltaWkbias.mmul(1-momentumRate).add(deltaWkbiasPrev.mmul(momentumRate));
		deltaWji = deltaWji.mmul(1-momentumRate).add(deltaWjiPrev.mmul(momentumRate));
		deltaWjbias = deltaWjbias.mmul(1-momentumRate).add(deltaWjbiasPrev.mmul(momentumRate));
	}
	
	private static void applyUpdates(DoubleMatrix deltaWji, DoubleMatrix deltaWjbias,
			DoubleMatrix deltaWkj, DoubleMatrix deltaWkbias) {
		//delta Wkj
		trainee.applyWkjUpdate(deltaWkj);
		//delta Wkbias
		trainee.applyWkbiasUpdate(deltaWkbias);
		//delta Wji
		trainee.applyWjiUpdate(deltaWji);
		//delta Wjbias
		trainee.applyWjbiasUpdate(deltaWjbias);
	}
	
	public static double calculateError(ArrayList<DoubleMatrix> ttOutputs, ArrayList<DoubleMatrix> cActualOutputs) {
		DoubleMatrix errorVec = DoubleMatrix.zeros(1, ttOutputs.get(0).columns);
		for(int i=0; i<ttOutputs.size(); i++) {
			if(cActualOutputs.get(i) != null) {
				DoubleMatrix tmp = MatrixFunctions.pow(ttOutputs.get(i).sub(cActualOutputs.get(i)), 2);
				errorVec = errorVec.add(tmp);
			}
		}
		errorVec.mmuli(0.5);
		return errorVec.get(0,0);
	}
}
