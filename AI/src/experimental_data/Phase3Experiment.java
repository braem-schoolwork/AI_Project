package experimental_data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jblas.DoubleMatrix;

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
	private static double learningRateIncrease = 0.01;
	
	private static double startingMomentumRate = 0.0;
	private static double endingMomentumRate = 0.95;
	private static double momentumRateIncrease = 0.05;
	
	private static int startingTrainingIter;
	private static int endingTrainingIter;
	private static int trainingIterIncrease;
	
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
		//TODO find the best params
	}
	
	private void runExp(TrainingData td) {
		for(int hls=startingHiddenLayerSize; hls<endingHiddenLayerSize; hls+=hiddenLayerSizeIncrease)
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
	}
	
	private static void setupParams(ExperimentSize size, TrainingData td) {
		switch(size) {
		case SMALL:
			
			break;
		case MEDIUM:
			
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
	
	class NNSBPParam {
		DoubleMatrix error;
		NeuralNetworkParams NNparams;
		SBPParams sbpParams;
		public NNSBPParam(DoubleMatrix error, NeuralNetworkParams NNparams, SBPParams sbpParams) {
			this.error = error;
			this.NNparams = NNparams;
			this.sbpParams = sbpParams;
		}
	}
}
