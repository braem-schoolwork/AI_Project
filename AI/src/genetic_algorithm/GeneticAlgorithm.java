package genetic_algorithm;

import java.util.ArrayList;

import random_gen.RandomNumberGenerator;

/**
 * Basic Genetic Algorithm
 * 
 * @author braem
 * @version 1.0
 */
public class GeneticAlgorithm
{
	private GeneticAlgorithmParams params;
	private Genome bestGenome;
	
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
	public Genome getBestGenome() {
		return bestGenome;
	}
	public void setBestGenome(Genome bestGenome) {
		this.bestGenome = bestGenome;
	}
	
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
		/* populate */
		populate(population, subject);
		/* Generation Loop */
		for(int currentGen=0; currentGen<params.getNumGenerations(); currentGen++) {
			/* fitness testing */
			double[] fitnessScores = fitnessTester.scoreFitness(population, params.getFitnessMethod());
			/* elite selection */
			Genome[] elites = eliteSelection(population, fitnessScores);
			//System.out.println("elites: "+Arrays.toString(elites));
			//System.out.println(elites.length);
			/* mutation */
			Genome[] mutations = mutations(elites);
			//System.out.println(mutations.length);
			//System.out.println("mutations: "+Arrays.toString(mutations));
			/* cross over  */
			Genome[] crossovers = crossover(population, elites);
			/* repopulation */
			population = repopulate(population, elites, mutations, crossovers);
			//System.out.println("POP"+Arrays.toString(population));
		}
		setBestGenome(fitnessTester.getBestGenome(population));
	}
	
	/**
	 * repopulate the population
	 * @param population
	 * @param elites		genomes from elite selection
	 * @param mutations		genomes from mutation
	 * @param crossovers	genomes from crossover
	 * @return				new population
	 */
	private Genome[] repopulate(Genome[] population, Genome[] elites, Genome[] mutations, Genome[] crossovers) {
		population = new Genome[params.getPopulationSize()];
		for(int i=0; i<population.length; i++)
			if (i<elites.length) {
				population[i] = elites[i];
				//System.out.println(elites[i]+"elite");
			}
			else if (i<elites.length+mutations.length) {
				population[i] = mutations[i-elites.length];
				//System.out.println(mutations[i-elites.length]+"mut");
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
	private void populate(Genome[] population, GenomeImpl subject) {
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
	private float findVectorDistance(Genome g1, Genome g2) {
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
	private Genome[] eliteSelection(Genome[] population, double[] fitnessScores) {
		//create elites array
		Genome[] elites = new Genome[(int)Math.floor(params.getPopulationSize()*params.getPercentElite()/100)*2];
		//create a genome extension & add the population to it
		ArrayList<GenomeExt> genomes = new ArrayList<GenomeExt>();
		for(int i=0; i<params.getPopulationSize(); i++) {
			GenomeExt gExt = new GenomeExt();
			gExt.setGenomeID(i);
			gExt.setG(population[i]);
			gExt.setRawFitness(fitnessScores[i]);
			genomes.add(gExt);
		}
		//sort by raw fitness
		genomes.sort(new FitnessScoreComparator());
		//set fitness ranks
		for(int i=0; i<genomes.size(); i++) {
			GenomeExt gExt = genomes.get(i);
			gExt.setFitnessRank(i+1);
		}
		
		/* fill elites array */
		for(int a=0; a<elites.length; a++) {
			//calculate diversity score
			for(int i=0; i<genomes.size(); i++) {
				GenomeExt gExt = genomes.get(i);
				float dist = 0f;
				for(int j=0; j<genomes.size(); j++) {
					dist += findVectorDistance(gExt.getG(), genomes.get(j).getG());
				}
				dist /= genomes.size();
				gExt.setDiversityScore(dist);
			}
			//sort by diversity score
			genomes.sort(new DiversityScoreComparator());
			for(int i=0; i<genomes.size(); i++) {
				GenomeExt gExt = genomes.get(i);
				gExt.setDiversityRank(i+1);
			}
			//calculate combined rank
			for(int i=0; i<genomes.size(); i++) {
				GenomeExt gExt = genomes.get(i);
				gExt.setCombinedRank(gExt.getFitnessRank()+gExt.getDiversityRank());
			}
			//sort by combined rank
			genomes.sort(new CombinedRankComparator());
			//calculate probability of selection for the combined rank
			for(int i=0; i<genomes.size(); i++) {
				GenomeExt gExt = genomes.get(i);
				double sumPrevious = 0f;
				for(int j=0; j<i; j++) {
					sumPrevious += genomes.get(j).getProbSelCR();
				}
				double probability = params.getEliteFavoritismCoeff()*(1-sumPrevious);
				gExt.setProbSelCR(probability);
			}
			//find the elite genome
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
	private Genome[] mutations(Genome[] elites) {
		Genome[] mutations = new Genome[(int)Math.floor(params.getPopulationSize()*params.getPercentMutation()/100)*2];
		//apply mutations
		for(int i=0; i<mutations.length; i++) {
			//get a random elite & copy it
			int randomEliteIndex = RandomNumberGenerator.genIntBetweenInterval(0, elites.length-1);
			Genome randomElite = new Genome(elites[randomEliteIndex]);
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
	private Genome[] crossover(Genome[] population, Genome[] elites) {
		Genome[] crossovers = new Genome[Math.round(params.getPopulationSize()*params.getPercentCrossOver()/100)*2];
		for(int i=0; i<crossovers.length; i++) {
			//get 2 random elites
			int randomEliteIndex1 = RandomNumberGenerator.genIntBetweenInterval(0, elites.length-1);
			int randomEliteIndex2 = RandomNumberGenerator.genIntBetweenInterval(0, elites.length-1);
			//make sure elites arent the same
			if(randomEliteIndex1 == randomEliteIndex2) { //collision detection
				randomEliteIndex2++;
				randomEliteIndex2 %= (elites.length-1);
			}
			Genome elite1 = elites[randomEliteIndex1];
			Genome elite2 = elites[randomEliteIndex2];
			//pick the child's genes based on a random boolean
			ArrayList<Double> childGenes = new ArrayList<Double>();
			for(int j=0; j<elite1.getGenes().size(); j++) {
				boolean randomBool = RandomNumberGenerator.genRandomBoolean();
				if(randomBool) //use elite1 gene
					childGenes.add(elite1.getGenes().get(j));
				else //use elite2 gene
					childGenes.add(elite2.getGenes().get(j));	
			}
			Genome childGenome = new Genome(childGenes);
			crossovers[i] = childGenome;
		}
		return crossovers;
	}
}
