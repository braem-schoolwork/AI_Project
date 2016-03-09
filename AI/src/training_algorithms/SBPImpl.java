package training_algorithms;

import java.util.ArrayList;

import org.jblas.DoubleMatrix;

/**
 * Interface needed to be implemented in order for SBP to train
 * 
 * @author braemen
 * @version 1.0
 */
public interface SBPImpl
{
	public DoubleMatrix feedForward(DoubleMatrix inputVector);
	
	public DoubleMatrix applySigmoid(DoubleMatrix inc);
	public DoubleMatrix applySigmoidDeriv(DoubleMatrix inc);
	
	public void init();
	public void saveToDisk(DoubleMatrix error);

	public DoubleMatrix getNETk();
	public ArrayList<DoubleMatrix> getNETjs();
	public ArrayList<DoubleMatrix> getACTjs();
	public DoubleMatrix getWkj();
	public ArrayList<DoubleMatrix> getWjs();
	
	public void applyWkjUpdate(DoubleMatrix Wkj);
	public void applyWkbiasUpdate(DoubleMatrix Wkbias);
	public void applyWjiUpdate(DoubleMatrix Wji);
	public void applyWjbiasUpdate(ArrayList<DoubleMatrix> Wjbias);
	public void applyWjsUpdate(ArrayList<DoubleMatrix> Wjs);
	
	public void printAllEdges();
}
