package rubiks;

import java.util.LinkedList;
import java.util.Queue;

public class BFSearch implements Search
{
	public Searchable search(Searchable startState, Searchable[] goalStates) {
		
		Queue<Searchable> openList = new LinkedList<Searchable>();
		Queue<Searchable> closedList = new LinkedList<Searchable>();
		openList.add(startState);
		
		while(!openList.isEmpty()) {
			Searchable current = openList.poll();
			closedList.add(current);
			
			//check if current state is a goal state
			if(current.isSolved())
				return current;
			
			Searchable[] childList = current.genChildren();
			
			for(Searchable child : childList) {
				boolean addChild = true;
				//check openList
				for(Searchable item : openList) {
					if(child.equals(item)) {
						addChild = false;
						break;
					}
				}
				if(addChild) {
					for(Searchable item : closedList) {
						if(child.equals(item)) {
							addChild = false;
							break;
						}
					}
				}
				
				if(addChild)
					openList.add(child);
			}//foreach child
		}//while openList
		return null; //search failed
	}//end search
}
