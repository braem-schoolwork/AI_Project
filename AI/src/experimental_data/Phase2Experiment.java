package experimental_data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jblas.DoubleMatrix;

import neural_network.NeuralNetwork;
import training_algorithms.SBP;
import training_algorithms.SBPParams;
import training_data.TrainingData;
import training_data.TrainingTuple;

/**
 * Runs experiment from phase 2
 * 
 * @author braem
 * @version 1.0
 */
public class Phase2Experiment implements Experiment
{
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private static String LRMR_FILE_NAME = System.getProperty("user.dir")+"\\LRMR";
	private static String LRTI_FILE_NAME = System.getProperty("user.dir")+"\\LRTI";
	private static String MRTI_FILE_NAME = System.getProperty("user.dir")+"\\MRTI";
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
	public void runExperiment(String fileExtension) {
		LRMR_FILE_NAME += fileExtension;
		LRTI_FILE_NAME += fileExtension;
		MRTI_FILE_NAME += fileExtension;
		runExperimentMRTI();
		runExperimentLRTI();
		runExperimentLRMR();
	}
	
	private static void runExperimentMRTI() {
		setupTuples();
		List<String> contents = new ArrayList<String>();
		String firstRow = "MR|TI";
		boolean firstPass = true;
		for(double i=startingMomentumRate; i<=endingMomentumRate; i+=momentumRateIncrease) {
			String row = i+"";
			SBPParams sbpParams = new SBPParams();
			sbpParams.setMomentumRate(i);
			for(int j=startingTrainingIter; j<=endingTrainingIter; j+=trainingIterIncrease) {
				sbpParams.setTrainingIterations(j);
				double errorAvg = 0.0;
				for(int k=0; k<applySBPamount; k++) {
					SBP sbp = new SBP(sbpParams);
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
		try {
			writeToFile(contents, MRTI_FILE_NAME);
		} catch (IOException e) { }
	}
	
	private static void runExperimentLRTI() {
		setupTuples();
		List<String> contents = new ArrayList<String>();
		String firstRow = "LR|TI";
		boolean firstPass = true;
		for(double i=startingLearningRate; i<=endingLearningRate; i+=learningRateIncrease) {
			String row = i+"";
			SBPParams sbpParams = new SBPParams();
			sbpParams.setLearningRate(i);
			for(int j=startingTrainingIter; j<=endingTrainingIter; j+=trainingIterIncrease) {
				sbpParams.setTrainingIterations(j);
				double errorAvg = 0.0;
				for(int k=0; k<applySBPamount; k++) {
					SBP sbp = new SBP(sbpParams);
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
		try {
			writeToFile(contents, LRTI_FILE_NAME);
		} catch (IOException e) { }
	}
	
	private static void runExperimentLRMR() {
		setupTuples();
		List<String> contents = new ArrayList<String>();
		String firstRow = "LR|MR";
		boolean firstPass = true;
		for(double i=startingLearningRate; i<=endingLearningRate; i+=learningRateIncrease) {
			String row = i+"";
			SBPParams sbpParams = new SBPParams();
			sbpParams.setLearningRate(i);
			for(double j=startingMomentumRate; j<=endingMomentumRate; j+=momentumRateIncrease) {
				sbpParams.setMomentumRate(j);
				double errorAvg = 0.0;
				for(int k=0; k<applySBPamount; k++) {
					SBP sbp = new SBP(sbpParams);
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
		try {
			writeToFile(contents, LRMR_FILE_NAME);
		} catch (IOException e) { }
	}
	
	private static void writeToFile(List<String> lines, String filename) throws IOException {
		Path path = Paths.get(filename);
		Files.write(path, lines, ENCODING);
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
		return "Phase2";
	}
}
