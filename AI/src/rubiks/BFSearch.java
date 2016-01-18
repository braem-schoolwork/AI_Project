package rubiks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Standard Breadth-First Search
 * 
 * Path is recorded through keeping track of parent & moves from the parent
 * in the RubiksCube class
 * 
 * 2x2x2 at depth 3 takes ~0.1-0.2 seconds
 * 2x2x2 at depth 4 takes ~80-100 seconds
 */
public class BFSearch implements Search
{
	private ArrayList<Searchable> path;
	private ArrayList<Move> moves;
	private int movesDone = 0;
	public BFSearch() {
		path = new ArrayList<Searchable>();
		moves = new ArrayList<Move>();
	}
	
	public ArrayList<Searchable> getPath() {
		return path;
	}
	public int getMovesDone() {
		return movesDone;
	}
	public ArrayList<Move> getMoves() {
		return moves;
	}
	
	public Searchable search(Searchable startState)
	{
		//queue for objects to be searched
		Queue<Searchable> openList = new LinkedList<Searchable>();
		//queue for objects that have already been searched
		Queue<Searchable> closedList = new LinkedList<Searchable>();
		
		openList.add(startState); //add start state to the queue of objects to be searched
		
		//search loop
		while(!openList.isEmpty())
		{
			Searchable current = openList.poll(); //get the next unexplored obj
			closedList.add(current); //explored this
			
			//check if current state is a goal state
			if(current.isSolved()) {
				backTrace(startState, current);
				return current;
			}
			
			//generate every possible object adjacent to this object
			Searchable[] childList = current.genChildren();
			
			//search every adjacent object
			for(Searchable child : childList) {
				boolean addChild = true;
				
				//check queue of items to be searched
				for(Searchable item : openList)
					//if we find the same item, dont bother searching it
					if(child.equals(item)) {
						addChild = false;
						break;
					}
				
				if(addChild) //if we should add the child
					//check the items that have been searched
					for(Searchable item : closedList)
						//if we find the same item, dont bother searching it
						if(child.equals(item)) {
							addChild = false;
							break;
						}
				
				//if we should add the child
				if(addChild) {
					openList.add(child); //add it to the queue of items to be searched
				}
				
			}//foreach child
		}//while openList
		return null; //search failed
	}//end search
	
	//TODO remove moves && trace those later when Searchable is cast to RubiksCube
	//backtrace the path of the BFSearch
	private void backTrace(Searchable start, Searchable end) {
		path.add(end);
		boolean isStartState = false;
		while(!isStartState) {
			Searchable parent = path.get(0).getParent();
			if(parent.equals(start)) {
				isStartState = true;
			}
			moves.add(0, path.get(0).getMoveAppliedToParent());
			path.add(0, parent);
		}
		movesDone = moves.size();
	}
	
}
