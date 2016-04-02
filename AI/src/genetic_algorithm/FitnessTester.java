package genetic_algorithm;

/**
 * 
 * @author braemen
 *
 */
public interface FitnessTester
{
	double[] scoreFitness(Genome[] population, FitnessMethod method);
}
