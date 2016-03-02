package training_algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jblas.*;

import training_data.TrainingData;
import training_data.TrainingTuple;

/**
 * 
 * @author braem
 *
 * Stochastic Back Propagation Algorithm
 * 
 */
public class SBP
{
	//class params initialized to best values from experimentation
	private SBPParams params;
	private SBPImpl trainee;
	private double error;
	
	//keeping track of previous values
	private static DoubleMatrix deltaWkjPrev;
	private static DoubleMatrix deltaWkbiasPrev;
	private static DoubleMatrix deltaWjiPrev;
	private static List<DoubleMatrix> deltaWjbiasPrev;
	private static List<DoubleMatrix> deltaWjsPrev;
	
	public SBP() {
		params = new SBPParams();
	}
	public SBP(SBPParams params) {
		this.params = params;
	}
	
	//setters
	public void setParams(SBPParams params) { this.params = params; }
	public void setTrainee(SBPImpl trainee) { this.trainee = trainee; }
	
	//getters
	public SBPParams getParams() { return params; }
	public SBPImpl getTrainee() { return trainee; }
	public double getError() { return error; }
	
	//SBP method
	public void apply(TrainingData trainingData) {
		for(int epoch=0; epoch<params.getEpochs(); epoch++) { //epoch loop
			//keep track of training tuple outputs
			List<DoubleMatrix> ttOutputs = new ArrayList<DoubleMatrix>();
			for(TrainingTuple tt : trainingData.getData())
				ttOutputs.add(tt.getOutputs());
			//keep track of best actual output corresponding to each tuple output
			List<DoubleMatrix> cActualOutputs = new ArrayList<DoubleMatrix>(ttOutputs.size());
			for(int i=0; i<ttOutputs.size(); i++)
				cActualOutputs.add(null);
			/* initialize trainee */
			trainee.init();
			boolean firstPass = true; //dont apply momentum on first pass
			for(int iter=0; iter<params.getTrainingIterations(); iter++) { //training iteration loop
				/* pick a training tuple from trainer at random */
				Random rand = new Random();
				int randInt = rand.nextInt(trainingData.getData().size());
				TrainingTuple chosenTuple = trainingData.getData().get(randInt);
				DoubleMatrix inputVector = chosenTuple.getInputs();
				DoubleMatrix expectedOutputVector = chosenTuple.getOutputs();
				
				/* Get actual output from feed forward */
				DoubleMatrix actualOutputVector = trainee.feedForward(inputVector);
				setCorrespondingOutput(cActualOutputs, expectedOutputVector, actualOutputVector, randInt);
				
				/* Calculate Updates */
				DoubleMatrix deltaK = calcDeltaK(expectedOutputVector, actualOutputVector);
				List<DoubleMatrix> deltaJs = calcDeltaJs(deltaK, actualOutputVector);
				DoubleMatrix deltaWkj = calcDeltaWkj(deltaK, deltaJs.get(deltaJs.size()-1).columns, expectedOutputVector.columns);
				DoubleMatrix deltaWkbias = calcDeltaWkbias(deltaK);
				DoubleMatrix deltaWji = calcDeltaWji(deltaJs.get(0), inputVector, inputVector.columns, deltaJs.get(0).columns);
				List<DoubleMatrix> deltaWjbias = calcDeltaWjbias(deltaJs);
				List<DoubleMatrix> deltaWjs = calcDeltaWjs(deltaJs);
				
				//System.out.println(deltaK);
				//System.out.println(deltaJs);
				
				/* Apply Momentum */
				if(!firstPass)
					applyMomentum(deltaWji, deltaWjbias, deltaWkj, deltaWkbias, deltaWjs);
				
				/* apply updates */
				applyUpdates(deltaWji, deltaWjbias, deltaWkj, deltaWkbias, deltaWjs);
				
				//set the previous weight differences as these
				deltaWkjPrev = deltaWkj;
				deltaWkbiasPrev = deltaWkbias;
				deltaWjiPrev = deltaWji;
				deltaWjbiasPrev = deltaWjbias;
				deltaWjsPrev = deltaWjs;
			
				firstPass = false;
			}
			
			/* Calculate error */
			double error = calculateError(ttOutputs, cActualOutputs);
			System.out.println(ttOutputs+""+cActualOutputs);
			/* save best network so far to disk */
			if(error < params.getErrorThreshold()) { //if below threshold
				trainee.saveToDisk(error);
				this.error = error;
				return;
			}
		}
	}
	
	private void setCorrespondingOutput(List<DoubleMatrix> cActualOutputs,
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
	
	private List<DoubleMatrix> calcDeltaWjs(List<DoubleMatrix> deltaJs) {
		double learningRate = params.getLearningRate();
		List<DoubleMatrix> deltaWjs = new ArrayList<DoubleMatrix>();
		List<DoubleMatrix> ACTjs = trainee.getACTjs();
		for(int i=0; i<deltaJs.size()-2; i++) {
			DoubleMatrix deltaWj = new DoubleMatrix(deltaJs.get(i).columns, deltaJs.get(i+1).columns); //TODO
			DoubleMatrix deltaJ = deltaJs.get(i);
			DoubleMatrix ACTj = ACTjs.get(i);
			for(int j=0; j<deltaWj.columns; j++) {
				for(int k=0; k<deltaWj.rows; k++) {
					deltaWj.put(k, j, learningRate*deltaJ.get(0, j)*ACTj.get(0, k));
				}
			}
			deltaWjs.add(deltaWj);
		}
		return deltaWjs;
	}
	
	private DoubleMatrix calcDeltaK(DoubleMatrix expectedOutputVector, DoubleMatrix actualOutputVector) {
		//delta k			(error at output layer)
		//( (expect output k) - (actual output k) ) * sigmoid'(NETk)
		return ( expectedOutputVector.sub(actualOutputVector) ).
				mulRowVector(trainee.applySigmoidDeriv(trainee.getNETk()));
	}
	
	private DoubleMatrix calcDeltaWkj(DoubleMatrix deltaK, int rows, int cols) {
		double learningRate = params.getLearningRate();
		List<DoubleMatrix> ACTjs = trainee.getACTjs();
		DoubleMatrix Yj = ACTjs.get(ACTjs.size()-1);
		//delta Wkj			(matrix of weight difference) 
		//(learning rate) * deltaK * ACTj
		DoubleMatrix deltaWkj = new DoubleMatrix(rows, cols);
		for(int i=0; i<deltaWkj.columns; i++)
			for(int j=0; j<deltaWkj.rows; j++)
				deltaWkj.put(j, i, learningRate*deltaK.get(0, i)*Yj.get(0, j) );
		return deltaWkj;
	}
	
	private DoubleMatrix calcDeltaWkbias(DoubleMatrix deltaK) {
		double learningRate = params.getLearningRate();
		//(learning rate) * deltaK * 1
		return deltaK.mul(learningRate);
	}
	
	private List<DoubleMatrix> calcDeltaJs(DoubleMatrix deltaK, DoubleMatrix actualOutputVector) {
		List<DoubleMatrix> NETjs = trainee.getNETjs();
		List<DoubleMatrix> deltaJs = new ArrayList<DoubleMatrix>();
		for(DoubleMatrix m : NETjs) {
			deltaJs.add(trainee.applySigmoidDeriv(m));
		}
		//sigmoid'(NETj) * (sum(Wkj) k=0 to n) * delta k
		//TODO fix
		for(int i=deltaJs.size()-1; i>0; i--) {
			DoubleMatrix deltaJ = deltaJs.get(i);
			for(int j=0; j<deltaJ.columns; j++) {
				double sum = 0.0;
				if(i==deltaJs.size()) {
					for(int k=0; k<actualOutputVector.length; k++)
						sum += trainee.getWkj().get(j,k)*deltaK.get(0,k);
					deltaJ.put(0, j, deltaJ.get(0,j)*sum);
				}
				else {
					for(int k=0; k<deltaJs.get(i+1).columns; k++)
						sum += trainee.getWjs().get(i).get(j,k)*deltaJs.get(i+1).get(0,k);
					deltaJ.put(0, j, deltaJ.get(0,j)*sum);
				}
			}
			deltaJs.set(i, deltaJ);
		}
		return deltaJs;
	}
	
	private DoubleMatrix calcDeltaWji(DoubleMatrix deltaJfirst, DoubleMatrix inputVector, int rows, int cols) {
		double learningRate = params.getLearningRate();
		//activation at inputs
		DoubleMatrix ACTi = inputVector;
		//delta Wji			(weight updates)
		//(learning curve) * (activation at input i) * delta j
		DoubleMatrix deltaWji = new DoubleMatrix(rows, cols);
		for(int i=0; i<deltaWji.rows; i++)
			for(int j=0; j<deltaWji.columns; j++)
				deltaWji.put(i, j, learningRate*ACTi.get(0,i)*deltaJfirst.get(0,j));
		return deltaWji;
	}
	
	private List<DoubleMatrix> calcDeltaWjbias(List<DoubleMatrix> deltaJ) {
		double learningRate = params.getLearningRate();
		List<DoubleMatrix> deltaWjbias = new ArrayList<DoubleMatrix>();
		for(DoubleMatrix m : deltaJ) {
			deltaWjbias.add(m.mmul(learningRate));
		}
		//(learning curve) * delta j
		return deltaWjbias;
	}
	
	private void applyMomentum(DoubleMatrix deltaWji, List<DoubleMatrix> deltaWjbias,
			DoubleMatrix deltaWkj, DoubleMatrix deltaWkbias, List<DoubleMatrix> deltaWjs) {
		double momentumRate = params.getMomentumRate();
		deltaWkj = deltaWkj.mmul(1-momentumRate).add(deltaWkjPrev.mmul(momentumRate));
		deltaWkbias = deltaWkbias.mmul(1-momentumRate).add(deltaWkbiasPrev.mmul(momentumRate));
		deltaWji = deltaWji.mmul(1-momentumRate).add(deltaWjiPrev.mmul(momentumRate));
		List<DoubleMatrix> newDeltaWjbias = new ArrayList<DoubleMatrix>();
		for(int i=0; i<deltaWjbias.size(); i++) {
			newDeltaWjbias.add(deltaWjbias.get(i).mmul(1-momentumRate).add(deltaWjbiasPrev.get(i).mmul(momentumRate)));
		}
		deltaWjbias = newDeltaWjbias;
		List<DoubleMatrix> newDeltaWjs = new ArrayList<DoubleMatrix>();
		for(int i=0; i<deltaWjs.size(); i++) {
			newDeltaWjs.add(deltaWjs.get(i).mmul(1-momentumRate).add(deltaWjsPrev.get(i).mmul(momentumRate)));
		}
		deltaWjs = newDeltaWjs;
	}
	
	private void applyUpdates(DoubleMatrix deltaWji, List<DoubleMatrix> deltaWjbias,
			DoubleMatrix deltaWkj, DoubleMatrix deltaWkbias, List<DoubleMatrix> deltaWjs) {
		//delta Wkj
		trainee.applyWkjUpdate(deltaWkj);
		//delta Wkbias
		trainee.applyWkbiasUpdate(deltaWkbias);
		//delta Wji
		trainee.applyWjiUpdate(deltaWji);
		//delta Wjbias
		trainee.applyWjbiasUpdate(deltaWjbias);
		//delta Wjs
		trainee.applyWjsUpdate(deltaWjs);
	}
	
	static double calculateError(List<DoubleMatrix> ttOutputs, List<DoubleMatrix> cActualOutputs) {
		DoubleMatrix errorVec = DoubleMatrix.zeros(1, ttOutputs.get(0).columns);
		for(int i=0; i<ttOutputs.size(); i++) {
			if(cActualOutputs.get(i) != null) {
				DoubleMatrix tmp = MatrixFunctions.pow(ttOutputs.get(i).sub(cActualOutputs.get(i)), 2);
				errorVec = errorVec.add(tmp);
			}
		}
		errorVec.mmuli(0.5);
		double error = 0.0;
		for(int i=0; i<errorVec.columns; i++) {
			error += errorVec.get(0,i);
		}
		return error;
	}
}
