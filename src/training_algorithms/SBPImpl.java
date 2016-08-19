package training_algorithms;

import java.util.ArrayList;

import org.jblas.DoubleMatrix;

/**
 * Interface needed to be implemented in order for SBP to train.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public interface SBPImpl
{
	/**
	 * Feeds an input through the object.
	 * @param inputVector	input
	 * @return				output from object
	 */
	public DoubleMatrix feedForward(DoubleMatrix inputVector);
	
	/**
	 * Applies the sigmoid function.
	 * @param inc	incoming matrix
	 * @return		matrix with the sigmoid function applied
	 */
	public DoubleMatrix applySigmoid		(DoubleMatrix inc);
	/**
	 * Applies the derivative of the sigmoid function.
	 * @param inc	incoming matrix
	 * @return		matrix with the derivative of the sigmoid function applied
	 */
	public DoubleMatrix applySigmoidDeriv	(DoubleMatrix inc);
	
	/**
	 * Initializes the object.
	 */
	public void init();
	
	/**
	 * Saves this object to disk.
	 * @param error		associated error of object
	 */
	public void saveToDisk(DoubleMatrix error);

	public DoubleMatrix 			getNETk();
	public ArrayList<DoubleMatrix> 	getNETjs();
	public ArrayList<DoubleMatrix> 	getACTjs();
	public DoubleMatrix 			getWkj();
	public ArrayList<DoubleMatrix> 	getWjs();
	
	/**
	 * Applies weight updates to the output layer to last hidden layer weights.
	 * @param Wkj		weight to apply
	 */
	public void applyWkjUpdate		(DoubleMatrix Wkj);
	/**
	 * Applies weight updates to the output layer to bias neuron weights.
	 * @param Wkbias	weight to apply
	 */
	public void applyWkbiasUpdate	(DoubleMatrix Wkbias);
	/**
	 * Applies weight updates to the input layer to first hidden layer weights.
	 * @param Wji		weight to apply
	 */
	public void applyWjiUpdate		(DoubleMatrix Wji);
	/**
	 * Applies weight updates to the hidden layer i to hidden layer i+1 weights for all i.
	 * <p>
	 * That is, updates the weights between each hidden layer.
	 * <p>
	 * Note that the length of the list should be n-1 where n is the number of hidden layers.
	 * @param Wjbias	list of weights to apply
	 */
	public void applyWjbiasUpdate	(ArrayList<DoubleMatrix> Wjbias);
	/**
	 * Applies weight updates to the hidden layer i to the bias for all i.
	 * <p>
	 * That is, updates the weights from each hidden layer to the bias.
	 * <p>
	 * Note that the length of the list should be n where n is the number of hidden layers.
	 * @param Wjs
	 */
	public void applyWjsUpdate		(ArrayList<DoubleMatrix> Wjs);
	
	/**
	 * Sets the associated error of the object.
	 * @param error		error of the object
	 */
	public void setError(DoubleMatrix error);
}
