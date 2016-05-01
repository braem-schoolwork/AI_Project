package genetic_algorithm;

/**
 * Interface for objects to implement in order to be 'genomeable'.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public interface GenomeImpl
{
	/**
	 * Generate other objects similar this one
	 * 
	 * @param size		the amount of other similar objects to generate
	 * @return			array of similar objects
	 */
	public GenomeImpl[] genOthers(int size);
}
