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
	public static double genBetweenInterval(double left, double right) {
		Random r = new Random();
		return left+(right-left) * r.nextDouble();
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