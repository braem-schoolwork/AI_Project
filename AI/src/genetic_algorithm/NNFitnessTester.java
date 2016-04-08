package genetic_algorithm;

import java.util.Arrays;

import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

import matrix_wrapper.MatrixFunctionWrapper;
import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkParams;
import training_data.TrainingData;
import training_data.TrainingTuple;

public class NNFitnessTester implements FitnessTester
{
	private TrainingData trainingData;
	private NeuralNetworkParams NNparams;
	public NNFitnessTester(TrainingData trainingData, NeuralNetworkParams NNparams) {
		this.trainingData = trainingData;
		this.NNparams = NNparams;
	}
	
	@Override
	public double[] scoreFitness(Genome[] population, FitnessMethod method) { //TODO
		double[] fitnessScores = new double[population.length];
		for(int i=0; i<fitnessScores.length; i++) {
			Genome subject = population[i];
			NeuralNetwork NN = Genome.toNN(subject, NNparams);
			DoubleMatrix errorVec = DoubleMatrix.zeros(1, trainingData.getData().get(0).getOutputs().columns);
			for(TrainingTuple tt : trainingData.getData()) {
				DoubleMatrix inputVec = tt.getInputs();
				DoubleMatrix expectedOutputVec = tt.getOutputs();
				DoubleMatrix actualOutputVec = NN.feedForward(inputVec);
				DoubleMatrix thisTupleError = MatrixFunctions.pow(expectedOutputVec.sub(actualOutputVec), 2);
				thisTupleError.mmuli(0.5);
				errorVec = errorVec.addRowVector(thisTupleError);
			}
			for(int j=0; j<errorVec.columns; j++) {
				errorVec.put(0,j,errorVec.get(0,j)/trainingData.getData().size());
			}
			fitnessScores[i] = MatrixFunctionWrapper.avgValues(errorVec);
		}
		return fitnessScores;
	}

	@Override
	public Genome getBestGenome(Genome[] genomes) {
		double bestScore = Double.MAX_VALUE;
		Genome bestGenome = null;
		for(int i=0; i<genomes.length; i++) {
			NeuralNetwork NN = Genome.toNN(genomes[i], NNparams);
			double feedAvg = 0;
			for(TrainingTuple tt : trainingData.getData()) {
				double netScore = MatrixFunctionWrapper.avgValues(NN.feedForward(tt.getInputs()));
				feedAvg += netScore;
			}
			feedAvg /= trainingData.getData().size();
			if(i==0) {
				bestScore = feedAvg;
				bestGenome = genomes[i];
			}
			else if(feedAvg < bestScore) {
				bestScore = feedAvg;
				bestGenome = genomes[i];
			}
		}
		return bestGenome;
	}
}
