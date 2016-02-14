package neural_network;

import org.jblas.*;

public class SigmoidFunction
{
	
	public static DoubleMatrix apply(DoubleMatrix inc) {
		return MatrixFunctions.tanh(inc);
	}
	
	public static DoubleMatrix applyDeriv(DoubleMatrix inc) {
		return (MatrixFunctions.pow(2, (MatrixFunctions.tanh(inc))).mul(-1)).add(1);
	}
	
}
