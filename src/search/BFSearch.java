package search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import data_structures.HashSet;

/**
 * Performs a Breadth-First Search on an object implementing Searchable.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class BFSearch implements Search
{
	private ArrayList<Searchable> path;
	private boolean searched = false;
	
	public BFSearch() { path = new ArrayList<Searchable>(); }
	
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
		Queue<SearchListNode> 	openList 			= new LinkedList<SearchListNode>();
		HashSet<SearchListNode> closedList 			= new HashSet<SearchListNode>();
		Searchable[] 			childList;
		SearchListNode 			startingListNode 	= new SearchListNode(null, startState, 0);
		
		openList.add(startingListNode);
		while(!openList.isEmpty())
		{
			SearchListNode current = openList.poll();
			closedList.add(current);

			if(current.getSearchableObj().equals(goalState)) {
				backTrace(startingListNode, current);
				searched = true;
				return current.getSearchableObj();
			}

			childList = current.getSearchableObj().genChildren();

			for(Searchable child : childList) {
				SearchListNode childNode = new SearchListNode(current, child, 1+current.getGVal());
				boolean addChild = true;

				for(SearchListNode item : openList)
					if(childNode.equals(item)) {
						addChild = false;
						openList.remove(item);
						break;
					}
				if(addChild) {
					SearchListNode matchingElem = (SearchListNode)closedList.containsRef(childNode);
					if(matchingElem != null) {
						addChild = false;
						closedList.remove(matchingElem);
					}
				}
				if(addChild) {
					openList.add(childNode);
				}
				
			}//foreach child
		}//while openList
		return null;
	}//end search
	
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

