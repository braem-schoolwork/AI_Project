package search;

import java.util.ArrayList;

import data_structures.HashSet;
import data_structures.PriorityQueue;

/**
 * 
 * @author braem
 *
 * A* Search
 * PriorityQueue for the openList (ordered by f value & fast insert/deletion)
 * HashSet for the closedList (fast insert & lookup)
 *
 * 
 * NOTES: The PriorityQueue used is Sun MicroSystems' PriorityQueue, but with an added
 * internal HashMap of which keeps track of the relationship between an Object and its
 * index in the queue. The reason for this is to make java.util's PriorityQueue also have
 * O(1) time complexity when looking up an object/removing that object by equality. This does
 * mean the space used by the openList is doubled, but in this case sacrificing space for speed is useful.
 * 
 * The HashSet is Sun MicroSystems HashSet, but with an added method
 * for extracting the reference via remove, instead of just returning
 * a boolean.
 *
 */

public class AstarSearch implements Search {
	
	private ArrayList<Searchable> path;
	private ArrayList<Edge> edges;
	private boolean searched = false;
	
	public AstarSearch() {
		path = new ArrayList<Searchable>();
		edges = new ArrayList<Edge>();
	}
	
	//returns path taken from search
	@Override
	public ArrayList<Searchable> getPath() {
		if(searched)
			return path;
		else
			return null;
	}
	
	@Override
	public ArrayList<Edge> getEdges() {
		if(searched)
			return edges;
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
				Searchable matchingElem = (Searchable)closedList.containsRef(child);
				if(matchingElem != null) {
					addChild = false;
					//consider if it's cheaper to go this way
					if(child.g() < matchingElem.g()) {
						closedList.remove(matchingElem);
						addChild = true;
					}
				}
				
				/* consider openList */
				if(addChild) {
					matchingElem = openList.containsRef(child);
					if(matchingElem != null) {
						addChild = false;
						if(child.g() < matchingElem.g()) {
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
		
		return openList.peek(); // return result of search
		
	} //A*
	
	
	//backtrace the path of the A* Search
	private void backTrace(Searchable start, Searchable end) {
		
		path.add(end);
		edges.add(end.getEdge());
		
		if(path.get(0).getParent() != null) {
			boolean isStartState = false;
			while(!isStartState) {
				Searchable parent = path.get(0).getParent();
				if(parent.equals(start)) {
					isStartState = true;
					path.add(0, parent);
				}
				else {
					path.add(0, parent);
					edges.add(0, parent.getEdge());
				}
			}
		}
	}
}
