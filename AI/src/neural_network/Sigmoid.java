package neural_network;

import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

/**
 * Class for applying a tanh sigmoid function using a SigmoidParams
 * paramter object
 * 
 * @author braem
 * @version 1.0
 */
public class Sigmoid
{
	private SigmoidParams params;
	
	public Sigmoid(SigmoidParams params) {
		this.params = params;
	}
	
	public DoubleMatrix apply(DoubleMatrix m) {
		return MatrixFunctions.tanh(m.mul(params.getB())).mul(params.getA());
	}
	public DoubleMatrix applyDeriv(DoubleMatrix m) {
		return MatrixFunctions.pow( MatrixFunctions.tanh(m),2 ).mul(-1).add(1);
	}
}
