package rubiks;

public class RubiksCube implements Searchable
{
	private int size; //size=2 when dealing 2x2x2 cube & vice versa
	private Node[] cube;
	
	//3x3x3 cube is represented by a 3*3*6=54 element vector
	//2x2x2 by 2*2*6=24 element vector
	public RubiksCube(int size) {
		this.size = size;
		cube = new Node[size*size*6];
	}

	@Override
	public Node[] getNodes() {
		return cube;
	}
	
	
}
