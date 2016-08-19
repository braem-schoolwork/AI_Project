package search;

import java.util.ArrayList;

/**
 * Interface to specify a search algorithm.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public interface Search {
	
	/**
	 * Searches startState, looking for a point where the startState equals the goalState
	 * while keeping track of path via parent updates in SearchListNode
	 * 
	 * @param startState	start state for the search
	 * @param goalState		goal state for the search
	 * @return				object in the goal state
	 */
	public Searchable search(Searchable startState, Searchable goalState);
	
	public ArrayList<Searchable> getPath();
}
