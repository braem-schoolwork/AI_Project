package search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class AstarSearch implements Search {
	private ArrayList<Searchable> path;
	private boolean searched = false;
	
	public AstarSearch() {
		path = new ArrayList<Searchable>();
	}
	
	public ArrayList<Searchable> getPath() {
		if(searched)
			return path;
		else
			return null;
	}
	
	@Override
	public Searchable search(Searchable startState, Searchable goalState)
	{
		searched = true;
		
		//queue for objects to be searched
		PriorityQueue<Searchable> openList = new PriorityQueue<Searchable>();
		//queue for objects that have already been searched
		Queue<Searchable> closedList = new LinkedList<Searchable>();
		
		openList.add(startState); //add start state to the queue of objects to be searched
		while(!openList.peek().equals(goalState))
		{
			Searchable current = openList.poll(); //minimum element
			closedList.add(current);
			
			Searchable[] childList = current.genChildren();
			
			//search the children
			for(Searchable child : childList) {
				boolean addChild = true;
				
				/* consider closedList */
				boolean inList = false;
				Searchable matchingElem = null;
				for(Searchable item : closedList) {
					if(child.equals(item)) {
						matchingElem = item;
						inList = true;
						break;
					}
				}
				if(inList) { //we've explored it
					addChild = false;
					//consider if it's cheaper to go this way
					//should not fire frequently
					if(child.f() < matchingElem.f()) {
						closedList.remove(matchingElem);
						addChild = true;
					}
				}
				
				/* consider openList */
				if(addChild) {
					inList = false;
					for(Searchable item : openList) {
						if(child.equals(item)) {
							matchingElem = item;
							inList = true;
							break;
						}
					}
					if(inList) {
						addChild = false;
						if(child.f() < matchingElem.f()) {
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
		backTrace(startState, openList.peek());
		return openList.poll(); // return result of search
	} //A*
	
	//backtrace the path of the AstarSearch
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
