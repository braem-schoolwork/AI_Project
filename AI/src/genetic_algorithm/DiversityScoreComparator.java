package genetic_algorithm;

import java.util.Comparator;

class DiversityScoreComparator implements Comparator<GenomeExt>
{

	@Override
	public int compare(GenomeExt o1, GenomeExt o2) {
		if(o1.getDiversityScore() > o2.getDiversityScore())
			return -10;
		else if(o1.getDiversityScore() < o2.getDiversityScore())
			return 10;
		return 0;
	}

}
