package dataStructures;

public class UnderflowException extends RuntimeException
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 625336447484900855L;
	public UnderflowException() {
		super();
	}
	public UnderflowException(String message) {
		super(message);
	}
	public UnderflowException(String message, Throwable cause) {
		super(message, cause);
	}
	public UnderflowException(Throwable cause) {
		super(cause);
	}

}
