package training_algorithms;

import java.io.Serializable;

/**
 * Parameter object for SBP
 * 
 * @author braemen
 * @version 1.0
 */
public class SBPParams implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5792887989828622404L;
	private int epochs;
	private int trainingIterations;
	private double errorThreshold;
	private double learningRate;
	private double momentumRate;
	
	public SBPParams() {
		setEpochs(5000);
		setTrainingIterations(3500);
		setErrorThreshold(0.0001);
		setLearningRate(0.10);
		setMomentumRate(0.10);
	}
	
	public SBPParams(int epochs, int trainingIterations, double errorThreshold, double learningRate, double momentumRate) {
		this.setEpochs(epochs);
		this.setTrainingIterations(trainingIterations);
		this.setErrorThreshold(errorThreshold);
		this.setLearningRate(learningRate);
		this.setMomentumRate(momentumRate);
	}

	public int getEpochs() {
		return epochs;
	}

	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}

	public int getTrainingIterations() {
		return trainingIterations;
	}

	public void setTrainingIterations(int trainingIterations) {
		this.trainingIterations = trainingIterations;
	}

	public double getErrorThreshold() {
		return errorThreshold;
	}

	public void setErrorThreshold(double errorThreshold) {
		this.errorThreshold = errorThreshold;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getMomentumRate() {
		return momentumRate;
	}

	public void setMomentumRate(double momentumRate) {
		this.momentumRate = momentumRate;
	}
}
