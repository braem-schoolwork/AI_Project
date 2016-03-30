package search;

import java.util.ArrayList;

/**
 * interface to specify a search algorithm
 * 
 * @author braem
 * @version 1.0
 */
public interface Search {
	/**
	 * Searches startState, looking for a point where startState = goalState
	 * while keeping track of path via parent updates in SearchListNode
	 * 
	 * @param startState
	 * @param goalState
	 * @return
	 */
	public Searchable search(Searchable startState, Searchable goalState);
	
	/**
	 * @return path of searchable objects
	 */
	public ArrayList<Searchable> getPath();
}
