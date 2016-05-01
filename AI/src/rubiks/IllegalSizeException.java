package rubiks;

/**
 * Exception that handles bad rubik's cube sizes.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class IllegalSizeException extends RuntimeException
{
	private static final long serialVersionUID = -1694655347436827898L;
	
	public IllegalSizeException() 									{ super(); }
	public IllegalSizeException(String message) 					{ super(message); }
	public IllegalSizeException(String message, Throwable cause) 	{ super(message, cause); }
	public IllegalSizeException(Throwable cause) 					{ super(cause); }
}