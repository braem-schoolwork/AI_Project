package genetic_algorithm;

import java.util.ArrayList;

import random_gen.RandomNumberGenerator;

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
	
	private Genome bestGenome;
	
	/**
	 * 
	 * @param g
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
			/* cross over */
			Genome[] crossovers = crossover(elites);
			/* repopulation */
			population = repopulate(population, elites, mutations, crossovers);
			//System.out.println("POP"+Arrays.toString(population));
		}
		setBestGenome(fitnessTester.getBestGenome(population));
	}
	
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
	private void populate(Genome[] population, GenomeImpl subject) {
		GenomeImpl[] others = subject.genOthers(params.getPopulationSize());
		for(int i=0; i<population.length; i++) {
			population[i] = Genome.convertTo(others[i]);
		}
	}
	
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
	
	//TODO
	private Genome[] crossover(Genome[] elites) {
		Genome[] crossovers = new Genome[Math.round(params.getPopulationSize()*params.getPercentCrossOver()/100)*2];
		
		return crossovers;
	}

	public Genome getBestGenome() {
		return bestGenome;
	}
	public void setBestGenome(Genome bestGenome) {
		this.bestGenome = bestGenome;
	}
}
