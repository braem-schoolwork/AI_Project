package genetic_algorithm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ComparatorTest {

	@Test
	public void testRawFitness() {
		GenomeExt ga1 = new GenomeExt();
		GenomeExt ga2 = new GenomeExt();
		ga1.setRawFitness(5000);
		ga2.setRawFitness(3000);
		ArrayList<GenomeExt> gas = new ArrayList<GenomeExt>();
		gas.add(ga1);
		gas.add(ga2);
		gas.sort(new FitnessScoreComparator());
		assertTrue(gas.get(0).getRawFitness() == 5000);
	}

	@Test
	public void testDiverseScore() {
		GenomeExt ga1 = new GenomeExt();
		GenomeExt ga2 = new GenomeExt();
		ga1.setDiversityScore(5000);
		ga2.setDiversityScore(3000);
		ArrayList<GenomeExt> gas = new ArrayList<GenomeExt>();
		gas.add(ga1);
		gas.add(ga2);
		gas.sort(new DiversityScoreComparator());
		assertTrue(gas.get(0).getDiversityScore() == 5000);
	}
	
	@Test
	public void testRank() {
		GenomeExt ga1 = new GenomeExt();
		GenomeExt ga2 = new GenomeExt();
		ga1.setCombinedRank(2);
		ga2.setCombinedRank(5);
		ArrayList<GenomeExt> gas = new ArrayList<GenomeExt>();
		gas.add(ga1);
		gas.add(ga2);
		gas.sort(new CombinedRankComparator());
		assertTrue(gas.get(0).getCombinedRank() == 2);
	}
	
}
