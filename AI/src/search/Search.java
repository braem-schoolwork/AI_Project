package search;

import java.util.ArrayList;

public interface Search {
	public Searchable search(Searchable startState, Searchable goalState);
	public ArrayList<Searchable> getPath();
}
