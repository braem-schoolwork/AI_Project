package rubiks;

import java.util.LinkedList;
import java.util.Queue;

public class BFSearch implements Search
{
	
	public void search(Searchable searchableObj, Node root) {
		Node[] nodes = searchableObj.getNodes();
		
		for(Node n : nodes) {
			n.setDistance(Integer.MAX_VALUE);
			n.setParent(null);
		}
		
		Queue<Node> queue = new LinkedList<Node>();
		
		root.setDistance(0);
		queue.add(root);
		
		while(!queue.isEmpty()) {
			Node currentNode = queue.poll();
			
			//for every node adjacent (has currentNode as parent)
			for(int i=0; i < nodes.length && nodes[i].getParent().equals(currentNode); i++) {
				Node n = nodes[i];
				if(n.getDistance() == Integer.MAX_VALUE) { //not explored
					n.setDistance(currentNode.getDistance()+1);
					n.setParent(currentNode);
					queue.add(n);
				}
			}
		}//end while
	}//end search
}
