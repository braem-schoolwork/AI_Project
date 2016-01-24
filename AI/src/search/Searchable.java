package search;

/**
 * 
 * @author braem
 *
 * A Searchable object must be able to generate its own
 * child nodes, be equatable, keep track of its parent,
 * and keep track of the number of its moves taken from the start node
 *
 */

public interface Searchable {
	
	public Searchable[] genChildren();
	public boolean equals(Searchable obj);
	public Searchable getParent();
	public int g();
	
}
