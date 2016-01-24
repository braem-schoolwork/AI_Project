package search;

import java.util.ArrayList;

/**
 * 
 * @author braem
 *
 * A Search needs to be able to search & generate
 * a path from start state to goal state
 *
 */

public interface Search {
	public Searchable search(Searchable startState, Searchable goalState);
	public ArrayList<Searchable> getPath();
}
