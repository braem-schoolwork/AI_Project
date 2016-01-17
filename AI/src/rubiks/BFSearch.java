package rubiks;

import java.util.LinkedList;
import java.util.Queue;

public class BFSearch implements Search
{
	public static Searchable search(Searchable startState)
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
			if(current.isSolved())
				return current;
			
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
				if(addChild)
					openList.add(child); //add it to the queue of items to be searched
				
			}//foreach child
		}//while openList
		return null; //search failed
	}//end search
}
