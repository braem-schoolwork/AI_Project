package search;

import java.util.ArrayList;
import java.util.PriorityQueue;

import dataStructures.HashSet;

/**
 * 
 * @author braem
 *
 * A* Search
 * PriorityQueue for the openList (ordered by f value & fast insert/deletion)
 * HashSet for the closedList (fast insert & lookup)
 *
 * 
 * NOTES: PriorityQueue has a O(n) for lookup. Possible fixes might be
 * keeping track of index to object mappings via HashMap.
 * 
 * The HashSet is Sun MicroSystems HashSet, but with an added method
 * for extracting the reference via remove, instead of just returning
 * a boolean.
 *
 */

public class AstarSearch implements Search {
	
	private ArrayList<Searchable> path;
	private boolean searched = false;
	
	public AstarSearch() {
		path = new ArrayList<Searchable>();
	}
	
	//returns path taken from search
	public ArrayList<Searchable> getPath() {
		if(searched)
			return path;
		else
			return null;
	}
	
	@Override
	public Searchable search(Searchable startState, Searchable goalState)
	{
		
		//queue for objects to be searched
		PriorityQueue<Searchable> openList = new PriorityQueue<Searchable>();
		//set for objects that have already been searched
		HashSet<Searchable> closedList = new HashSet<Searchable>();
		//array for the children generated
		Searchable[] childList;
		
		openList.add(startState); //add start state to the queue of objects to be searched
		
		while(!openList.peek().equals(goalState))
		{
			
			Searchable current = openList.poll(); //get & remove minimum element
			closedList.add(current);
			
			childList = current.genChildren(); //generate child nodes
			
			//search the children
			for(Searchable child : childList) {
				boolean addChild = true;
				
				/* consider closedList */
				boolean inList = false;
				Searchable matchingElem = null;
				matchingElem = closedList.removeRef(child);
				if(inList) { //we've explored it
					addChild = false;
					//consider if it's cheaper to go this way
					if(child.g() <= matchingElem.g()) {
						closedList.remove(matchingElem);
						addChild = true;
					}
				}
				
				/* consider openList */
				if(addChild) {
					inList = false;
					for(Searchable item : openList) { //TODO fix O(n) worst case
						if(child.equals(item)) {
							matchingElem = item;
							inList = true;
							break;
						}
					}
					if(inList) {
						addChild = false;
						if(child.g() <= matchingElem.g()) {
							openList.remove(matchingElem);
							addChild = true;
						}
					}
				}
				
				/* if we should add it, just do it already */
				if(addChild) {
					openList.add(child);
				}
			}//enhanced for
		}//while
		
		searched = true; //completed search
		backTrace(startState, openList.peek()); //backtrace nodes
		
		return openList.poll(); // return result of search
		
	} //A*
	
	
	//backtrace the path of the A* Search
	private void backTrace(Searchable start, Searchable end) {
		
		path.add(end);
		
		if(path.get(0).getParent() != null) {
			boolean isStartState = false;
			while(!isStartState) {
				Searchable parent = path.get(0).getParent();
				if(parent.equals(start))
					isStartState = true;
				path.add(0, parent);
			}
		}
		
	}
}
