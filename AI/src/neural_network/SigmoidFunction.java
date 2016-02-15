package neural_network;

import org.jblas.*;

public class SigmoidFunction
{
	
	private static double bias = 0.667;
	private static double A = 1.716;
	private static double Aa = 1.144572;
	
	public static DoubleMatrix apply(DoubleMatrix inc) {
		return MatrixFunctions.tanh(inc.mul(bias)).mul(A);
	}
	
	public static DoubleMatrix applyDeriv(DoubleMatrix inc) {
		return MatrixFunctions.pow( MatrixFunctions.tanh(inc.mul(bias)),2 ).mul(-A*bias).add(A*bias);
	}
	
}
