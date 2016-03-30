package genetic_algorithm;

import neural_network.NeuralNetwork;

/**
 * 
 * @author braemen
 *
 */
public class FitnessTester
{
	static double[] scoreFitness(Genome[] population, FitnessMethod method, GenomeImpl genome) { //TODO
		double[] fitnessScores = new double[population.length];
		for(int i=0; i<fitnessScores.length; i++) {
			Genome subject = population[i];
			switch(method) {
			case NN_HEURISTIC:
				NeuralNetwork NN = (NeuralNetwork) genome;
				
				break;
			case AVERAGE_OF_10:
				break;
			case BEST_OF_10:
				break;
			case SIMULATED_ANNEALING:
				break;
			default:
				break;
			}
			
		}
		return fitnessScores;
	}
}
