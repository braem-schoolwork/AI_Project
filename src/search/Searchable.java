package search;

/**
 * Interface an object needs to implement in order to be searchable.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public interface Searchable {
	
	/**
	 * Generate children of the object.
	 * 
	 * @return 		list of all children
	 */
	public Searchable[] genChildren();
	
	/**
	 * Problem-specific heuristic value.
	 * 
	 * @return 		heuristic guess on distance from goal state
	 */
	public float h();
	
	@Override
	public int hashCode();
	
	@Override
	public boolean equals(Object obj);
}
