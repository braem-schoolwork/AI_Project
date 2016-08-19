package neural_network;

import org.jblas.DoubleMatrix;

import genetic_algorithm.FitnessMethod;
import genetic_algorithm.FitnessTester;
import genetic_algorithm.Genome;
import matrix_wrapper.MatrixFunctionWrapper;
import training_algorithms.ErrorCalculator;
import training_data.TrainingData;
import training_data.TrainingTuple;

/**
 * Fitness tester heuristic for Neural Networks.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class NNFitnessTester implements FitnessTester
{
	private TrainingData 	trainingData;
	private NeuralNetwork 	subject;
	
	public NNFitnessTester(TrainingData trainingData, NeuralNetwork subject) {
		this.trainingData 	= trainingData;
		this.subject 		= subject;
	}
	
	@Override
	public double[] scoreFitness(Genome[] population, FitnessMethod method) {
		double[] fitnessScores = new double[population.length];
		switch(method) {
			case NN_HEURISTIC:
				for(int i=0; i<fitnessScores.length; i++) {
					Genome 			genome 		= population[i];
					NeuralNetwork 	NN 			= (NeuralNetwork) Genome.convertFrom(subject, genome);
					DoubleMatrix 	errorVec 	= ErrorCalculator.calculateError(trainingData, NN);
					fitnessScores[i] 			= (trainingData.getData().size()*4)-MatrixFunctionWrapper.avgValues(errorVec);
				}
				break;
			case RUBIKSCUBE_NN_HEURISTIC:
				break;
			default: break;
		}
		return fitnessScores;
	}

	@Override
	public Genome getBestGenome(Genome[] genomes) {
		double bestScore 	= Double.MAX_VALUE;
		Genome bestGenome 	= null;
		
		for(int i=0; i<genomes.length; i++) {
			NeuralNetwork 	NN 		= (NeuralNetwork) Genome.convertFrom(this.subject, genomes[i]);
			double 			feedAvg = 0;
			for(TrainingTuple tt : trainingData.getData()) {
				double netScore = MatrixFunctionWrapper.avgValues(NN.feedForward(tt.getInputs()));
				feedAvg += netScore;
			}
			feedAvg /= trainingData.getData().size();
			if(i==0) {
				bestScore 	= feedAvg;
				bestGenome 	= genomes[i];
			}
			else if(feedAvg < bestScore) {
				bestScore 	= feedAvg;
				bestGenome 	= genomes[i];
			}
		}
		return bestGenome;
	}
}
