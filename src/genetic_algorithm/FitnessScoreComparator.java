package genetic_algorithm;

import java.util.Comparator;

/**
 * Comparator to sort a Genome extension by raw fitness score.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
class FitnessScoreComparator implements Comparator<GenomeExt>
{

	@Override
	public int compare(GenomeExt arg0, GenomeExt arg1) {
		if(arg0.getRawFitness() > arg1.getRawFitness())
			return -10;
		else if(arg0.getRawFitness() < arg1.getRawFitness())
			return 10;
		return 0;
	}

}
