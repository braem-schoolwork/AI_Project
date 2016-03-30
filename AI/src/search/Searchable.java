package search;

/**
 * Interface an object needs to implement in order to be searchable
 * 
 * @author braem
 * @version 1.0
 */
public interface Searchable {
	
	/**
	 * Generation of all children of the object
	 * 
	 * @return list of all children
	 */
	public Searchable[] genChildren();
	
	/**
	 * Problem-specific heuristic value
	 * 
	 * @return guess on how far from goal state
	 */
	public float h();
	
	@Override
	public int hashCode();
	
	@Override
	public boolean equals(Object obj);
}
