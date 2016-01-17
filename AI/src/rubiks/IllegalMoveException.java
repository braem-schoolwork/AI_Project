package rubiks;

public class IllegalMoveException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3143214943611449324L;
	public IllegalMoveException() {
		super();
	}
	public IllegalMoveException(String message) {
		super(message);
	}
	public IllegalMoveException(String message, Throwable cause) {
		super(message, cause);
	}
	public IllegalMoveException(Throwable cause) {
		super(cause);
	}
}
