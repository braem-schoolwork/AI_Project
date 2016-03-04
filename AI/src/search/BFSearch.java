package search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import data_structures.HashSet;
/**
 * 
 * @author braem
 *
 * Breadth-First Search
 * 
 * 
 *
 */

public class BFSearch implements Search
{
	private ArrayList<Searchable> path;
	private boolean searched = false;
	
	public BFSearch() {
		path = new ArrayList<Searchable>();
	}
	
	@Override
	public ArrayList<Searchable> getPath() {
		if(searched)
			return path;
		else
			return null;
	}
	
	public Searchable search(Searchable startState, Searchable goalState)
	{
		path.clear();
		//queue for objects to be searched
		Queue<SearchListNode> openList = new LinkedList<SearchListNode>();
		//queue for objects that have already been searched
		HashSet<SearchListNode> closedList = new HashSet<SearchListNode>();
		Searchable[] childList; //array for the generated children
		
		SearchListNode startingListNode = new SearchListNode(null, startState, 0);
		openList.add(startingListNode); //add start state to the queue of objects to be searched
		//search loop
		while(!openList.isEmpty())
		{
			SearchListNode current = openList.poll(); //get the next unexplored obj
			closedList.add(current); //explored this
			
			//check if current state is a goal state
			if(current.equals(goalState)) {
				backTrace(startingListNode, current); //backtrace steps taken
				searched = true; //done search
				return current.getSearchableObj();
			}
			
			//generate every possible object adjacent to this object
			childList = current.getSearchableObj().genChildren();
			
			//search every adjacent object
			for(Searchable child : childList) {
				SearchListNode childNode = new SearchListNode(current, child, 1+current.getGVal());
				boolean addChild = true;
				
				//check the items that have been searched
				for(SearchListNode item : openList) //O(n)
					//if we find the same item, dont bother searching it
					if(childNode.equals(item)) {
						addChild = false;
						openList.remove(item);
						break;
					}
				
				if(addChild) { //if we should add the child
					//check the set of items to be searched
					SearchListNode matchingElem = (SearchListNode)closedList.containsRef(childNode);
					//if we find the same item, dont bother searching it
					if(matchingElem != null) {
						addChild = false;
						closedList.remove(matchingElem);
					}
				}
				
				//if we should add the child
				if(addChild) {
					openList.add(childNode); //add it to the queue of items to be searched
				}
				
			}//foreach child
		}//while openList
		
		return null; //search failed
	}//end search
	
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

