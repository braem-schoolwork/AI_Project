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
import training_data.TrainingData;
import training_data.TrainingTuple;

public class Phase2Experiment
{
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private final static String LRMR_FILE_NAME = System.getProperty("user.dir")+"\\LRMR.csv";
	private final static String LRTR_FILE_NAME = System.getProperty("user.dir")+"\\LRTR.csv";
	private final static String MRTR_FILE_NAME = System.getProperty("user.dir")+"\\MRTR.csv";
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
	private static int defaultTrainingIter = 1000;
	
	public static void runExperimentMRTR() {
		setupTuples();
		List<String> contents = new ArrayList<String>();
		String firstRow = "LearningRate|TrainingIterations";
		for(double i=startingMomentumRate; i<=endingMomentumRate; i+=momentumRateIncrease) {
			String row = i+"";
			SBP.setMomentumRate(i);
			for(int j=startingTrainingIter; j<=endingTrainingIter; j+=trainingIterIncrease) {
				SBP.setTrainingIterations(j);
				double errorAvg = 0.0;
				for(int k=0; k<applySBPamount; k++) {
					SBP.apply(trainingData);
					errorAvg += SBP.getError();
				}
				errorAvg /= applySBPamount;
				firstRow += ","+j;
				row += ","+errorAvg;
			}
			contents.add(row);
			System.out.println(i);
		}
		contents.add(0, firstRow);
		try {
			writeToFile(contents, MRTR_FILE_NAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void runExperimentLRTR() {
		setupTuples();
		List<String> contents = new ArrayList<String>();
		String firstRow = "LearningRate|TrainingIterations";
		for(double i=startingLearningRate; i<=endingLearningRate; i+=learningRateIncrease) {
			String row = i+"";
			SBP.setLearningRate(i);
			for(int j=startingTrainingIter; j<=endingTrainingIter; j+=trainingIterIncrease) {
				SBP.setTrainingIterations(j);
				double errorAvg = 0.0;
				for(int k=0; k<applySBPamount; k++) {
					SBP.apply(trainingData);
					errorAvg += SBP.getError();
				}
				errorAvg /= applySBPamount;
				firstRow += ","+j;
				row += ","+errorAvg;
			}
			contents.add(row);
			System.out.println(i);
		}
		contents.add(0, firstRow);
		try {
			writeToFile(contents, LRTR_FILE_NAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void runExperimentLRMR() {
		setupTuples();
		List<String> contents = new ArrayList<String>();
		String firstRow = "LearningRate|MomentumRate";
		for(double i=startingLearningRate; i<=endingLearningRate; i+=learningRateIncrease) {
			String row = i+"";
			SBP.setLearningRate(i);
			for(double j=startingMomentumRate; j<=endingMomentumRate; j+=momentumRateIncrease) {
				SBP.setMomentumRate(j);
				double errorAvg = 0.0;
				for(int k=0; k<applySBPamount; k++) {
					SBP.apply(trainingData);
					errorAvg += SBP.getError();
				}
				errorAvg /= applySBPamount;
				firstRow += ","+j;
				row += ","+errorAvg;
			}
			contents.add(row);
			System.out.println(i);
		}
		contents.add(0, firstRow);
		try {
			writeToFile(contents, LRMR_FILE_NAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeToFile(List<String> lines, String filename) throws IOException {
		Path path = Paths.get(filename);
		Files.write(path, lines, ENCODING);
	}
	
	private static void setupTuples() {
		NeuralNetwork NN = new NeuralNetwork();
		SBP.setSBPImpl(NN);
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
		SBP.apply(data);
		SBP.setEpochs(1);
		SBP.setLearningRate(defaultLearningRate);
		SBP.setMomentumRate(defaultMomentumRate);
		SBP.setTrainingIterations(defaultTrainingIter);
		SBP.setErrorThreshold(Double.MAX_VALUE);
	}
}
