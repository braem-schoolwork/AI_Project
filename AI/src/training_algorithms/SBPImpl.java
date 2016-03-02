package training_algorithms;

import java.util.List;

import org.jblas.DoubleMatrix;

public interface SBPImpl
{
	public DoubleMatrix feedForward(DoubleMatrix inputVector);
	
	public DoubleMatrix applySigmoid(DoubleMatrix inc);
	public DoubleMatrix applySigmoidDeriv(DoubleMatrix inc);
	
	public void init();
	public void saveToDisk(double error);

	public DoubleMatrix getNETk();
	public List<DoubleMatrix> getNETjs();
	public List<DoubleMatrix> getACTjs();
	public DoubleMatrix getWkj();
	public List<DoubleMatrix> getWjs();
	
	public void applyWkjUpdate(DoubleMatrix Wkj);
	public void applyWkbiasUpdate(DoubleMatrix Wkbias);
	public void applyWjiUpdate(DoubleMatrix Wji);
	public void applyWjbiasUpdate(List<DoubleMatrix> Wjbias);
	public void applyWjsUpdate(List<DoubleMatrix> Wjs);
}
