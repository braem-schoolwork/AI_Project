package genetic_algorithm;

/**
 * Exception to handle illegal GeneticAlgorithmParams
 * If fired, most likely due to crossover, mutation, and elite percentages
 * 
 * @author braem
 * @version 1.0
 */
public class IllegalGAParamsException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1694655347436827898L;
	public IllegalGAParamsException() {
		super();
	}
	public IllegalGAParamsException(String message) {
		super(message);
	}
	public IllegalGAParamsException(String message, Throwable cause) {
		super(message, cause);
	}
	public IllegalGAParamsException(Throwable cause) {
		super(cause);
	}
}
