package rubiks;

public class IllegalSizeException extends RuntimeException
{
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