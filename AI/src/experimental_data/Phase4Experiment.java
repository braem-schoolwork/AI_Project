package experimental_data;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import genetic_algorithm.GeneticAlgorithm;
import genetic_algorithm.GeneticAlgorithmParams;
import neural_network.NNFitnessTester;
import neural_network.NeuralNetwork;
import training_data.TrainingData;
import training_data.XORTrainingDataGenerator;

/**
 * Experiment for Phase 4
 * 
 * @author braem
 * @version 1.0
 */
public class Phase4Experiment implements Experiment
{
	private static String filenameGENPOP = System.getProperty("user.dir")+"\\GENPOP";
	private static String filenameMUTCROSS = System.getProperty("user.dir")+"\\MUTCROSS";
	private static String filenameFitnessGraphs = System.getProperty("user.dir")+"\\FitnessGraph";
	
	private static int genStart = 10;
	private static int genIncrease = 10;
	private static int genEnd = 100;
	
	private static int popStart = 20;
	private static int popIncrease = 20;
	private static int popEnd = 160;
	
	private static float pEliteStart = 10;
	private static float pEliteIncrease = 10;
	private static float pEliteEnd = 90;
	
	private static float pMutationStart = 10;
	private static float pMutationIncrease = 10;
	private static float pMutationEnd = 90;
	
	private static float pCrossoverStart = 10;
	private static float pCrossoverIncrease = 10;
	private static float pCrossoverEnd = 90;
	
	private static int defaultGen = 100;
	private static int defaultPop = 100;
	private static float defaultElite = 20;
	private static float defaultMutation = 75;
	private static float defaultCrossover = 5;
	private static TrainingData td;
	
	//keep the combos in order by fitness
	private PriorityQueue<GenPopCombo> genPopCombos = new PriorityQueue<GenPopCombo>();
	private PriorityQueue<PercentageCombo> percentageCombos = new PriorityQueue<PercentageCombo>();
	private PriorityQueue<GAFit> bestGAs = new PriorityQueue<GAFit>();
	private static int amountOfParams = 5;
	
	/*
	 * 1. find best Gen|Pop combos
	 * 2. find best Percentages
	 * 3. run Gen|Pop experiment on fixed best percentage's from (2)
	 * 	  should generate 5 different heat graphs (from 5 top percentages combos)
	 * 4. run Percentages experiment on fixed best Gen|Pop's from (1)
	 * 	  should generate 5 different heat graphs (from 5 top Gen|Pop combos)
	 */
	@Override
	public void runExperiment(String fileExtension, ExperimentSize size) {
		setupParams(size);
		td = XORTrainingDataGenerator.gen();
		findBestGenPopCombos();
		findBestPercentageCombos();
		//run Gen|Pop Experiment
		for(int i=0; i<amountOfParams; i++) {
			PercentageCombo pc = percentageCombos.poll();
			runGenPopExp(pc.pElite, pc.pMutation, pc.pCrossover, filenameGENPOP+i, fileExtension);
		}
		for(int i=0; i<amountOfParams; i++) {
			GenPopCombo gpc = genPopCombos.poll();
			runMutCrossExp(gpc.gen, gpc.pop, defaultElite, filenameMUTCROSS+i, fileExtension);
		}
		for(int i=0; i<amountOfParams; i++) {
			GAFit gaFit = bestGAs.poll();
			writeFitnessGraphs(gaFit, filenameFitnessGraphs+i, fileExtension);
		}
	}
	
	private void writeFitnessGraphs(GAFit gaFit, String filename, String fileExtension) {
		ArrayList<Double> bestFitness = gaFit.GA.getBestFitnesses();
		ArrayList<Double> avgFitness = gaFit.GA.getAvgFitnesses();
		ArrayList<Double> worstFitness = gaFit.GA.getWorstFitnesses();
		List<String> contents = new ArrayList<String>();
		contents.add("Best Fitness Per Generation");
		contents.add("Generation#,Fitness-Value");
		for(int i=0; i<bestFitness.size(); i++)
			contents.add(i+","+bestFitness.get(i));
		contents.add("");
		contents.add("Average Fitness Per Generation");
		contents.add("Generation#,Fitness-Value");
		for(int i=0; i<avgFitness.size(); i++)
			contents.add(i+","+avgFitness.get(i));
		contents.add("");
		contents.add("Worst Fitness Per Generation");
		contents.add("Generation#,Fitness-Value");
		for(int i=0; i<worstFitness.size(); i++)
			contents.add(i+","+worstFitness.get(i));
		ExperimentIO.writeToFile(contents, filename+fileExtension);
	}
	
	private void runMutCrossExp(int gen, int pop, float pElite, String filename, String fileExtension) {
		List<String> contents = new ArrayList<String>();
		String firstRow = "Mutation|Crossover";
		for(float pMutation=pMutationStart; pMutation<=pMutationEnd; pMutation+=pMutationIncrease) {
			String row = pMutation+"";
			for(float pCrossover=pCrossoverStart; pCrossover<=pCrossoverEnd; pCrossover+=pCrossoverIncrease) {
				if(pMutation+pCrossover+pElite == 100f) {
					GeneticAlgorithmParams GAparams = new GeneticAlgorithmParams();
					GAparams.setNumGenerations(gen);
					GAparams.setPopulationSize(pop);
					GAparams.setPercentCrossOver(pCrossover);
					GAparams.setPercentElite(pElite);
					GAparams.setPercentMutation(pMutation);
					GeneticAlgorithm GA = new GeneticAlgorithm(GAparams);
					NeuralNetwork NN = new NeuralNetwork();
					GA.apply(NN, new NNFitnessTester(td, NN));
					double fitness = GA.getBestFitness();
					bestGAs.add(new GAFit(GA, fitness));
					row += ","+fitness;
				}
			}
			contents.add(row);
		}
		contents.add(0, firstRow);
		contents.add("With Generation# = "+gen);
		contents.add("Population Size = "+pop);
		contents.add("Elite% = "+pElite);
		ExperimentIO.writeToFile(contents, filename+fileExtension);
	}
	
	private void runGenPopExp(float pElite, float pMutation, float pCrossover, String filename, String fileExtension) {
		List<String> contents = new ArrayList<String>();
		String firstRow = "Gen|Pop";
		boolean firstPass = true;
		for(int gen=genStart; gen<=genEnd; gen+=genIncrease) {
			String row = gen+"";
			for(int pop=popStart; pop<=popEnd; pop+=popIncrease) {
				GeneticAlgorithmParams GAparams = new GeneticAlgorithmParams();
				GAparams.setNumGenerations(gen);
				GAparams.setPopulationSize(pop);
				GAparams.setPercentCrossOver(pCrossover);
				GAparams.setPercentElite(pElite);
				GAparams.setPercentMutation(pMutation);
				GeneticAlgorithm GA = new GeneticAlgorithm(GAparams);
				NeuralNetwork NN = new NeuralNetwork();
				GA.apply(NN, new NNFitnessTester(td, NN));
				double fitness = GA.getBestFitness();
				bestGAs.add(new GAFit(GA, fitness));
				if(firstPass)
					firstRow += ","+pop;
				row += ","+fitness;
			}
			contents.add(row);
			firstPass = false;
		}
		contents.add(0, firstRow);
		contents.add("With Elite% = "+pElite);
		contents.add("Mutation% = "+pMutation);
		contents.add("Crossover% = "+pCrossover);
		ExperimentIO.writeToFile(contents, filename+fileExtension);
	}
	
	private void findBestPercentageCombos() {
		for(float pElite=pEliteStart; pElite<=pEliteEnd; pElite+=pEliteIncrease)
			for(float pMutation=pMutationStart; pMutation<=pMutationEnd; pMutation+=pMutationIncrease)
				for(float pCrossover=pCrossoverStart; pCrossover<=pCrossoverEnd; pCrossover+=pCrossoverIncrease) {
					if(pElite+pMutation+pCrossover == 100f) {
						GeneticAlgorithmParams GAparams = new GeneticAlgorithmParams();
						GAparams.setNumGenerations(defaultGen);
						GAparams.setPopulationSize(defaultPop);
						GAparams.setPercentCrossOver(pCrossover);
						GAparams.setPercentElite(pElite);
						GAparams.setPercentMutation(pMutation);
						GeneticAlgorithm GA = new GeneticAlgorithm(GAparams);
						NeuralNetwork NN = new NeuralNetwork();
						GA.apply(NN, new NNFitnessTester(td, NN));
						double fitness = GA.getBestFitness();
						percentageCombos.add(new PercentageCombo(pElite, pMutation, pCrossover, fitness));
					}
				}
	}
	private void findBestGenPopCombos() {
		for(int gen=genStart; gen<=genEnd; gen+=genIncrease)
			for(int pop=popStart; pop<=popEnd; pop+=popIncrease) {
				GeneticAlgorithmParams GAparams = new GeneticAlgorithmParams();
				GAparams.setNumGenerations(gen);
				GAparams.setPopulationSize(pop);
				GAparams.setPercentCrossOver(defaultCrossover);
				GAparams.setPercentElite(defaultElite);
				GAparams.setPercentMutation(defaultMutation);
				GeneticAlgorithm GA = new GeneticAlgorithm(GAparams);
				NeuralNetwork NN = new NeuralNetwork();
				GA.apply(NN, new NNFitnessTester(td, NN));
				double fitness = GA.getBestFitness();
				genPopCombos.add(new GenPopCombo(gen, pop, fitness));
			}
	}
	
	private static void setupParams(ExperimentSize size) {
		switch(size) {
		case SMALL:
			genStart = 25;
			genIncrease = 25;
			genEnd = 100;
			popStart = 25;
			popIncrease = 25;
			popEnd = 100;
			pEliteStart = 10;
			pEliteIncrease = 20;
			pEliteEnd = 50;
			pMutationStart = 0;
			pMutationIncrease = 20;
			pMutationEnd = 80;
			pCrossoverStart = 0;
			pCrossoverIncrease = 20;
			pCrossoverEnd = 80;
			break;
		case MEDIUM:
			genStart = 20;
			genIncrease = 20;
			genEnd = 100;
			popStart = 20;
			popIncrease = 20;
			popEnd = 100;
			pEliteStart = 10;
			pEliteIncrease = 10;
			pEliteEnd = 50;
			pMutationStart = 0;
			pMutationIncrease = 10;
			pMutationEnd = 80;
			pCrossoverStart = 0;
			pCrossoverIncrease = 10;
			pCrossoverEnd = 80;
			break;
		case LARGE:
			genStart = 20;
			genIncrease = 20;
			genEnd = 200;
			popStart = 20;
			popIncrease = 20;
			popEnd = 200;
			pEliteStart = 10;
			pEliteIncrease = 10;
			pEliteEnd = 50;
			pMutationStart = 0;
			pMutationIncrease = 10;
			pMutationEnd = 80;
			pCrossoverStart = 0;
			pCrossoverIncrease = 10;
			pCrossoverEnd = 80;
			break;
		}
	}
	
	@Override
	public String toString() {
		return "Phase 4";
	}

	class GAFit implements Comparable<GAFit> {
		GeneticAlgorithm GA;
		double fitness;
		
		public GAFit(GeneticAlgorithm GA, double fitness) {
			this.GA = GA;
			this.fitness = fitness;
		}
		
		@Override
		public int compareTo(GAFit arg0) {
			if(this.fitness > arg0.fitness)
				return -10;
			else if(this.fitness < arg0.fitness)
				return 10;
			else 
				return 0;
		}
	}
	class GenPopCombo implements Comparable<GenPopCombo> {
		int gen;
		int pop;
		double fitness;
		
		public GenPopCombo(int gen, int pop, double fitness) {
			this.gen = gen;
			this.pop = pop;
			this.fitness = pop;
		}
		
		@Override
		public int compareTo(GenPopCombo arg0) {
			if(this.fitness > arg0.fitness)
				return -10;
			else if(this.fitness < arg0.fitness)
				return 10;
			else 
				return 0;
		}
	}
	class PercentageCombo implements Comparable<PercentageCombo> {
		float pElite;
		float pMutation;
		float pCrossover;
		double fitness;
		
		public PercentageCombo(float pElite, float pMutation, float pCrossover, double fitness) {
			this.pElite = pElite;
			this.pMutation = pMutation;
			this.pCrossover = pCrossover;
			this.fitness = fitness;
		}
		
		@Override
		public int compareTo(PercentageCombo o) {
			if(this.fitness > o.fitness)
				return -10;
			else if(this.fitness < o.fitness)
				return 10;
			else 
				return 0;
		}
	}
}
