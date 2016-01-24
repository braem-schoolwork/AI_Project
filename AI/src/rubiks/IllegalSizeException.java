package rubiks;

/**
 * 
 * @author braem
 *
 * Disallows illegal sizes for a RubiksCube object
 *
 */

public class IllegalSizeException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1694655347436827898L;
	public IllegalSizeException() {
		super();
	}
	public IllegalSizeException(String message) {
		super(message);
	}
	public IllegalSizeException(String message, Throwable cause) {
		super(message, cause);
	}
	public IllegalSizeException(Throwable cause) {
		super(cause);
	}
}