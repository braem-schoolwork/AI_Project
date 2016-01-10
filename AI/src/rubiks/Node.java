package rubiks;

public class Node {
	private int distance;
	private Node parent;
	
	public Node(int distance, Node parent) {
		this.distance = distance;
		this.parent = parent;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Node getParent() {
		return parent;
	}
	public int getDistance() {
		return distance;
	}
}
