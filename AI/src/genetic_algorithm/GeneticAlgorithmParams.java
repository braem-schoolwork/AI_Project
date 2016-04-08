package genetic_algorithm;

/**
 * Parameter object for the Genetic Algorithm
 * 
 * @author braemen
 * @version 1.0
 */
public class GeneticAlgorithmParams
{
	private float percentCrossOver;
	private float percentMutation;
	private float percentElite;
	private int numGenerations;
	private int populationSize;
	private double fitnessTheshold;
	private float eliteFavoritismCoeff;
	private float annealingFactor;
	private FitnessMethod fitnessMethod;
	
	public GeneticAlgorithmParams() {
		this.setPercentCrossOver(0.0f);
		this.setPercentMutation(80f);
		this.setPercentElite(20f);
		this.setNumGenerations(100);
		this.setPopulationSize(100);
		this.setFitnessTheshold(0.2);
		this.setEliteFavoritismCoeff(0.667f);
		this.setAnnealingFactor(0.8f);
		this.setFitnessMethod(FitnessMethod.SIMULATED_ANNEALING);
	}
	public GeneticAlgorithmParams(float crossOverAmount, float mutationAmount,
			float eliteAmount, int numGenerations, int populationSize, double fitnessTheshold,
			float eliteFavoritismCoeff, float annealingFactor, FitnessMethod fitnessMethod) {
		
		this.setPercentCrossOver(crossOverAmount);
		this.setPercentMutation(mutationAmount);
		this.setPercentElite(eliteAmount);
		this.setNumGenerations(numGenerations);
		this.setPopulationSize(populationSize);
		this.setFitnessTheshold(fitnessTheshold);
		this.setEliteFavoritismCoeff(eliteFavoritismCoeff);
		this.setAnnealingFactor(annealingFactor);
		this.setFitnessMethod(fitnessMethod);
	}
	
	public int getNumGenerations() {
		return numGenerations;
	}
	public void setNumGenerations(int numGenerations) {
		this.numGenerations = numGenerations;
	}
	public float getPercentCrossOver() {
		return percentCrossOver;
	}
	public void setPercentCrossOver(float percentCrossOver) {
		this.percentCrossOver = percentCrossOver;
	}
	public float getPercentMutation() {
		return percentMutation;
	}
	public void setPercentMutation(float percentMutation) {
		this.percentMutation = percentMutation;
	}
	public float getPercentElite() {
		return percentElite;
	}
	public void setPercentElite(float percentElite) {
		this.percentElite = percentElite;
	}
	public int getPopulationSize() {
		return populationSize;
	}
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}
	public double getFitnessTheshold() {
		return fitnessTheshold;
	}
	public void setFitnessTheshold(double fitnessTheshold) {
		this.fitnessTheshold = fitnessTheshold;
	}
	public float getEliteFavoritismCoeff() {
		return eliteFavoritismCoeff;
	}
	public void setEliteFavoritismCoeff(float eliteFavoritismCoeff) {
		this.eliteFavoritismCoeff = eliteFavoritismCoeff;
	}
	public float getAnnealingFactor() {
		return annealingFactor;
	}
	public void setAnnealingFactor(float annealingFactor) {
		this.annealingFactor = annealingFactor;
	}
	public FitnessMethod getFitnessMethod() {
		return fitnessMethod;
	}
	public void setFitnessMethod(FitnessMethod fitnessMethod) {
		this.fitnessMethod = fitnessMethod;
	}
}
