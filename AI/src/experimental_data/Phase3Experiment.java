package experimental_data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

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
	
	private static double defaultLR = 0.01;
	private static double defaultMR = 0.01;
	private static int defaultHiddenLayerSizes = 36;
	private static int defaultEpochs;
	private static int defaultTIs;
	
	private static final int AMOUNT_OF_PARAMS = 5;
	
	private PriorityQueue<NNSBPParam> NNSBPparams = new PriorityQueue<NNSBPParam>();
	private ArrayList<NNSBPParam> bestNNSBPparams = new ArrayList<NNSBPParam>();
	
	@Override
	public void runExperiment(String fileExtension, ExperimentSize size) {
		fileNameETI += fileExtension;
		fileNameHLSLR += fileExtension;
		fileNameHLSMR += fileExtension;
		fileNameLRMR += fileExtension;
		TrainingData td = TrainingDataGenerator.genFromFile();
		if(td == null) {
			Phase1Experiment exp = new Phase1Experiment();
			exp.runExperiment(".csv", ExperimentSize.LARGE);
			td = TrainingDataGenerator.genFromFile();
		}
		setupParams(size, td);
		runExpSBPParams(td, "E|TI", fileNameETI);
		computeBestSBPParams();
		for(NNSBPParam p : bestNNSBPparams) {
			runExpNNParams(td, p.sbpParams.getEpochs(), p.sbpParams.getTrainingIterations());
		}
		ExperimentIO.serializeNNparams(getNNParams());
		ExperimentIO.serializeSBPparams(getSBPParams());
		ExperimentIO.serializeErrors(getErrors());
	}
	
	private void runExpSBPParams(TrainingData td, String descStr, String fileName) {
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
				SBPParams sbpParams = new SBPParams(e, ti, 0.1, defaultLR, defaultMR);
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
		contents.add("With HLS = "+defaultHiddenLayerSizes+","+defaultHiddenLayerSizes);
		contents.add("LR = "+defaultLR);
		contents.add("MR = "+defaultMR);
		ExperimentIO.writeToFile(contents, fileName);
	}
	private void runExpNNParams(TrainingData td, int epochs, int trainingIters) {
		NNSBPparams.clear();
		for(int hls=startingHiddenLayerSize; hls<=endingHiddenLayerSize; hls+=hiddenLayerSizeIncrease) {
			for(double lr=startingLearningRate; lr<=endingLearningRate; lr+=learningRateIncrease) {
				for(double mr=startingMomentumRate; mr<=endingMomentumRate; mr+=momentumRateIncrease) {
					ArrayList<Integer> hiddenLayerSizes = new ArrayList<Integer>();
					hiddenLayerSizes.add(hls);
					NeuralNetworkParams NNparams = new NeuralNetworkParams(1.0, 
							td.getData().get(0).getInputs().columns, hiddenLayerSizes, 
							td.getData().get(0).getOutputs().columns);
					NeuralNetwork NN = new NeuralNetwork(NNparams);
					SBPParams sbpParams = new SBPParams(epochs, trainingIters, 0.1, lr, mr);
					SBP sbp = new SBP(sbpParams, NN);
					sbp.apply(td);
					DoubleMatrix error = sbp.getError();
					NNSBPparams.add(new NNSBPParam(error, NNparams, sbpParams));
					System.out.println("hls"+hls);
					System.out.println("lr"+lr);
					System.out.println("mr"+mr);
				}
			}
		}
	}
	
	//
	private void runExp(TrainingData td) {
		for(int hls=startingHiddenLayerSize; hls<=endingHiddenLayerSize; hls+=hiddenLayerSizeIncrease)
			for(double lr=startingLearningRate; lr<=endingLearningRate; lr+=learningRateIncrease)
				for(double mr=startingMomentumRate; mr<=endingMomentumRate; mr+=momentumRateIncrease)
					for(int e=startingEpochs; e<=endingEpochs; e+=epochsIncrease)
						for(int ti=startingTrainingIter; ti<=endingTrainingIter; ti+=trainingIterIncrease) {
							ArrayList<Integer> hiddenLayerSizes = new ArrayList<Integer>();
							hiddenLayerSizes.add(hls);
							NeuralNetworkParams NNparams = new NeuralNetworkParams(1.0, 
									td.getData().get(0).getInputs().columns, hiddenLayerSizes, 
									td.getData().get(0).getOutputs().columns);
							NeuralNetwork NN = new NeuralNetwork(NNparams);
							SBPParams sbpParams = new SBPParams(e, ti, 0.1, lr, mr);
							SBP sbp = new SBP(sbpParams, NN);
							sbp.apply(td);
							DoubleMatrix error = sbp.getError();
							NNSBPparams.add(new NNSBPParam(error, NNparams, sbpParams));
						}
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
		defaultEpochs = getPopularElement(bestEpochs);
		defaultTIs = getPopularElement(bestTIs);
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
			startingHiddenLayerSize = 36;
			endingHiddenLayerSize = 36;
			hiddenLayerSizeIncrease = 5;
			startingEpochs = 20;
			endingEpochs = 60;
			epochsIncrease = 20;
			startingLearningRate = 0.01;
			endingLearningRate = 0.01;
			learningRateIncrease = 0.01;
			startingMomentumRate = 0.01;
			endingMomentumRate = 0.01;
			momentumRateIncrease = 0.05;
			startingTrainingIter = td.getData().size();
			endingTrainingIter = td.getData().size()*100;
			trainingIterIncrease = td.getData().size()*10;
			break;
		case MEDIUM:
			startingHiddenLayerSize = 15;
			endingHiddenLayerSize = 65;
			hiddenLayerSizeIncrease = 5;
			startingEpochs = 10;
			endingEpochs = 100;
			epochsIncrease = 10;
			startingLearningRate = 0.01;
			endingLearningRate = 0.3;
			learningRateIncrease = 0.05;
			startingMomentumRate = 0.0;
			endingMomentumRate = 0.3;
			momentumRateIncrease = 0.05;
			startingTrainingIter = td.getData().size();
			endingTrainingIter = td.getData().size()*1000;
			trainingIterIncrease = td.getData().size()*10;
			break;
		case LARGE:
			startingHiddenLayerSize = 15;
			endingHiddenLayerSize = 65;
			hiddenLayerSizeIncrease = 5;
			
			startingEpochs = 20;
			endingEpochs = 50;
			epochsIncrease = 10;
			
			startingLearningRate = 0.05;
			endingLearningRate = 0.3;
			learningRateIncrease = 0.1;
			
			startingMomentumRate = 0.0;
			endingMomentumRate = 0.3;
			momentumRateIncrease = 0.1;
			
			startingTrainingIter = td.getData().size();
			endingTrainingIter = td.getData().size()*100;
			trainingIterIncrease = 100000;
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
