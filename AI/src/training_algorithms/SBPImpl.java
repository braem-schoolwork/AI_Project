package training_algorithms;

import org.jblas.DoubleMatrix;

public interface SBPImpl
{
	public DoubleMatrix feedForward(DoubleMatrix inputVector);
	
	public DoubleMatrix applySigmoid(DoubleMatrix inc);
	public DoubleMatrix applySigmoidDeriv(DoubleMatrix inc);
	
	public void init();
	public void saveToDisk(double error);

	public DoubleMatrix getNETk();
	public DoubleMatrix getNETj();
	public DoubleMatrix getACTj();
	public DoubleMatrix getWkj();
	
	public void applyWkjUpdate(DoubleMatrix Wkj);
	public void applyWkbiasUpdate(DoubleMatrix Wkbias);
	public void applyWjiUpdate(DoubleMatrix Wji);
	public void applyWjbiasUpdate(DoubleMatrix Wjbias);
	
	public boolean isBestSoFar(double error);
}
