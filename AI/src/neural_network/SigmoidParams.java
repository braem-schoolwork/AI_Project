package neural_network;

import java.io.Serializable;

/**
 * Parameters for the Sigmoid Function
 * 
 * @author braem
 * @version 1.0
 */
public class SigmoidParams implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5549357125056479039L;
	
	private double A;
	private double B;
	
	public SigmoidParams() {
		setA(1.716);
		setB(0.667);
	}
	public SigmoidParams(double A, double B) {
		this.setA(A);
		this.setB(B);
	}
	
	public double getB() {
		return B;
	}
	public void setB(double b) {
		B = b;
	}
	public double getA() {
		return A;
	}
	public void setA(double a) {
		A = a;
	}
}
