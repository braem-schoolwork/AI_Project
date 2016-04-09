package genetic_algorithm;

import java.util.Comparator;

class CombinedRankComparator implements Comparator<GenomeExt>
{

	@Override
	public int compare(GenomeExt o1, GenomeExt o2) {
		if(o1.getCombinedRank() < o2.getCombinedRank())
			return -10;
		else if(o1.getCombinedRank() > o2.getCombinedRank())
			return 10;
		return 0;
	}

}
