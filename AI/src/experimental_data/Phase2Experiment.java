package experimental_data;

import java.util.ArrayList;
import java.util.List;

import org.jblas.DoubleMatrix;

import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkParams;
import training_algorithms.SBP;
import training_algorithms.SBPParams;
import training_data.TrainingData;
import training_data.TrainingTuple;

/**
 * Runs experiment from Phase 2
 * 
 * @author braemen
 * @version 1.0
 */
public class Phase2Experiment implements Experiment
{
	private static String fileNameLRMR = System.getProperty("user.dir")+"\\LRMR";
	private static String fileNameLRTI = System.getProperty("user.dir")+"\\LRTI";
	private static String fileNameMRTI = System.getProperty("user.dir")+"\\MRTI";
	private static TrainingData trainingData;
	
	private static double startingLearningRate = 0.05;
	private static double endingLearningRate = 0.95;
	private static double learningRateIncrease = 0.05;
	private static double startingMomentumRate = 0.05;
	private static double endingMomentumRate = 0.95;
	private static double momentumRateIncrease = 0.05;
	private static int startingTrainingIter = 500;
	private static int endingTrainingIter = 10000;
	private static int trainingIterIncrease = 500;
	private static double applySBPamount = 100;
	
	private static double defaultLearningRate = 0.3;
	private static double defaultMomentumRate = 0.3;
	private static int defaultTrainingIter = 3500;
	
	@Override
	public void runExperiment(String fileExtension, ExperimentSize size) {
		setParams(size);
		fileNameLRMR += fileExtension;
		fileNameLRTI += fileExtension;
		fileNameMRTI += fileExtension;
		runGeneralExp(startingMomentumRate, momentumRateIncrease, endingMomentumRate,
				startingTrainingIter, trainingIterIncrease, endingTrainingIter, "MR|TI", fileNameMRTI);
		runGeneralExp(startingLearningRate, learningRateIncrease, endingLearningRate,
				startingTrainingIter, trainingIterIncrease, endingTrainingIter, "LR|TI", fileNameLRTI);
		runGeneralExp(startingLearningRate, learningRateIncrease, endingLearningRate,
				startingMomentumRate, momentumRateIncrease, endingMomentumRate, "LR|MR", fileNameLRMR);
	}
	
	private static void setParams(ExperimentSize size) {
		switch(size) {
		case SMALL:
			startingLearningRate = 0.1;
			endingLearningRate = 0.5;
			learningRateIncrease = 0.1;
			startingMomentumRate = 0.1;
			endingMomentumRate = 0.5;
			momentumRateIncrease = 0.1;
			startingTrainingIter = 1000;
			endingTrainingIter = 10000;
			trainingIterIncrease = 1000;
			applySBPamount = 100;
			break;
		case MEDIUM:
			startingLearningRate = 0.1;
			endingLearningRate = 0.9;
			learningRateIncrease = 0.1;
			startingMomentumRate = 0.1;
			endingMomentumRate = 0.9;
			momentumRateIncrease = 0.1;
			startingTrainingIter = 1000;
			endingTrainingIter = 10000;
			trainingIterIncrease = 1000;
			applySBPamount = 100;
			break;
		case LARGE:
			startingLearningRate = 0.05;
			endingLearningRate = 0.95;
			learningRateIncrease = 0.05;
			startingMomentumRate = 0.05;
			endingMomentumRate = 0.95;
			momentumRateIncrease = 0.05;
			startingTrainingIter = 500;
			endingTrainingIter = 10000;
			trainingIterIncrease = 500;
			applySBPamount = 100;
			break;
		}
	}
	
	private static void runGeneralExp(double outerStart, double outerIncrease, double outerEnd,
			double innerStart, double innerIncrease, double innerEnd, String descStr, String fileName) {
		setupTuples();
		List<String> contents = new ArrayList<String>();
		String firstRow = descStr;
		boolean firstPass = true;
		for(double i=outerStart; i<=outerEnd; i+=outerIncrease) {
			String row = i+"";
			SBPParams sbpParams = new SBPParams();
			if(descStr.equals("MR|TI"))
				sbpParams.setMomentumRate(i);
			else
				sbpParams.setLearningRate(i);
			for(double j=innerStart; j<=innerEnd; j+=innerIncrease) {
				if(descStr.equals("LR|MR"))
					sbpParams.setMomentumRate(j);
				else
					sbpParams.setTrainingIterations((int)j);
				double errorAvg = 0.0;
				for(int k=0; k<applySBPamount; k++) {
					NeuralNetwork NN = new NeuralNetwork(new NeuralNetworkParams());
					SBP sbp = new SBP(sbpParams);
					sbp.setTrainee(NN);
					sbp.apply(trainingData);
					errorAvg += sbp.getError().get(0,0);
				}
				errorAvg /= applySBPamount;
				if(firstPass) firstRow += ","+j;
				row += ","+errorAvg;
			}
			contents.add(row);
			firstPass = false;
		}
		contents.add(0, firstRow);
		ExperimentIO.writeToFileTrunc(contents, fileName);
	}
	
	private static void setupTuples() {
		NeuralNetwork NN = new NeuralNetwork();
		SBP sbp = new SBP();
		SBPParams sbpParams = new SBPParams();
		sbp.setTrainee(NN);
		TrainingTuple t1 = new TrainingTuple(new DoubleMatrix(new double[][] {{-1,1}}), new DoubleMatrix(new double[][] {{1}}));
		TrainingTuple t2 = new TrainingTuple(new DoubleMatrix(new double[][] {{1,1}}), new DoubleMatrix(new double[][] {{-1}}));
		TrainingTuple t3 = new TrainingTuple(new DoubleMatrix(new double[][] {{-1,-1}}), new DoubleMatrix(new double[][] {{-1}}));
		TrainingTuple t4 = new TrainingTuple(new DoubleMatrix(new double[][] {{1,-1}}), new DoubleMatrix(new double[][] {{1}}));
		ArrayList<TrainingTuple> tuples = new ArrayList<TrainingTuple>();
		tuples.add(t1); 
		tuples.add(t2); 
		tuples.add(t3); 
		tuples.add(t4); 
		TrainingData data = new TrainingData(tuples);
		trainingData = data;
		sbp.apply(data);
		sbpParams.setEpochs(1);
		sbpParams.setLearningRate(defaultLearningRate);
		sbpParams.setMomentumRate(defaultMomentumRate);
		sbpParams.setTrainingIterations(defaultTrainingIter);
		sbpParams.setErrorThreshold(Double.MAX_VALUE);
		sbp.setParams(sbpParams);
	}

	@Override
	public String toString() {
		return "Phase 2";
	}
}
