package genetic_algorithm;

public class GeneticAlgorithm
{
	private GeneticAlgorithmParams params;
	
	public GeneticAlgorithm() {
		this.setParams(new GeneticAlgorithmParams());
	}
	public GeneticAlgorithm(GeneticAlgorithmParams params) {
		this.setParams(params);
	}

	public GeneticAlgorithmParams getParams() {
		return params;
	}
	public void setParams(GeneticAlgorithmParams params) {
		this.params = params;
	}
	
	/**
	 * 
	 * @param genome
	 */
	public void apply(Genome genome) {
		
	}
}
