package experimental_data;

import java.util.ArrayList;
import java.util.Collections;

import org.jblas.DoubleMatrix;

import matrix_wrapper.FunctionWrapper;
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
	private static int startingHiddenLayerSize = 10;
	private static int endingHiddenLayerSize = 65;
	private static int hiddenLayerSizeIncrease = 5;
	
	private static int startingEpochs = 10;
	private static int endingEpochs = 100;
	private static int epochsIncrease = 10;
	
	private static double startingLearningRate = 0.01;
	private static double endingLearningRate = 0.3;
	private static double learningRateIncrease = 0.05;
	
	private static double startingMomentumRate = 0.0;
	private static double endingMomentumRate = 0.30;
	private static double momentumRateIncrease = 0.05;
	
	private static int startingTrainingIter;
	private static int endingTrainingIter;
	private static int trainingIterIncrease;
	
	private static final int AMOUNT_OF_PARAMS = 5;
	
	private ArrayList<NNSBPParam> NNSBPparams = new ArrayList<NNSBPParam>();
	private ArrayList<NNSBPParam> bestNNSBPparams = new ArrayList<NNSBPParam>();
	
	@Override
	public void runExperiment(String fileExtension, ExperimentSize size) {
		TrainingData td = TrainingDataGenerator.genFromFile();
		if(td == null) {
			Phase1Experiment exp = new Phase1Experiment();
			exp.runExperiment(".csv", ExperimentSize.LARGE);
			td = TrainingDataGenerator.genFromFile();
		}
		setupParams(size, td);
		runExp(td);
		ExperimentIO.serializeNNparams(getBestNNParams());
		ExperimentIO.serializeSBPparams(getBestSBPParams());
		ExperimentIO.serializeErrors(getBestErrors());
	}
	
	private void runExp(TrainingData td) {
		for(int hls=startingHiddenLayerSize; hls<endingHiddenLayerSize; hls+=hiddenLayerSizeIncrease) {
			for(double lr=startingLearningRate; lr<endingLearningRate; lr+=learningRateIncrease)
				for(double mr=startingMomentumRate; mr<endingMomentumRate; mr+=momentumRateIncrease)
					for(int e=startingEpochs; e<endingEpochs; e+=epochsIncrease)
						for(int ti=startingTrainingIter; ti<endingTrainingIter; ti+=trainingIterIncrease) {
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
			System.out.println(hls);
		}
		for(int i=0; i<AMOUNT_OF_PARAMS; i++)
			bestNNSBPparams.add(new NNSBPParam());
		for(int i=0; i<NNSBPparams.size(); i++) {
			NNSBPParam current = NNSBPparams.get(i);
			if(FunctionWrapper.lessThanRowVec(current.error, bestNNSBPparams.get(AMOUNT_OF_PARAMS-1).error))
				bestNNSBPparams.set(AMOUNT_OF_PARAMS-1, current);
			Collections.sort(NNSBPparams);
		}
	}
	
	/**
	 * @return Best NeuralNetwork parameters from experiment
	 */
	public ArrayList<NeuralNetworkParams> getBestNNParams() {
		ArrayList<NeuralNetworkParams> p = new ArrayList<NeuralNetworkParams>();
		for(NNSBPParam s : NNSBPparams)
			p.add(s.NNparams);
		return p;
	}
	/**
	 * @return Best SBP parameters from experiment
	 */
	public ArrayList<SBPParams> getBestSBPParams() {
		ArrayList<SBPParams> p = new ArrayList<SBPParams>();
		for(NNSBPParam s : NNSBPparams)
			p.add(s.sbpParams);
		return p;
	}
	/**
	 * @return Best errors from experiment
	 */
	public ArrayList<DoubleMatrix> getBestErrors() {
		ArrayList<DoubleMatrix> p = new ArrayList<DoubleMatrix>();
		for(NNSBPParam s : NNSBPparams)
			p.add(s.error);
		return p;
	}
	
	private static void setupParams(ExperimentSize size, TrainingData td) {
		switch(size) {
		case SMALL:
			startingHiddenLayerSize = 10;
			endingHiddenLayerSize = 65;
			hiddenLayerSizeIncrease = 5;
			startingEpochs = 10;
			endingEpochs = 100;
			epochsIncrease = 10;
			startingLearningRate = 0.01;
			endingLearningRate = 0.3;
			learningRateIncrease = 0.01;
			startingMomentumRate = 0.0;
			endingMomentumRate = 0.95;
			momentumRateIncrease = 0.05;
			startingTrainingIter = td.getData().size();
			endingTrainingIter = td.getData().size()*1000;
			trainingIterIncrease = td.getData().size()/10;
			break;
		case MEDIUM:
			startingHiddenLayerSize = 10;
			endingHiddenLayerSize = 65;
			hiddenLayerSizeIncrease = 5;
			startingEpochs = 10;
			endingEpochs = 100;
			epochsIncrease = 10;
			startingLearningRate = 0.01;
			endingLearningRate = 0.3;
			learningRateIncrease = 0.01;
			startingMomentumRate = 0.0;
			endingMomentumRate = 0.95;
			momentumRateIncrease = 0.05;
			startingTrainingIter = td.getData().size();
			endingTrainingIter = td.getData().size()*1000;
			trainingIterIncrease = td.getData().size()/10;
			break;
		case LARGE:
			startingHiddenLayerSize = 10;
			endingHiddenLayerSize = 65;
			hiddenLayerSizeIncrease = 5;
			
			startingEpochs = 10;
			endingEpochs = 100;
			epochsIncrease = 10;
			
			startingLearningRate = 0.01;
			endingLearningRate = 0.3;
			learningRateIncrease = 0.01;
			
			startingMomentumRate = 0.0;
			endingMomentumRate = 0.95;
			momentumRateIncrease = 0.05;
			
			startingTrainingIter = td.getData().size();
			endingTrainingIter = td.getData().size()*1000;
			trainingIterIncrease = td.getData().size()/10;
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
			if(FunctionWrapper.lessThanRowVec(this.error, arg0.error)) return -10;
			else if(FunctionWrapper.lessThanRowVec(arg0.error, this.error)) return 10;
			else return 0;
		}
		@Override
		public String toString() {
			return ""+error;
		}
	}
}
