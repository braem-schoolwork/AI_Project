package random_gen;

import java.util.Random;

/**
 * Utility for generating random numbers
 * 
 * @author braemen
 * @version 1.0
 */
public class RandomNumberGenerator
{
	/**
	 * Generates a random number between a specified interval
	 * 
	 * @param left			left side of interval (min value)
	 * @param right			right side of interval (max value)
	 * @return 				a random number between left and right
	 */
	public static double genDoubleBetweenInterval(double left, double right) {
		Random r = new Random();
		return left+(right-left) * r.nextDouble();
	}
	
	public static int genIntBetweenInterval(int left, int right) {
		Random r = new Random();
		return r.nextInt((right-left)+1) + left;
	}
	
	/**
	 * Generates a random value
	 * 
	 * @return a random value
	 */
	public static double genRandomVal() {
		Random r = new Random();
		return r.nextDouble();
	}
}
