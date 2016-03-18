package experimental_data;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;

import org.jblas.DoubleMatrix;

import matrix_wrapper.MatrixFunctionWrapper;
import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkParams;
import training_algorithms.SBP;
import training_algorithms.SBPParams;
import training_data.TrainingData;
import training_data.TrainingDataGenerator;

/**
 * Experiment for Phase 3
 * 
 * @author braemen
 * @version 1.0
 */
public class Phase3Experiment implements Experiment
{
	private static String fileNameETI = System.getProperty("user.dir")+"\\ETI";
	private static String fileNameHLSLR = System.getProperty("user.dir")+"\\HLSLR";
	private static String fileNameHLSMR = System.getProperty("user.dir")+"\\HLSMR";
	private static String fileNameLRMR = System.getProperty("user.dir")+"\\LRMR";
	
	private static int startingHiddenLayerSize = 10;
	private static int endingHiddenLayerSize = 65;
	private static int hiddenLayerSizeIncrease = 5;
	
	private static int startingEpochs = 20;
	private static int endingEpochs = 100;
	private static int epochsIncrease = 20;
	
	private static double startingLearningRate = 0.05;
	private static double endingLearningRate = 0.3;
	private static double learningRateIncrease = 0.1;
	
	private static double startingMomentumRate = 0.0;
	private static double endingMomentumRate = 0.30;
	private static double momentumRateIncrease = 0.1;
	
	private static int startingTrainingIter;
	private static int endingTrainingIter;
	private static int trainingIterIncrease;
	
	private static double errorThreshold = 0;
	private static double defaultLR = 0.01;
	private static double defaultMR = 0.01;
	private static int defaultHiddenLayerSizes = 36;
	private static int bestFoundEpochs;
	private static int bestFoundTIs;
	volatile boolean finished = true;
	
	private static final int AMOUNT_OF_PARAMS = 5;
	
	private PriorityQueue<NNSBPParam> NNSBPparams = new PriorityQueue<NNSBPParam>();
	private ArrayList<NNSBPParam> bestNNSBPparams = new ArrayList<NNSBPParam>();
	
	@Override
	public void runExperiment(String fileExtension, ExperimentSize size) {
		fileNameETI += fileExtension;
		TrainingData td = TrainingDataGenerator.genFromFile();
		if(td == null) {
			Phase1Experiment exp = new Phase1Experiment();
			exp.runExperiment(".csv", ExperimentSize.SMALL);
			td = TrainingDataGenerator.genFromFile();
		}
		setupParams(size, td);
		findBestEpochTIs(td, "E|TI", fileNameETI);
		computeBestSBPParams();
		for(int i=0; i<bestNNSBPparams.size(); i++) {
			runGeneralExp(td, startingHiddenLayerSize, endingHiddenLayerSize, hiddenLayerSizeIncrease,
					startingLearningRate, endingLearningRate, learningRateIncrease, "HLS|LR", fileNameHLSLR+i+fileExtension,
					bestNNSBPparams.get(i).sbpParams.getEpochs(), bestNNSBPparams.get(i).sbpParams.getTrainingIterations());
			runGeneralExp(td, startingHiddenLayerSize, endingHiddenLayerSize, hiddenLayerSizeIncrease,
					startingMomentumRate, endingMomentumRate, momentumRateIncrease, "HLS|MR", fileNameHLSMR+i+fileExtension,
					bestNNSBPparams.get(i).sbpParams.getEpochs(), bestNNSBPparams.get(i).sbpParams.getTrainingIterations());
			runGeneralExp(td, startingLearningRate, endingLearningRate, learningRateIncrease,
					startingMomentumRate, endingMomentumRate, momentumRateIncrease, "LR|MR", fileNameLRMR+i+fileExtension,
					bestNNSBPparams.get(i).sbpParams.getEpochs(), bestNNSBPparams.get(i).sbpParams.getTrainingIterations());
		}
	}
	
	private void findBestEpochTIs(TrainingData td, String descStr, String fileName) {
		NNSBPparams.clear();
		List<String> contents = new ArrayList<String>();
		String firstRow = descStr;
		boolean firstPass = true;
		for(int e=startingEpochs; e<=endingEpochs; e+=epochsIncrease) {
			String row = e+"";
			for(int ti=startingTrainingIter; ti<=endingTrainingIter; ti+=trainingIterIncrease) {
				ArrayList<Integer> hiddenLayerSizes = new ArrayList<Integer>();
				hiddenLayerSizes.add(defaultHiddenLayerSizes);
				hiddenLayerSizes.add(defaultHiddenLayerSizes);
				NeuralNetworkParams NNparams = new NeuralNetworkParams(1.0, 
						td.getData().get(0).getInputs().columns, hiddenLayerSizes, 
						td.getData().get(0).getOutputs().columns);
				NeuralNetwork NN = new NeuralNetwork(NNparams);
				SBPParams sbpParams = new SBPParams(e, ti, errorThreshold, defaultLR, defaultMR);
				SBP sbp = new SBP(sbpParams, NN);
				sbp.apply(td);
				DoubleMatrix error = sbp.getError();
				NNSBPparams.add(new NNSBPParam(error, NNparams, sbpParams));
				System.out.println("e"+e);
				System.out.println("ti"+ti);
				if(firstPass) firstRow += ","+ti;
				row += ","+MatrixFunctionWrapper.avgValues(error);
			}
			contents.add(row);
			firstPass = false;
		}
		contents.add(0, firstRow);
		contents.add("With HLS = "+defaultHiddenLayerSizes);
		contents.add("LR = "+defaultLR);
		contents.add("MR = "+defaultMR);
		ExperimentIO.writeToFile(contents, fileName);
	}
	
	private static void runGeneralExp(TrainingData trainingData, double outerStart, double outerEnd, double outerIncrease,
			double innerStart, double innerEnd, double innerIncrease, String descStr, String fileName, int epochs, int TIs) {
		List<String> contents = new ArrayList<String>();
		String firstRow = descStr;
		boolean firstPass = true;
		for(double i=outerStart; i<=outerEnd; i+=outerIncrease) {
			String row = i+"";
			SBPParams sbpParams = new SBPParams(epochs, TIs, errorThreshold, defaultLR, defaultMR);
			NeuralNetworkParams NNparams;
			if(descStr.equals("LR|MR")) { //set learning rate
				sbpParams.setLearningRate(i);
				ArrayList<Integer> hLSs = new ArrayList<Integer>();
				hLSs.add(defaultHiddenLayerSizes);
				hLSs.add(defaultHiddenLayerSizes);
				NNparams = new NeuralNetworkParams(1.0, trainingData.getData().get(0).getInputs().columns, hLSs, 
							trainingData.getData().get(0).getOutputs().columns);
			}
			else { //set hidden layer sizes
				ArrayList<Integer> hLSs = new ArrayList<Integer>();
				hLSs.add((int)i);
				hLSs.add((int)i);
				NNparams = new NeuralNetworkParams(1.0, trainingData.getData().get(0).getInputs().columns, hLSs, 
						trainingData.getData().get(0).getOutputs().columns);
			}
			for(double j=innerStart; j<=innerEnd; j+=innerIncrease) {
				if(descStr.equals("HLS|LR"))
					sbpParams.setLearningRate(j);
				else
					sbpParams.setMomentumRate(j);
				NeuralNetwork NN = new NeuralNetwork(NNparams);
				SBP sbp = new SBP(sbpParams);
				sbp.setTrainee(NN);
				sbp.apply(trainingData);
				if(firstPass) firstRow += ","+j;
				row += ","+MatrixFunctionWrapper.avgValues(sbp.getError());
				System.out.println(i+" "+j);
			}
			contents.add(row);
			firstPass = false;
		}
		contents.add(0, firstRow);
		contents.add("With Epochs = "+epochs);
		contents.add("With Training Iterations = "+TIs);
		if(descStr.equals("HLS|LR"))
			contents.add("With MomentumRate = "+defaultMR);
		else if(descStr.equals("HLS|MR"))
			contents.add("With Learning Rate = "+defaultLR);
		else if(descStr.equals("LR|MR"))
			contents.add("With HLS = "+defaultHiddenLayerSizes);
		ExperimentIO.writeToFile(contents, fileName);
	}
	
	/**
	 * @return Best NeuralNetwork parameters from experiment
	 */
	public ArrayList<NeuralNetworkParams> getNNParams() {
		ArrayList<NeuralNetworkParams> p = new ArrayList<NeuralNetworkParams>();
		for(NNSBPParam s : NNSBPparams)
			p.add(0,s.NNparams);
		return p;
	}
	/**
	 * @return Best SBP parameters from experiment
	 */
	public ArrayList<SBPParams> getSBPParams() {
		ArrayList<SBPParams> p = new ArrayList<SBPParams>();
		for(NNSBPParam s : NNSBPparams)
			p.add(0,s.sbpParams);
		return p;
	}
	/**
	 * @return Best errors from experiment
	 */
	public ArrayList<DoubleMatrix> getErrors() {
		ArrayList<DoubleMatrix> p = new ArrayList<DoubleMatrix>();
		for(NNSBPParam s : NNSBPparams)
			p.add(0,s.error);
		return p;
	}
	
	private void computeBestSBPParams() {
		int[] bestEpochs = new int[AMOUNT_OF_PARAMS];
		int[] bestTIs = new int[AMOUNT_OF_PARAMS];
		for(int i=0; i<AMOUNT_OF_PARAMS; i++) {
			//get the lowest error & then remove
			bestNNSBPparams.add(NNSBPparams.peek());
			int bestEpoch = NNSBPparams.peek().sbpParams.getEpochs();
			int bestTI = NNSBPparams.poll().sbpParams.getTrainingIterations();
			bestEpochs[i] = bestEpoch;
			bestTIs[i] = bestTI;
		}
		//set default epochs/TIs based on findings
		bestFoundEpochs = getPopularElement(bestEpochs);
		bestFoundTIs = getPopularElement(bestTIs);
	}
	
	private int getPopularElement(int[] a) {
		int count = 1, tempCount;
		int popular = a[0];
		int temp = 0;
		for(int i = 0; i < (a.length - 1); i++) {
			temp = a[i];
			tempCount = 0;
		    for(int j = 1; j < a.length; j++)
		    {
		    	if (temp == a[j])
		    	tempCount++;
		    }
		    if(tempCount > count)
		    {
		    	popular = temp;
		    	count = tempCount;
		    }
		}
		return popular;
	}
	
	private static void setupParams(ExperimentSize size, TrainingData td) {
		switch(size) {
		case SMALL:
			startingHiddenLayerSize = 20;
			endingHiddenLayerSize = 40;
			hiddenLayerSizeIncrease = 20;
			startingEpochs = 10;
			endingEpochs = 30;
			epochsIncrease = 10;
			startingLearningRate = 0.01;
			endingLearningRate = 0.03;
			learningRateIncrease = 0.01;
			startingMomentumRate = 0.01;
			endingMomentumRate = 0.03;
			momentumRateIncrease = 0.01;
			startingTrainingIter = td.getData().size();
			endingTrainingIter = td.getData().size()*1000;
			trainingIterIncrease = td.getData().size()*100;
			break;
		case MEDIUM:
			startingHiddenLayerSize = 10;
			endingHiddenLayerSize = 40;
			hiddenLayerSizeIncrease = 10;
			startingEpochs = 10;
			endingEpochs = 50;
			epochsIncrease = 10;
			startingLearningRate = 0.01;
			endingLearningRate = 0.05;
			learningRateIncrease = 0.01;
			startingMomentumRate = 0.01;
			endingMomentumRate = 0.05;
			momentumRateIncrease = 0.01;
			startingTrainingIter = td.getData().size();
			endingTrainingIter = td.getData().size()*1000;
			trainingIterIncrease = td.getData().size()*100;
			break;
		case LARGE:
			startingHiddenLayerSize = 20;
			endingHiddenLayerSize = 60;
			hiddenLayerSizeIncrease = 10;
			startingEpochs = 10;
			endingEpochs = 60;
			epochsIncrease = 10;
			startingLearningRate = 0.01;
			endingLearningRate = 0.1;
			learningRateIncrease = 0.01;
			startingMomentumRate = 0.01;
			endingMomentumRate = 0.1;
			momentumRateIncrease = 0.01;
			startingTrainingIter = td.getData().size();
			endingTrainingIter = td.getData().size()*1000;
			trainingIterIncrease = td.getData().size()*100;
			break;
		}
	}
	
	@Override
	public String toString() {
		return "Phase3";
	}
	
	class NNSBPParam implements Comparable<NNSBPParam> {
		DoubleMatrix error;
		NeuralNetworkParams NNparams;
		SBPParams sbpParams;
		public NNSBPParam() {
			error = new DoubleMatrix(1,2);
			error.fill(Double.MAX_VALUE);
			NNparams = null;
			sbpParams = null;
		}
		public NNSBPParam(DoubleMatrix error, NeuralNetworkParams NNparams, SBPParams sbpParams) {
			this.error = error;
			this.NNparams = NNparams;
			this.sbpParams = sbpParams;
		}
		@Override
		public int compareTo(NNSBPParam arg0) {
			if(MatrixFunctionWrapper.lessThanRowVec(this.error, arg0.error)) return -10;
			else if(MatrixFunctionWrapper.lessThanRowVec(arg0.error, this.error)) return 10;
			else return 0;
		}
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof NNSBPParam) {
				NNSBPParam p = (NNSBPParam)obj;
				if(this.error.equals(p.error) && this.NNparams.equals(p.NNparams) && this.sbpParams.equals(p.sbpParams)) {
					return true;
				}
				else return false;
			}
			else return false;
		}
		@Override
		public String toString() {
			return ""+error;
		}
	}
}
