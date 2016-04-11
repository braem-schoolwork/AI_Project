package genetic_algorithm;

/**
 * Interface for a fitness tester used by the Genetic Algorithm
 * 
 * @author braemen
 * @version 1.0
 */
public interface FitnessTester
{
	/**
	 * Method to score fitness of a Genome population given a certain FitnessMethod
	 * @param population	Genome population
	 * @param method		FitnessMethod used to score fitness
	 * @return				fitness values corresponding to the population
	 */
	double[] scoreFitness(Genome[] population, FitnessMethod method);
	
	/**
	 * Method to get the best genome out of an array of genomes
	 * @param genomes		array of genomes
	 * @return				the best genome in the array of genomes
	 */
	Genome getBestGenome(Genome[] genomes);
}
