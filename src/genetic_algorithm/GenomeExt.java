package genetic_algorithm;

/**
 * Extension, or wrapper, for a Genome to be used in the Genetic Algorithm.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
class GenomeExt
{
	private int 	genomeID;
	private Genome 	g;
	private double 	rawFitness;
	private int 	fitnessRank;
	private double 	probSelFR;
	private double 	diversityScore;
	private int 	diversityRank;
	private int 	combinedRank;
	private double 	probSelCR;
	
	public GenomeExt() { }
	
	public int 		getGenomeID() 								{ return genomeID; }
	public void 	setGenomeID(int genomeID) 					{ this.genomeID = genomeID; }
	public Genome 	getG() 										{ return g; }
	public void 	setG(Genome g) 								{ this.g = g; }
	public double 	getRawFitness() 							{ return rawFitness; }
	public void 	setRawFitness(double rawFitness) 			{ this.rawFitness = rawFitness; }
	public int 		getFitnessRank() 							{ return fitnessRank; }
	public void 	setFitnessRank(int fitnessRank) 			{ this.fitnessRank = fitnessRank; }
	public double 	getProbSelFR() 								{ return probSelFR;}
	public void 	setProbSelFR(double probSelFR) 				{ this.probSelFR = probSelFR; }
	public double 	getDiversityScore() 						{ return diversityScore; }
	public void 	setDiversityScore(double diversityScore) 	{ this.diversityScore = diversityScore; }
	public int 		getDiversityRank() 							{ return diversityRank; }
	public void 	setDiversityRank(int diversityRank)			{ this.diversityRank = diversityRank; }
	public int 		getCombinedRank() 							{ return combinedRank; }
	public void 	setCombinedRank(int combinedRank) 			{ this.combinedRank = combinedRank; }
	public double 	getProbSelCR() 								{ return probSelCR; }
	public void 	setProbSelCR(double probSelCR) 				{ this.probSelCR = probSelCR; }
}
