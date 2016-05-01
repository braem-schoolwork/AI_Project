package search;

import java.util.ArrayList;
import java.util.List;

import data_structures.HashSet;
import data_structures.PriorityQueue;

/**
 * Performs an A* Search on an object implementing Searchable.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class AstarSearch implements Search {
	private ArrayList<Searchable> 	path 		= null;
	private boolean 				searched 	= false;
	
	public AstarSearch() { path = new ArrayList<Searchable>(); }
	
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
		PriorityQueue<SearchListNode> 	openList 			= new PriorityQueue<SearchListNode>();
		HashSet<SearchListNode> 		closedList 			= new HashSet<SearchListNode>();
		Searchable[] 					childList;
		SearchListNode 					startingListNode 	= new SearchListNode(null, startState, 0);
		
		openList.add(startingListNode);
		while(!openList.peek().getSearchableObj().equals(goalState))
		{
			SearchListNode current = openList.poll();
			closedList.add(current);
			
			childList = current.getSearchableObj().genChildren();
			
			for(Searchable child : childList) {
				SearchListNode 	childNode 		= new SearchListNode(current, child, 1+current.getGVal());
				boolean 		addChild 		= true;
				SearchListNode 	matchingElem 	= (SearchListNode)closedList.containsRef(childNode);
				
				if(matchingElem != null) {
					addChild = false;
					if(childNode.getGVal() < matchingElem.getGVal()) {
						closedList.remove(matchingElem);
						addChild = true;
					}
				}
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
				if(addChild) {
					openList.add(childNode);
				}
			}//enhanced for
		}//while
		searched = true;
		backTrace(startingListNode, openList.peek());
		return openList.peek().getSearchableObj();
	} //A*
	
	/**
	 * back-tracks from the end node to the start node, creating a path of nodes.
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
				else
					nodes.add(0, parentNode);
			}
		}
		for(SearchListNode sln : nodes)
			path.add(sln.getSearchableObj());
	}
}
