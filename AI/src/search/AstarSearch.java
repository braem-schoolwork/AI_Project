package search;

import java.util.ArrayList;
import java.util.List;

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
	private boolean searched = false;
	
	public AstarSearch() {
		path = new ArrayList<Searchable>();
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
	public Searchable search(Searchable startState, Searchable goalState)
	{
		path.clear();
		//queue for objects to be searched
		PriorityQueue<SearchListNode> openList = new PriorityQueue<SearchListNode>();
		//set for objects that have already been searched
		HashSet<SearchListNode> closedList = new HashSet<SearchListNode>();
		//array for the children generated
		Searchable[] childList;
		
		SearchListNode startingListNode = new SearchListNode(null, startState, 0);
		openList.add(startingListNode); //add start state to the queue of objects to be searched
		while(!openList.peek().getSearchableObj().equals(goalState))
		{
			SearchListNode current = openList.poll(); //get & remove minimum element
			closedList.add(current);
			
			childList = current.getSearchableObj().genChildren(); //generate child nodes
			
			//search the children
			for(Searchable child : childList) {
				SearchListNode childNode = new SearchListNode(current, child, 1+current.getGVal());
				boolean addChild = true;
				/* consider closedList */
				SearchListNode matchingElem = (SearchListNode)closedList.containsRef(childNode);
				if(matchingElem != null) {
					addChild = false;
					//consider if it's cheaper to go this way
					if(childNode.getGVal() < matchingElem.getGVal()) {
						closedList.remove(matchingElem);
						addChild = true;
					}
				}
				/* consider openList */
				if(addChild) {
					matchingElem = openList.containsRef(childNode);
					if(matchingElem != null) {
						addChild = false;
						if(childNode.getGVal() < matchingElem.getGVal()) {
							openList.remove(matchingElem);
							addChild = true;
						}
					}
				}
				
				/* if we should add it, just do it already */
				if(addChild) {
					openList.add(childNode);
				}
			}//enhanced for
		}//while
		searched = true; //completed search
		backTrace(startingListNode, openList.peek()); //backtrace nodes
		
		return openList.peek().getSearchableObj(); // return result of search
		
	} //A*
	
	
	//backtrace the path of the A* Search
	private void backTrace(SearchListNode startNode, SearchListNode endNode) {
		List<SearchListNode> nodes = new ArrayList<SearchListNode>();
		nodes.add(endNode);
		
		if(nodes.get(0).getParent() != null) {
			boolean isStartState = false;
			while(!isStartState) {
				SearchListNode parentNode = nodes.get(0).getParent();
				if(parentNode.equals(startNode)) {
					isStartState = true;
					nodes.add(0, parentNode);
				}
				else {
					nodes.add(0, parentNode);
				}
			}
		}//end if
		for(SearchListNode sln : nodes)
			path.add(sln.getSearchableObj());
	}
}
