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
		
		//generation loop
		for(int currentGen=0; currentGen<params.getNumGenerations(); currentGen++) {
			//population loop
			for(int i=0; i<params.getPopulationSize(); i++) {
				
			}
		}
	}
}
