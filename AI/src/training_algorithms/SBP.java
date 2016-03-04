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
	private DoubleMatrix error;
	
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
	public DoubleMatrix getError() { return error; }
	
	//SBP method
	public void apply(TrainingData trainingData) {
		for(int epoch=0; epoch<params.getEpochs(); epoch++) { //epoch loop

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
				//System.out.println("Chosen Tuple: "+chosenTuple.getInputs());
				//System.out.println("Expected output: "+expectedOutputVector);
				//System.out.println("Actual output: "+actualOutputVector);
				
				/* Calculate Updates */
				DoubleMatrix deltaK = calcDeltaK(expectedOutputVector, actualOutputVector);
				List<DoubleMatrix> deltaJs = calcDeltaJs(deltaK, actualOutputVector);
				DoubleMatrix deltaWkj = calcDeltaWkj(deltaK, deltaJs.get(deltaJs.size()-1).columns, expectedOutputVector.columns);
				DoubleMatrix deltaWkbias = calcDeltaWkbias(deltaK);
				DoubleMatrix deltaWji = calcDeltaWji(deltaJs.get(0), inputVector, inputVector.columns, deltaJs.get(0).columns);
				List<DoubleMatrix> deltaWjbias = calcDeltaWjbias(deltaJs);
				List<DoubleMatrix> deltaWjs = calcDeltaWjs(deltaJs);
				
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
				//trainee.printAllEdges();
				
				firstPass = false;
			}
			
			/* Calculate error */
			DoubleMatrix error = calculateError(trainingData);
			System.out.println(error);

			/* save best network so far to disk */
			if(isBelowThreshold(error)) { //if below threshold
				this.error = error;
				trainee.saveToDisk(error);
				return;
			}
		}
	}
	
	private List<DoubleMatrix> calcDeltaWjs(List<DoubleMatrix> deltaJs) {
		double learningRate = params.getLearningRate();
		List<DoubleMatrix> deltaWjs = new ArrayList<DoubleMatrix>();
		List<DoubleMatrix> ACTjs = trainee.getACTjs();
		for(int i=0; i<deltaJs.size()-1; i++) {
			DoubleMatrix deltaWj = new DoubleMatrix(deltaJs.get(i).columns, deltaJs.get(i+1).columns);
			DoubleMatrix deltaJ = deltaJs.get(i);
			DoubleMatrix ACTj = ACTjs.get(i);
			for(int j=0; j<deltaWj.rows; j++) {
				for(int k=0; k<deltaWj.columns; k++) {
					deltaWj.put(j, k, learningRate*deltaJ.get(0, j)*ACTj.get(0, j));
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
		//System.out.println(deltaJs);
		//sigmoid'(NETj) * (sum(Wkj) k=0 to n) * delta k
		for(int i=deltaJs.size()-1; i>=0; i--) {
			DoubleMatrix deltaJ = deltaJs.get(i);
			for(int j=0; j<deltaJ.columns; j++) {
				double sum = 0.0;
				if(i==deltaJs.size()-1) {
					for(int k=0; k<actualOutputVector.length; k++)
						sum += trainee.getWkj().get(j,k)*deltaK.get(0,k);
					deltaJ.put(0, j, deltaJ.get(0,j)*sum);
				}
				else {
					for(int k=0; k<deltaJs.get(i).columns; k++)
						sum += trainee.getWjs().get(i).get(j,k)*deltaJs.get(i+1).get(0,k);
					deltaJ.put(0, j, deltaJ.get(0,j)*sum);
				}
			}
			deltaJs.set(i, deltaJ);
		}
		return deltaJs;
	}
	
	private DoubleMatrix calcDeltaWji(DoubleMatrix deltaJfirst, DoubleMatrix ACTi, int rows, int cols) {
		double learningRate = params.getLearningRate();
		//delta Wji			(weight updates)
		//(learning curve) * (activation at input i) * delta j
		DoubleMatrix deltaWji = new DoubleMatrix(rows, cols);
		for(int i=0; i<deltaWji.rows; i++)
			for(int j=0; j<deltaWji.columns; j++)
				deltaWji.put(i, j, learningRate*ACTi.get(0,i)*deltaJfirst.get(0,j));
		return deltaWji;
	}
	
	private List<DoubleMatrix> calcDeltaWjbias(List<DoubleMatrix> deltaJs) {
		double learningRate = params.getLearningRate();
		List<DoubleMatrix> deltaWjbias = new ArrayList<DoubleMatrix>();
		for(DoubleMatrix m : deltaJs) {
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
	
	private DoubleMatrix calculateError(TrainingData trainingData) {
		DoubleMatrix errorVec = DoubleMatrix.zeros(1, trainingData.getData().get(0).getOutputs().columns);
		for(TrainingTuple tt : trainingData.getData()) {
			DoubleMatrix inputVec = tt.getInputs();
			DoubleMatrix expectedOutputVec = tt.getOutputs();
			DoubleMatrix actualOutputVec = trainee.feedForward(inputVec);
			DoubleMatrix thisTupleError = MatrixFunctions.pow(expectedOutputVec.sub(actualOutputVec), 2);
			thisTupleError.mmuli(0.5);
			errorVec = errorVec.addRowVector(thisTupleError);
		}
		return errorVec;
	}
	
	private boolean isBelowThreshold(DoubleMatrix error) {
		boolean belowThresh = true;
		for(int i=0; i<error.rows; i++) 
			for(int j=0; j<error.columns; j++) 
				if(error.get(i,j) > params.getErrorThreshold()) {
					belowThresh = false;
				}
		return belowThresh;
	}
}
