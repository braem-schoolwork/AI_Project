package genetic_algorithm;

import java.util.ArrayList;

import random_gen.RandomNumberGenerator;

/**
 * Basic Genetic Algorithm.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class GeneticAlgorithm
{
	private GeneticAlgorithmParams 	params;
	private GenomeImpl 				bestGenomeImpl;
	private double 					bestFitness;
	private ArrayList<Double> 		bestFitnesses 	= new ArrayList<Double>();
	private ArrayList<Double> 		avgFitnesses 	= new ArrayList<Double>();
	private ArrayList<Double> 		worstFitnesses 	= new ArrayList<Double>();
	
	public GeneticAlgorithm() 								{ this.setParams(new GeneticAlgorithmParams()); }
	public GeneticAlgorithm(GeneticAlgorithmParams params) 	{ this.setParams(params); }

	public GeneticAlgorithmParams 	getParams() 								{ return params; }
	public void 					setParams(GeneticAlgorithmParams params) 	{ this.params = params; }
	public GenomeImpl 				getBestGenomeImpl() 						{ return bestGenomeImpl; }
	public double 					getBestFitness() 							{ return bestFitness; }
	public ArrayList<Double> 		getBestFitnesses() 							{ return bestFitnesses; }
	public ArrayList<Double> 		getAvgFitnesses() 							{ return avgFitnesses; }
	public ArrayList<Double> 		getWorstFitnesses() 						{ return worstFitnesses; }
	
	/**
	 * Applies the Genetic Algorithm given a subject of which to base it and a FitnessTester object
	 * 
	 * @param subject			subject of which to base the Algorithm around
	 * @param fitnessTester		FitnessTester object that tests the fitness of genomes
	 */
	public void apply(GenomeImpl subject, FitnessTester fitnessTester) {
		if(params.getPercentCrossOver()+params.getPercentElite()+params.getPercentMutation() != 100f)
			throw new IllegalGAParamsException("Crossover|Elite|Mutation percentages do not add up to 100%");
		
		Genome[] population = new Genome[params.getPopulationSize()];
		populate(population, subject);
		
		for(int currentGen=0; currentGen<params.getNumGenerations(); currentGen++) {
			double[] fitnessScores 	= fitnessTester.scoreFitness(population, params.getFitnessMethod());
			if(isBelowThreshold(fitnessScores, subject, population))
				return;
			
			Genome[] elites 		= eliteSelection(population, fitnessScores);
			Genome[] mutations 		= mutations(elites);
			Genome[] crossovers 	= crossover(population, elites);
			
			population = repopulate(population, elites, mutations, crossovers);
		}
		Genome 		bestGenome 		= fitnessTester.getBestGenome(population);
		double[] 	fitnessScore 	= fitnessTester.scoreFitness(new Genome[] { bestGenome }, params.getFitnessMethod());
		
		bestGenomeImpl 	= Genome.convertFrom(subject, bestGenome);
		bestFitness 	= fitnessScore[0];
	}
	
	/**
	 * Checks if a fitness score is below the threshold
	 * Also adds the fitness information for a single generation.
	 * <p>
	 * That is, the best, worst, and average fitnesses in that generation.
	 * @param fitnessScores		array of fitness scores
	 * @param subject			subject to base the population function on
	 * @param population		array of genomes in the population
	 * @return					<code>true</code> if a Genome was found with fitness under the fitness threshold
	 * 							<code>false</code> otherwise
	 */
	boolean isBelowThreshold(double[] fitnessScores, GenomeImpl subject, Genome[] population) {
		int 	index 	= -1;
		double 	sum 	= 0;
		double 	best 	= 0;
		double 	worst 	= Double.MAX_VALUE;
		for(int i=0; i<fitnessScores.length; i++) {
			double d = fitnessScores[i];
			if(d > params.getFitnessTheshold())
				index 	= i;
			if(d > best)
				best 	= d;
			if(d < worst)
				worst 	= d;
			sum += d;
		}
		bestFitnesses.add	(best);
		avgFitnesses.add	(sum/fitnessScores.length);
		worstFitnesses.add	(worst);
		if(index != -1) {
			bestGenomeImpl 	= Genome.convertFrom(subject, population[index]);
			bestFitness 	= fitnessScores[index];
			return true;
		}
		return false;
	}
	
	/**
	 * repopulate the population
	 * @param population
	 * @param elites		genomes from elite selection
	 * @param mutations		genomes from mutation
	 * @param crossovers	genomes from crossover
	 * @return				new population
	 */
	Genome[] repopulate(Genome[] population, Genome[] elites, Genome[] mutations, Genome[] crossovers) {
		population = new Genome[params.getPopulationSize()];
		for(int i=0; i<population.length; i++)
			if (i<elites.length) {
				population[i] = elites[i];
			}
			else if (i<elites.length+mutations.length) {
				population[i] = mutations[i-elites.length];
			}
			else if (i<elites.length+mutations.length+crossovers.length)
				population[i] = crossovers[i-elites.length-mutations.length];
		return population;
	}
	
	/**
	 * populates the population by generating GenomeImpls and converting them into genomes
	 * @param population	Genome population
	 * @param subject		subject of which to generate similar GenomeImpl's
	 */
	void populate(Genome[] population, GenomeImpl subject) {
		GenomeImpl[] others = subject.genOthers(params.getPopulationSize());
		for(int i=0; i<population.length; i++) {
			population[i] = Genome.convertTo(others[i]);
		}
	}
	
	/**
	 * helper function to find the vector distance between two genomes
	 * @param g1
	 * @param g2
	 * @return
	 */
	float findVectorDistance(Genome g1, Genome g2) {
		ArrayList<Double> genes1 = g1.getGenes();
		ArrayList<Double> genes2 = g2.getGenes();
		float sum = 0f;
		for(Double d : genes1) {
			for(Double d2 : genes2) {
				sum += Math.abs(d2-d);
			}
		}
		return sum;
	}
	
	/**
	 * Method to select the elites from the population pool
	 * @param population		Genome population
	 * @param fitnessScores		corresponding fitness scores of each genome
	 * @return					elite genomes
	 */
	Genome[] eliteSelection(Genome[] population, double[] fitnessScores) {
		Genome[] elites = new Genome[(int)Math.floor(params.getPopulationSize()*params.getPercentElite()/100)];
		ArrayList<GenomeExt> genomes = new ArrayList<GenomeExt>();
		for(int i=0; i<params.getPopulationSize(); i++) {
			GenomeExt gExt = new GenomeExt();
			gExt.setGenomeID(i);
			gExt.setG(population[i]);
			gExt.setRawFitness(fitnessScores[i]);
			genomes.add(gExt);
		}
		genomes.sort(new FitnessScoreComparator());
		for(int i=0; i<genomes.size(); i++) {
			GenomeExt gExt = genomes.get(i);
			gExt.setFitnessRank(i+1);
		}
		
		/* fill elites array */
		for(int a=0; a<elites.length; a++) {
			for(int i=0; i<genomes.size(); i++) {
				GenomeExt gExt = genomes.get(i);
				float dist = 0f;
				for(int j=0; j<elites.length; j++) {
					if(elites[j] != null)
						dist += findVectorDistance(gExt.getG(), genomes.get(j).getG());
				}
				dist /= genomes.size();
				gExt.setDiversityScore(dist);
			}
			genomes.sort(new DiversityScoreComparator());
			
			for(int i=0; i<genomes.size(); i++) {
				GenomeExt gExt = genomes.get(i);
				gExt.setDiversityRank(i+1);
			}
			
			for(int i=0; i<genomes.size(); i++) {
				GenomeExt gExt = genomes.get(i);
				gExt.setCombinedRank(gExt.getFitnessRank()+gExt.getDiversityRank());
			}
			genomes.sort(new CombinedRankComparator());
			
			for(int i=0; i<genomes.size(); i++) {
				GenomeExt gExt = genomes.get(i);
				double sumPrevious = 0f;
				for(int j=0; j<i; j++) {
					sumPrevious += genomes.get(j).getProbSelCR();
				}
				double probability = params.getEliteFavoritismCoeff()*(1-sumPrevious);
				gExt.setProbSelCR(probability);
			}
			
			double randomNum = RandomNumberGenerator.genDoubleBetweenInterval(0, 1); 
			double prevUpper = 0.0;
			for(int i=0; i<genomes.size(); i++) {
				double lower = prevUpper;
				double upper = prevUpper + genomes.get(i).getProbSelCR();
				if (randomNum<=upper && randomNum>=lower) {
					elites[a] = genomes.remove(i).getG();
					break;
				}
				prevUpper = upper;
			}
		} //end elites
		return elites;
	}
	
	/**
	 * Method to generate mutations from the elites
	 * @param elites	elite genomes
	 * @return			mutated genomes
	 */
	Genome[] mutations(Genome[] elites) {
		Genome[] mutations = new Genome[(int)Math.floor(params.getPopulationSize()*params.getPercentMutation()/100)];
		for(int i=0; i<mutations.length; i++) {
			int 	randomEliteIndex 	= RandomNumberGenerator.genIntBetweenInterval(0, elites.length-1);
			Genome 	randomElite 		= new Genome(elites[randomEliteIndex]);
			//apply random mutations
			int r = RandomNumberGenerator.genIntBetweenInterval(0, 2);
			for(int j=0; j<randomElite.getGenes().size(); j++) {
				double randomMutation = RandomNumberGenerator.genDoubleBetweenInterval(0.0001, 0.1);
				double randomCoeff = RandomNumberGenerator.genDoubleBetweenInterval(0.9, 1.1);
				switch(r) {
					case 0: 
						randomElite.getGenes().set(j, randomElite.getGenes().get(j)-randomMutation);
						break;
					case 1:
						randomElite.getGenes().set(j, randomElite.getGenes().get(j)+randomMutation);
						break;
					case 2:
						randomElite.getGenes().set(j, randomElite.getGenes().get(j)*randomCoeff);
						break;
					}
			}
			mutations[i] = randomElite;
		}
		return mutations;
	}
	
	/**
	 * Method to preform crossover on the elite genomes
	 * @param population	Genome population
	 * @param elites		elite genomes
	 * @return				crossover genomes
	 */
	Genome[] crossover(Genome[] population, Genome[] elites) {
		Genome[] crossovers = new Genome[Math.round(params.getPopulationSize()*params.getPercentCrossOver()/100)];
		for(int i=0; i<crossovers.length; i++) {
			int randomEliteIndex1 = RandomNumberGenerator.genIntBetweenInterval(0, elites.length-1);
			int randomEliteIndex2 = RandomNumberGenerator.genIntBetweenInterval(0, elites.length-1);
			if(randomEliteIndex1 == randomEliteIndex2) { //collision detection
				randomEliteIndex2++;
				randomEliteIndex2 %= (elites.length-1);
			}
			Genome elite1 = elites[randomEliteIndex1];
			Genome elite2 = elites[randomEliteIndex2];
			
			ArrayList<Double> childGenes = new ArrayList<Double>();
			for(int j=0; j<elite1.getGenes().size(); j++) {
				boolean randomBool = RandomNumberGenerator.genRandomBoolean();
				if(randomBool)
					childGenes.add(elite1.getGenes().get(j));
				else
					childGenes.add(elite2.getGenes().get(j));	
			}
			crossovers[i] = new Genome(childGenes);
		}
		return crossovers;
	}
}
