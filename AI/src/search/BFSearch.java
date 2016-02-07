package search;

import java.util.ArrayList;
import java.util.LinkedList;
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
	private ArrayList<Edge> edges;
	private boolean searched = false;
	
	public BFSearch() {
		path = new ArrayList<Searchable>();
		edges = new ArrayList<Edge>();
	}
	
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
	
	public Searchable search(Searchable startState, Searchable goalState)
	{
		
		//queue for objects to be searched
		Queue<Searchable> openList = new LinkedList<Searchable>();
		//queue for objects that have already been searched
		HashSet<Searchable> closedList = new HashSet<Searchable>();
		Searchable[] childList; //array for the generated children
		
		openList.add(startState); //add start state to the queue of objects to be searched
		
		//search loop
		while(!openList.isEmpty())
		{
			Searchable current = openList.poll(); //get the next unexplored obj
			closedList.add(current); //explored this
			
			//check if current state is a goal state
			if(current.equals(goalState)) {
				backTrace(startState, current); //backtrace steps taken
				searched = true; //done search
				return current;
			}
			
			//generate every possible object adjacent to this object
			childList = current.genChildren();
			
			//search every adjacent object
			for(Searchable child : childList) {
				boolean addChild = true;
				
				//check the items that have been searched
				for(Searchable item : openList) //O(n)
					//if we find the same item, dont bother searching it
					if(child.equals(item)) {
						addChild = false;
						openList.remove(item);
						break;
					}
				
				if(addChild) { //if we should add the child
					//check the set of items to be searched
					Searchable matchingElem = (Searchable)closedList.containsRef(child);
					//if we find the same item, dont bother searching it
					if(matchingElem != null) {
						addChild = false;
						closedList.remove(matchingElem);
					}
				}
				
				//if we should add the child
				if(addChild) {
					openList.add(child); //add it to the queue of items to be searched
				}
				
			}//foreach child
		}//while openList
		
		return null; //search failed
	}//end search
	
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

