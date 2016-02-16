package neural_network;

import org.jblas.DoubleMatrix;

public interface SBPImpl
{
	public DoubleMatrix feedForward(DoubleMatrix inputVector);
	public DoubleMatrix applySigmoid(DoubleMatrix inc);
	public DoubleMatrix applySigmoidDeriv(DoubleMatrix inc);
	public void init();
	public void writeNetworkToFile(double error);
	public int getInputLayerSize();
	public int getHiddenLayerSize();
	public int getOutputLayerSize();
	public DoubleMatrix getNETk();
	public DoubleMatrix getNETj();
	public DoubleMatrix getWkj();
	public void applyWkjUpdate(DoubleMatrix Wkj);
	public void applyWkbiasUpdate(DoubleMatrix Wkbias);
	public void applyWjiUpdate(DoubleMatrix Wji);
	public void applyWjbiasUpdate(DoubleMatrix Wjbias);
}
