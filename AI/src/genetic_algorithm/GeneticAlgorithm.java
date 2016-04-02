package genetic_algorithm;

import java.util.ArrayList;
import java.util.Collections;

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
	
	/**
	 * 
	 * @param genome
	 */
	public void apply(GenomeImpl subject, FitnessTester fitnessTester) {
		Genome[] population = new Genome[params.getPopulationSize()];
		/* populate */
		populate(population, subject);
		/* Generation Loop */
		for(int currentGen=0; currentGen<params.getNumGenerations(); currentGen++) {
			/* fitness testing */
			double[] fitnessScores = fitnessTester.scoreFitness(population, params.getFitnessMethod());
			/* elite selection */
			Genome[] elites = eliteSelection(population, fitnessScores);
			/* mutation */
			Genome[] mutations = mutations(elites);
			/* cross over */
			Genome[] crossovers = crossover(elites);
			/* repopulation */
			repopulate(population, elites, mutations, crossovers);
		}
	}
	
	private void populate(Genome[] population, GenomeImpl subject) { //TODO
		GenomeImpl[] others = subject.genOthers(params.getPopulationSize());
		for(int i=0; i<population.length; i++) {
			population[i] = Genome.convertTo(others[i]);
		}
	}
	
	//TODO implement diversity
	private Genome[] eliteSelection(Genome[] population, double[] fitnessScores) {
		ArrayList<FitnessGenome> fitnessGenomes = new ArrayList<FitnessGenome>();
		for(int i=0; i<params.getPopulationSize(); i++) 
			fitnessGenomes.add(new FitnessGenome(population[i], fitnessScores[i]));
		Genome[] elites = new Genome[Math.round(params.getPopulationSize()*params.getPercentElite()/100)];
		
		for(int a=0; a<elites.length; a++) {
			Collections.sort(fitnessGenomes); //rank by raw fitness
			//calculate the probability of selection for each genome
			for(int i=0; i<fitnessGenomes.size(); i++) {
				FitnessGenome fG = fitnessGenomes.get(i);
				float sumPrevious = 0f;
				for(int j=0; j<i; j++) {
					sumPrevious += fitnessGenomes.get(j).getProbabilityOfSelection();
				}
				float probability = params.getEliteFavoritismCoeff()*(1-sumPrevious);
				fG.setProbility(probability);
			}
			//generate random number between 0 and 1
			double randomNum = RandomNumberGenerator.genIntBetweenInterval(0, 1);
			//find the genome with the probability closest to the random number & add it to list
			for(int i=0; i<fitnessGenomes.size(); i++) {
				float lower = fitnessGenomes.get(i).getProbabilityOfSelection();
				float upper;
				if(i == fitnessGenomes.size()-1)
					upper = 1f;
				else
					upper = fitnessGenomes.get(i+1).getProbabilityOfSelection();
				if (randomNum<=upper && randomNum>=lower) {
					elites[a] = fitnessGenomes.get(i).genome;
					fitnessGenomes.remove(i);
					break;
				}
			}
		} //elite for
		return elites;
	}
	
	private Genome[] mutations(Genome[] elites) {
		Genome[] mutations = new Genome[Math.round(params.getPopulationSize()*params.getPercentMutation()/100)];
		//apply mutations
		for(int i=0; i<mutations.length; i++) {
			//get a random elite & copy it
			int randomEliteIndex = RandomNumberGenerator.genIntBetweenInterval(0, elites.length); //TODO may break
			Genome randomElite = new Genome(elites[randomEliteIndex]);
			//apply random mutations
			for(int j=0; j<randomElite.getGenes().size(); j++) {
				double randomMutation = RandomNumberGenerator.genDoubleBetweenInterval(-0.01, 0.01);
				randomElite.getGenes().set(j, randomElite.getGenes().get(j)+randomMutation);
			}
			mutations[i] = randomElite;
		}
		return mutations;
	}
	//TODO
	private Genome[] crossover(Genome[] elites) {
		Genome[] crossovers = new Genome[Math.round(params.getPopulationSize()*params.getPercentCrossOver()/100)];
		
		return crossovers;
	}
	
	private void repopulate(Genome[] population, Genome[] elites, Genome[] mutations, Genome[] crossovers) {
		population = new Genome[params.getPopulationSize()];
		for(int i=0; i<population.length; i++)
			if (i<elites.length)
				population[i] = elites[i];
			else if (i<elites.length+mutations.length)
				population[i] = mutations[i-elites.length];
			else if (i<elites.length+mutations.length+crossovers.length)
				population[i] = crossovers[i-elites.length-mutations.length];
	}
	
	class FitnessGenome implements Comparable<FitnessGenome> {
		Genome genome;
		double fitness;
		float probabilityOfSelection;
		public FitnessGenome(Genome genome, double fitness) {
			this.genome = genome;
			this.fitness = fitness;
		}
		
		public void setProbility(float prob) {
			this.probabilityOfSelection = prob;
		}
		public float getProbabilityOfSelection() {
			return probabilityOfSelection;
		}
		
		@Override
		public int compareTo(FitnessGenome fG) { //TODO may be backwards
			if (fitness<fG.fitness)
				return -10;
			else if (fitness>fG.fitness)
				return 10;
			else
				return 0;
		}
	}
}
