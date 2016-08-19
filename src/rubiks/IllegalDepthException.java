package rubiks;

/**
 * Exception to handle an invalid perturbation value.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class IllegalDepthException extends RuntimeException {
	private static final long serialVersionUID = 543153852702253133L;
	
	public IllegalDepthException() 									{ super(); }
	public IllegalDepthException(String message) 					{ super(message); }
	public IllegalDepthException(String message, Throwable cause) 	{ super(message, cause); }
	public IllegalDepthException(Throwable cause) 					{ super(cause); }
}
