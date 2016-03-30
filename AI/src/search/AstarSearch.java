package search;

import java.util.ArrayList;
import java.util.List;

import data_structures.HashSet;
import data_structures.PriorityQueue;

/**
 * Performs an A* Search on an object implementing Searchable 
 * 
 * @author braem
 * @version 1.0
 */
public class AstarSearch implements Search {
	
	private ArrayList<Searchable> path;
	private boolean searched = false;
	
	public AstarSearch() {
		path = new ArrayList<Searchable>();
	}
	
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
	
	/**
	 * back-tracks from the end node to the start node, creating a path of nodes
	 * 
	 * @param startNode		starting node to back trace to
	 * @param endNode		ending node to start the back trace
	 */
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
